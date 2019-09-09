package com.pr.webserver.controller;

import com.pr.common.data.PRFlowData;
import com.pr.common.exception.PRException;
import com.pr.contract.state.schema.contracts.PRContract;
import com.pr.contract.state.schema.states.PRState;
import com.pr.contract.state.schema.states.PRStatus;
import com.pr.server.common.bo.impl.PRBO;
import com.pr.server.common.controller.CommonController;
import com.pr.server.common.helper.PRControllerHelper;
import com.pr.wes.WesInitiator;
import net.corda.core.contracts.Amount;
import net.corda.core.contracts.StateAndRef;
import net.corda.core.messaging.FlowHandle;
import net.corda.core.transactions.SignedTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingLong;

/**
 * Define your API endpoints here.
 */
@RestController
@RequestMapping("/wes") // The paths for HTTP requests are relative to this base path.
@CrossOrigin
public class WesController extends CommonController {

    private final static Logger logger = LoggerFactory.getLogger(WesController.class);




    @CrossOrigin
    @PutMapping("/response")
    public ResponseEntity respondToPrRequest(@RequestParam(value ="requestId",required = true) String requestId, @RequestBody PRBO prbo) throws Exception{

            if (StringUtils.isEmpty(requestId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid requestId!");
            }

            PRContract.Commands command = new PRContract.Commands.RequestApproval();

            List<StateAndRef<PRState>> previousPrState = PRControllerHelper.getPrStateFromRequestId(requestId,connector.getRPCops());

            if (previousPrState == null || previousPrState.isEmpty()) {
                throw new PRException("PR Request with id: " + requestId + " doesn't exist please verify and try again!");
            }


            try {
                PRState newPRState = convertToPRStateForUpdate(previousPrState.get(0).getState().getData(), PRStatus.APPLICATION_SUBMITTED,prbo);
                FlowHandle<SignedTransaction> signedTransactionFlowHandle = connector.getRPCops().startFlowDynamic(
                        WesInitiator.class,
                        new PRFlowData(newPRState, previousPrState.get(0),command));
                SignedTransaction signedTransaction = signedTransactionFlowHandle.getReturnValue().get();

                return ResponseEntity.ok("PRState updated successfully with Txn ID: " + signedTransaction.getId().toString());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }

    }

    /**
     *
     * @return It returns the state by querying vault
     * @throws Exception
     */

    @CrossOrigin
    @GetMapping("/")
    public ResponseEntity getPRRequestDetails() throws Exception{

        List<StateAndRef<PRState>> states = connector.getRPCops().vaultQuery(PRState.class).getStates();
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(mapper.writeValueAsString(states));

    }




}