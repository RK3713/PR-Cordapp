package com.pr.webserver.controller;


import com.pr.common.data.PRFlowData;
import com.pr.common.exception.PRException;
import com.pr.consultant.ConsultantInitiator;
import com.pr.contract.state.schema.contracts.PRContract;
import com.pr.contract.state.schema.states.PRState;
import com.pr.contract.state.schema.states.PRStatus;
import com.pr.server.common.bo.impl.PRBO;
import com.pr.server.common.controller.CommonController;
import net.corda.core.contracts.Amount;
import net.corda.core.contracts.StateAndRef;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.CordaX500Name;
import net.corda.core.identity.Party;
import net.corda.core.messaging.FlowHandle;
import net.corda.core.node.services.Vault;
import net.corda.core.node.services.vault.QueryCriteria;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.utilities.OpaqueBytes;
import net.corda.finance.flows.AbstractCashFlow;
import net.corda.finance.flows.CashIssueFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * @author Ajinkya Pande & Rishi Kundu
 */


@RestController
@RequestMapping("/consultant") // The paths for HTTP requests are relative to this base path.
@CrossOrigin
public class ConsultantController extends CommonController {

    private final static Logger logger = LoggerFactory.getLogger(ConsultantController.class);


    @CrossOrigin
    @GetMapping("/hello")
    private String hello() {
        return "hello world";
    }

    /**
     * @param prData is a json object which we provide as an input to our post api
     * @return It returns status whether PR request is created or not
     */

    @CrossOrigin
    @PostMapping("/")
    private ResponseEntity sendPRRequest(@RequestBody PRBO prData) {
        StateAndRef<PRState> previousPRRequest = null;
        AbstractParty consultantParty = null;

        PRContract.Commands command = new PRContract.Commands.CREATE();

        if (prData == null)
            throw new PRException("Invalid Request!");

        AbstractParty wesParty = getPartyFromFullName("O=Wes,L=London,C=GB");

        final Amount amountToBeTransferred = new Amount<>((long) prData.getAmount() * 100, Currency.getInstance("USD"));

        Set<Party> partyFrom = connector.getRPCops().partiesFromName(prData.getConsultantParty(), false);
        Iterator<Party> parties = partyFrom.iterator();

        while (parties.hasNext()) {
            consultantParty = parties.next();
        }

        // Creating state
        PRState prState = convertToPRState(prData, PRStatus.APPLICATION_SUBMITTED, consultantParty, wesParty, amountToBeTransferred, wesParty);

        try {
            FlowHandle<SignedTransaction> flowHandle = connector.getRPCops().startFlowDynamic
                    (ConsultantInitiator.class, new PRFlowData(prState, previousPRRequest, command));
            SignedTransaction signedTransaction = flowHandle.getReturnValue().get();
            logger.info(String.format("signed Tx id: %s", signedTransaction.getId().toString()));
            return ResponseEntity.ok("PR Request created with Txn Id: " + signedTransaction.getId().toString());
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage(), e.getCause());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    /**
     * @param amount   is parameter which needs to be transferred to another party
     * @param currency relates to the type of currency (USD, INR)
     * @return It returns status whether money has transferred or not
     */

    @CrossOrigin
    @PutMapping("/")
    public ResponseEntity issueCash(@RequestParam("amount") Long amount, @RequestParam("currency") String currency) {
        // 1. Prepare issue request.
        final Amount<Currency> issueAmount = new Amount<>(amount * 100, Currency.getInstance(currency));
        final List<Party> notaries = connector.getRPCops().notaryIdentities();
        if (notaries.isEmpty()) {
            throw new IllegalStateException("COULD_NOT_FIND_A_NOTARY");
        }
        final Party notary = notaries.get(0);
        final OpaqueBytes issueRef = OpaqueBytes.of("0".getBytes());
        final CashIssueFlow.IssueRequest issueRequest = new CashIssueFlow.IssueRequest(issueAmount, issueRef, notary);
        // 2. Start flow and wait for response.
        try {
            final FlowHandle<AbstractCashFlow.Result> flowHandle = connector.getRPCops().startFlowDynamic(CashIssueFlow.class, issueRequest);
            final AbstractCashFlow.Result result = flowHandle.getReturnValue().get();
            final String msg = result.getStx().getTx().getOutputStates().get(0).toString();
            return ResponseEntity.status(HttpStatus.CREATED).body(msg);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }



    /**
     * @param id is a wesReferenceNumber which should be provided while querying state according to wesReferenceNumber
     * @return It returns the state by querying the vault
     * @throws Exception
     */

    @CrossOrigin
    @GetMapping("/")
    public ResponseEntity getPRRequestDetails(@RequestParam(value = "id", required = false) String id) throws Exception {

        if (!StringUtils.isEmpty(id)) {
            try {
                UniqueIdentifier uniqueIdentifier = UniqueIdentifier.Companion.fromString(id);
                Set<Class<PRState>> contractStateTypes = new HashSet(Collections.singletonList(PRState.class));

                QueryCriteria linearCriteria = new QueryCriteria.LinearStateQueryCriteria(null, Arrays.asList(uniqueIdentifier),
                        Vault.StateStatus.UNCONSUMED, contractStateTypes);

                Vault.Page<PRState> results = connector.getRPCops().vaultQueryByCriteria(linearCriteria, PRState.class);


                if (results.getStates().size() > 0) {
                    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(mapper.writeValueAsString(results.getStates()));
                } else {
                    return ResponseEntity.status(HttpStatus.OK).body("No Records found");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                List<StateAndRef<PRState>> states = connector.getRPCops().vaultQuery(PRState.class).getStates();
                if (logger.isDebugEnabled()) {
                    states.forEach(e -> logger.debug(e.getState().getData().toString()));
                }
                if (!states.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body
                            (mapper.writeValueAsString(states));
                } else {
                    return ResponseEntity.noContent().build();
                }

        } catch(Exception e){
            e.printStackTrace();
        }
    }

        return new ResponseEntity<>("",HttpStatus.BAD_REQUEST);
}


    /**
     * @param partyName is a full party name
     * @return It returns the exact party name from full name of party
     */

    private AbstractParty getPartyFromFullName(String partyName) {
        CordaX500Name x500Name = CordaX500Name.parse(partyName);
        return connector.getRPCops().wellKnownPartyFromX500Name(x500Name);
    }


}






