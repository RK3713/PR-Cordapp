package com.pr.webserver.controller;


import com.pr.common.data.PRFlowData;
import com.pr.consultant.ConsultantInitiator;
import com.pr.contract.state.schema.contracts.PRContract;
import com.pr.contract.state.schema.states.PRState;
import com.pr.contract.state.schema.states.PRStatus;
import com.pr.server.common.bo.impl.PRBO;
import com.pr.server.common.controller.CommonController;
import net.corda.core.contracts.StateAndRef;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.CordaX500Name;
import net.corda.core.identity.Party;
import net.corda.core.messaging.FlowHandle;
import net.corda.core.transactions.SignedTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Define your API endpoints here.
 */
@RestController
@RequestMapping("/consultant") // The paths for HTTP requests are relative to this base path.
@CrossOrigin
public class ConsultantController extends CommonController {

    private final static Logger logger = LoggerFactory.getLogger(ConsultantController.class);

    @CrossOrigin
    @GetMapping(value = "/templateendpoint", produces = "text/plain")
    private String templateendpoint() {
        return "Define an endpoint here.";
    }

    @CrossOrigin
    @GetMapping("/hello")
    private String hello() {
        return "hello world";
    }


    @CrossOrigin
    @PostMapping("/")
    private ResponseEntity sendPRRequest(@RequestBody PRBO prData) {

        PRContract.Commands command;
        SignedTransaction signedTransaction = null;
        PRState prState;
        StateAndRef<PRState> previousPRRequest = null;
        AbstractParty consultantParty = null;

        command = new PRContract.Commands.CREATE();

        if (prData == null)
            throw new IllegalArgumentException("Invalid Request!");

        AbstractParty wesParty = getPartyFromFullName("O=Wes,L=London,C=GB");
        AbstractParty universityParty = getPartyFromFullName("O=University,L=New York,C=US");

        Set<Party> partyFrom = connector.getRPCops().partiesFromName(prData.getConsultantParty(),false);
        Iterator<Party> parties = partyFrom.iterator();

        while(parties.hasNext()){
            consultantParty = parties.next();
        }

        logger.info(" ********************************************** ");
        logger.info(" Consultant Party :" + consultantParty);
        logger.info(" Wes Party Party :" + wesParty);
        logger.info(" University Party :" + universityParty);
        logger.info(" ********************************************** ");

        // Creating state
        prState = convertToPRState(prData,PRStatus.ACCOUNT_CREATED,consultantParty,wesParty,universityParty);

        try {
            FlowHandle<SignedTransaction> flowHandle = connector.getRPCops().startFlowDynamic
                    (ConsultantInitiator.class, new PRFlowData(prState,
                            previousPRRequest, command));
            signedTransaction = flowHandle.getReturnValue().get();
            logger.info(String.format("signed Tx id: %s", signedTransaction.getId().toString()));
            return ResponseEntity.ok("PR Request created with Txn Id: " + signedTransaction.getId().toString());
        }catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage(), e.getCause());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }



    private AbstractParty getPartyFromFullName(String partyName){
        CordaX500Name x500Name = CordaX500Name.parse(partyName);
        return connector.getRPCops().wellKnownPartyFromX500Name(x500Name);
    }


    @CrossOrigin
    @GetMapping("/")
    public ResponseEntity getPRRequestDetails() throws Exception{

        List<StateAndRef<PRState>> states = connector.getRPCops().vaultQuery(PRState.class).getStates();

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(mapper.writeValueAsString(states));

    }

}