package com.pr.webserver.controller;

import com.pr.common.data.RequestFlowData;
import com.pr.server.common.bo.impl.*;
import com.pr.server.common.controller.CommonController;
import com.pr.server.common.exception.PRServerException;
import com.pr.server.common.helper.PRControllerHelper;
import com.pr.student.contract.state.schema.contract.RequestFormContract;
import com.pr.student.contract.state.schema.state.*;
import com.pr.university.initiator.RequestFlowResponseInitiator;
import net.corda.core.contracts.StateAndRef;
import net.corda.core.messaging.FlowHandle;
import net.corda.core.transactions.SignedTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author Ajinkya Pande & Rishi Kundu
 */

@RestController
@RequestMapping("/university") // The paths for HTTP requests are relative to this base path.
@CrossOrigin
public class UniversityController extends CommonController {

    private final static Logger logger = LoggerFactory.getLogger(UniversityController.class);


    @CrossOrigin
    @GetMapping(value = "/hello", produces = "text/plain")
    private String uniName() {
        return "Hello University";
    }

    /**
     *
     * @param requestId is a UUID which helps to query the state from vault
     * @param requestStatus is a parameter to change request status (APPLICATION_READY_FOR_WES_VERIFICATION etc.)
     * @return It returns status whether Transaction is successful or not
     */

    @CrossOrigin
    @PostMapping("TranscriptDetailsReadyForWES/{requestId}")
    public ResponseEntity TranscriptDetailsReadyForWES(@PathVariable("requestId") String requestId,
                                                       @RequestParam(value = "requestStatus", required = false,
                                                               defaultValue = "APPLICATION_READY_FOR_WES_VERIFICATION") String requestStatus) {
        RequestFormContract.Commands contractCommand;
        SignedTransaction signedTransaction;

        if (StringUtils.isEmpty(requestId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("RequestId! is missing. Please enter a valid booking Id.");
        }

        contractCommand = new RequestFormContract.Commands.UPDATE();

        List<StateAndRef<RequestForm>> previousRequest = PRControllerHelper.getRequestFormStateFromLinearId(requestId, connector.getRPCops());

        if (previousRequest == null || previousRequest.isEmpty()) {
            throw new PRServerException("Request Id with id: " + requestId + " doesn't exist please verify and try again!");
        }

        if (!previousRequest.get(0).getState().getData().getRequestStatus().equals(RequestStatus.CONFIRMED)) {
            throw new IllegalArgumentException("Invalid request status, Expected Previous Status : " + RequestStatus.CONFIRMED);
        }

        RequestForm newRequestFormState = new RequestForm(previousRequest.get(0).getState().getData(), RequestStatus.APPLICATION_READY_FOR_WES_VERIFICATION);

        try {
            FlowHandle<SignedTransaction> signedTransactionFlowHandle = connector.getRPCops().startFlowDynamic(RequestFlowResponseInitiator.class,
                    new RequestFlowData(newRequestFormState, previousRequest.get(0), contractCommand));

            signedTransaction = signedTransactionFlowHandle.getReturnValue().get();
            return ResponseEntity.ok("Transcript details ready for WES verification. Txn ID: " + signedTransaction.getId().toString());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    /***
     *
     * @param requestId is a UUID which helps to query the state from vault
     * @param studentInfoBO is a json object which we provide as an input to our post api
     * @param requestStatus is a parameter to change request status (ADDED_TRANSCRIPT_DETAILS etc.)
     * @return It returns status whether Transcript details are added or not
     */

    @CrossOrigin
    @PostMapping("addTranscriptDetails/{requestId}")
    public ResponseEntity addTranscriptDetails(@PathVariable("requestId") String requestId,
                                               @RequestBody StudentInfoBO studentInfoBO,
                                               @RequestParam(value = "requestStatus", required = false,
                                                       defaultValue = "ADDED_TRANSCRIPT_DETAILS") String requestStatus) {

        RequestFormContract.Commands contractCommand;
        SignedTransaction signedTransaction;

        if (StringUtils.isEmpty(requestId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("RequestId! is missing. Please enter a valid Id.");
        }

        contractCommand = new RequestFormContract.Commands.UPDATE();

        List<StateAndRef<RequestForm>> previousRequest = PRControllerHelper.getRequestFormStateFromLinearId(requestId, connector.getRPCops());

        if (previousRequest == null || previousRequest.isEmpty()) {
            throw new PRServerException("Request Id with id: " + requestId + " doesn't exist please verify and try again!");
        }

        if (!previousRequest.get(0).getState().getData().getRequestStatus().equals(RequestStatus.APPLICATION_SUBMITTED)) {
            throw new IllegalArgumentException("Invalid request status, Expected Previous Status : " + RequestStatus.APPLICATION_SUBMITTED);
        }

        DegreeDetailsBO degreeDetailsBO = studentInfoBO.getDegreeDetailsBO();
        DegreeDetails degreeDetails = new DegreeDetails(degreeDetailsBO.getDegreeName(),
                degreeDetailsBO.getUniversityName(), degreeDetailsBO.getCompletionStatus(),
                degreeDetailsBO.getPassingYear(), degreeDetailsBO.getPassingDivision(),
                degreeDetailsBO.getFullName(), degreeDetailsBO.getFatherName(), degreeDetailsBO.getSpecializationField(),
                degreeDetailsBO.getRollNumber());

        UniversityBO universityBO = studentInfoBO.getUniversityBO();
        University university = new University(universityBO.getUniversityName(), universityBO.getAddress(),
                universityBO.getUniversityType(), universityBO.getContactNumber());

        TranscriptBO transcriptBO = studentInfoBO.getTranscriptBO();
        List<SemesterBO> semesterBOList = transcriptBO.getSemester();
        List<Semester> semesterList = new ArrayList<>();
        for (SemesterBO semesterBO : semesterBOList) {
            List<Subjects> subjectsList = new ArrayList<>();
            for (SubjectBO subjectBO : semesterBO.getSubbjectsList()) {
                Subjects subjects = new Subjects(subjectBO.getSubjectName(), subjectBO.getMarksObtained(), subjectBO.getPassed());
                subjectsList.add(subjects);
            }
            Semester semester = new Semester(semesterBO.getSemesterNumber(), subjectsList, semesterBO.getResultDeclaredOnDate());
            semesterList.add(semester);
        }
        Transcript transcript = new Transcript(transcriptBO.getRollNumber(),transcriptBO.getName(),
                transcriptBO.getUniversityName(),transcriptBO.getDateOfCompletion(),transcriptBO.getDegreeName(),
                transcriptBO.getPass(),semesterList);


        StudentInfoState studentInfoState = new StudentInfoState(studentInfoBO.getRollNumber(),
                studentInfoBO.getWESReferenceNumber(), studentInfoBO.getFirstName(),
                studentInfoBO.getLastName(), studentInfoBO.getCourseDuration(),
                studentInfoBO.getDegreeStatus(), degreeDetails,transcript,university);

        RequestForm newRequestFormState = new RequestForm(previousRequest.get(0).getState().getData(), RequestStatus.APPLICATION_READY_FOR_WES_VERIFICATION);

        try {
            FlowHandle<SignedTransaction> signedTransactionFlowHandle = connector.getRPCops().startFlowDynamic(RequestFlowResponseInitiator.class,
                    new RequestFlowData(newRequestFormState, previousRequest.get(0), contractCommand));

            signedTransaction = signedTransactionFlowHandle.getReturnValue().get();
            return ResponseEntity.ok("Transcript details added for confirmation with Txn ID: " + signedTransaction.getId().toString());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}