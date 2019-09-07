package com.pr.webserver.controller;


import com.pr.common.data.PRFlowData;
import com.pr.common.data.RequestFlowData;
import com.pr.consultant.initiator.ConsultantInitiator;
import com.pr.consultant.initiator.RequestFlowInitiator;
import com.pr.contract.state.schema.contracts.PRContract;
import com.pr.contract.state.schema.states.PRState;
import com.pr.contract.state.schema.states.PRStatus;
import com.pr.server.common.ServerConstant;
import com.pr.server.common.bo.impl.PRBO;
import com.pr.server.common.bo.impl.RequestFormBO;
import com.pr.server.common.controller.CommonController;
import com.pr.server.common.exception.PRServerException;
import com.pr.server.common.helper.PRControllerHelper;
import com.pr.student.contract.state.schema.contract.RequestFormContract;
import com.pr.student.contract.state.schema.state.RequestForm;
import com.pr.student.contract.state.schema.state.RequestStatus;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

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

        Set<Party> partyFrom = connector.getRPCops().partiesFromName(prData.getConsultantParty(), false);
        Iterator<Party> parties = partyFrom.iterator();

        while (parties.hasNext()) {
            consultantParty = parties.next();
        }

        logger.info(" ********************************************** ");
        logger.info(" Consultant Party :" + consultantParty);
        logger.info(" Wes Party Party :" + wesParty);
        logger.info(" University Party :" + universityParty);
        logger.info(" ********************************************** ");

        // Creating state
        prState = convertToPRState(prData, PRStatus.ACCOUNT_CREATED, consultantParty, wesParty, universityParty);

        try {
            FlowHandle<SignedTransaction> flowHandle = connector.getRPCops().startFlowDynamic
                    (ConsultantInitiator.class, new PRFlowData(prState,
                            previousPRRequest, command));
            signedTransaction = flowHandle.getReturnValue().get();
            logger.info(String.format("signed Tx id: %s", signedTransaction.getId().toString()));
            return ResponseEntity.ok("PR Request created with Txn Id: " + signedTransaction.getId().toString());
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage(), e.getCause());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @CrossOrigin
    @GetMapping("/")
    public ResponseEntity getPRRequestDetails() throws Exception {

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
    }

    private AbstractParty getPartyFromFullName(String partyName) {
        CordaX500Name x500Name = CordaX500Name.parse(partyName);
        return connector.getRPCops().wellKnownPartyFromX500Name(x500Name);
    }

    @CrossOrigin
    @PostMapping("raiseAcademicFormRequest/{command}")
    public ResponseEntity raiseBookingRequest(@PathVariable("command") String command,
                                              @RequestBody RequestFormBO requestFormBO) {
        RequestFormContract.Commands contractCommand;
        SignedTransaction signedTransaction = null;
        AbstractParty consultantParty = null;
        AbstractParty wesParty = null;
        AbstractParty universityParty = null;
        if (command.equalsIgnoreCase(ServerConstant.CREATE_COMMAND)) {
            contractCommand = new RequestFormContract.Commands.CREATE();
            logger.info("Contract Command" + contractCommand);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unknown Command! " + command);
        }
        RequestForm requestFormState;
        StateAndRef<RequestForm> previousrequestFormState = null;
        if (requestFormBO == null) {
            throw new IllegalArgumentException("Invalid Request!");
        }
        consultantParty = getParty(requestFormBO.getConsultantParty());
        wesParty = getParty(requestFormBO.getWesParty());
        universityParty = getParty(requestFormBO.getUniversityParty());
        requestFormState = convertToRequestFormState(requestFormBO,consultantParty,wesParty,universityParty,RequestStatus.APPLICATION_SUBMITTED);

        try {
            FlowHandle<SignedTransaction> flowHandle = connector.getRPCops().startFlowDynamic
                    (RequestFlowInitiator.class, new RequestFlowData(requestFormState,
                            previousrequestFormState, contractCommand));
            signedTransaction = flowHandle.getReturnValue().get();
            logger.info(String.format("signed Tx id: %s", signedTransaction.getId().toString()));
            return ResponseEntity.ok("Booking Request created with Txn Id: " + signedTransaction.getId().toString());
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage(), e.getCause());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @CrossOrigin
    @PostMapping("confirmTranscriptDetails/{requestId}")
    public ResponseEntity confirmTranscriptDetails(@PathVariable("requestId") String requestId,
                                              @RequestParam(value = "requestStatus", required = false, defaultValue = "CONFIRMED") String requestStatus) {
        RequestFormContract.Commands contractCommand;
        SignedTransaction signedTransaction = null;

        if (StringUtils.isEmpty(requestId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("RequestId! is missing. Please enter a valid booking Id.");
        }

        contractCommand = new RequestFormContract.Commands.UPDATE();

        List<StateAndRef<RequestForm>> previousRequest = PRControllerHelper.getRequestFormStateFromLinearId(requestId,connector.getRPCops());

        if (previousRequest == null || previousRequest.isEmpty()) {
            throw new PRServerException("Request Id with id: " + requestId + " doesn't exist please verify and try again!");
        }

        if(!previousRequest.get(0).getState().getData().getRequestStatus().equals(RequestStatus.ADDED_TRANSCRIPT_DETAILS)) {
            throw new IllegalArgumentException("Invalid request status, Expected Status : " + RequestStatus.ADDED_TRANSCRIPT_DETAILS);
        }

        RequestForm newRequestFormState = new RequestForm(previousRequest.get(0).getState().getData(),RequestStatus.CONFIRMED);

        try {
            FlowHandle<SignedTransaction> signedTransactionFlowHandle = connector.getRPCops().startFlowDynamic(RequestFlowInitiator.class,
                    new RequestFlowData(newRequestFormState, previousRequest.get(0),contractCommand));

            signedTransaction = signedTransactionFlowHandle.getReturnValue().get();
            return ResponseEntity.ok("Transcript Details confirmed successfully with Txn ID: " + signedTransaction.getId().toString());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    private AbstractParty getParty(String abstractParty) {
        AbstractParty party = null;
        Set<Party> partyFrom = connector.getRPCops().partiesFromName(abstractParty.toString(), false);
        Iterator<Party> parties = partyFrom.iterator();

        while (parties.hasNext()) {
            party = parties.next();
        }
        return party;
    }

}