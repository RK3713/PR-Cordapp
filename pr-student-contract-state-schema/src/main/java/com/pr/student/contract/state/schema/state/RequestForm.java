package com.pr.student.contract.state.schema.state;

import com.pr.student.contract.state.schema.contract.RequestFormContract;
import com.pr.student.contract.state.schema.schema.RequestFormSchemaV1;
import net.corda.core.contracts.BelongsToContract;
import net.corda.core.contracts.LinearState;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.AbstractParty;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.schemas.PersistentState;
import net.corda.core.schemas.QueryableState;
import net.corda.core.serialization.ConstructorForDeserialization;
import net.corda.core.serialization.CordaSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ajinkya Pande and Rishi Kundu
 */

@CordaSerializable
@BelongsToContract(RequestFormContract.class)
public class RequestForm implements LinearState, QueryableState {
    private Boolean isWESRequested;
    private Boolean isTranscriptRequested;
    private String WESReferenceNumber;
    private String universityName;
    private String studentName;
    private UniqueIdentifier requestId;
    private String rollNumber;
    private String degreeName;
    private String duration;
    private String universityAddress;
    private String WESAddress;
    private Boolean isApproved;
    private String comments;
    private StudentInfoState studentInfoState;
    private AbstractParty wesParty;
    private AbstractParty consultantParty;
    private AbstractParty universityParty;
    private RequestStatus requestStatus;
    private String stateHash;

    public UniqueIdentifier getRequestId() {
        return requestId;
    }

    public String getStateHash() {
        return stateHash;
    }

    public void setStateHash(String stateHash) {
        this.stateHash = stateHash;
    }


    public AbstractParty getWesParty() {
        return wesParty;
    }

    public AbstractParty getConsultantParty() {
        return consultantParty;
    }

    public AbstractParty getUniversityParty() {
        return universityParty;
    }

    public StudentInfoState getStudentInfoState() {
        return studentInfoState;
    }

    public Boolean getWESRequested() {
        return isWESRequested;
    }

    public Boolean getTranscriptRequested() {
        return isTranscriptRequested;
    }

    public String getWESReferenceNumber() {
        return WESReferenceNumber;
    }

    public String getUniversityName() {
        return universityName;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getDegreeName() {
        return degreeName;
    }

    public String getDuration() {
        return duration;
    }

    public String getUniversityAddress() {
        return universityAddress;
    }

    public String getWESAddress() {
        return WESAddress;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public String getComments() {
        return comments;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    @ConstructorForDeserialization
    public RequestForm(UniqueIdentifier requestId, Boolean isWESRequested, Boolean isTranscriptRequested, String WESReferenceNumber, String universityName, String studentName, String degreeName, String duration, String universityAddress, String WESAddress, Boolean isApproved, String comments, StudentInfoState studentInfoState, AbstractParty wesParty, AbstractParty consultantParty, AbstractParty universityParty, RequestStatus requestStatus,String rollNumber) {
        this.isWESRequested = isWESRequested;
        this.isTranscriptRequested = isTranscriptRequested;
        this.WESReferenceNumber = WESReferenceNumber;
        this.universityName = universityName;
        this.studentName = studentName;
        this.requestId = requestId;
        this.rollNumber = rollNumber;
        this.degreeName = degreeName;
        this.duration = duration;
        this.universityAddress = universityAddress;
        this.WESAddress = WESAddress;
        this.isApproved = isApproved;
        this.comments = comments;
        this.studentInfoState = studentInfoState;
        this.wesParty = wesParty;
        this.consultantParty = consultantParty;
        this.universityParty = universityParty;
        this.requestStatus = requestStatus;
    }

    public RequestForm(UniqueIdentifier requestId, Boolean isWESRequested, Boolean isTranscriptRequested, String WESReferenceNumber, String universityName, String studentName, String degreeName, String duration, String universityAddress, String WESAddress, Boolean isApproved, String comments,AbstractParty wesParty, AbstractParty consultantParty, AbstractParty universityParty, RequestStatus requestStatus,String rollNumber) {
        this.isWESRequested = isWESRequested;
        this.isTranscriptRequested = isTranscriptRequested;
        this.WESReferenceNumber = WESReferenceNumber;
        this.universityName = universityName;
        this.studentName = studentName;
        this.requestId = requestId;
        this.rollNumber = rollNumber;
        this.degreeName = degreeName;
        this.duration = duration;
        this.universityAddress = universityAddress;
        this.WESAddress = WESAddress;
        this.isApproved = isApproved;
        this.comments = comments;
        //this.studentInfoState = studentInfoState;
        this.wesParty = wesParty;
        this.consultantParty = consultantParty;
        this.universityParty = universityParty;
        this.requestStatus = requestStatus;
    }

    public RequestForm(RequestForm other) {
        this.isWESRequested = other.isWESRequested;
        this.isTranscriptRequested = other.isTranscriptRequested;
        this.WESReferenceNumber = other.WESReferenceNumber;
        this.universityName = other.universityName;
        this.studentName = other.studentName;
        this.rollNumber = other.rollNumber;
        this.requestId = other.requestId;
        this.degreeName = other.degreeName;
        this.duration = other.duration;
        this.universityAddress = other.universityAddress;
        this.WESAddress = other.WESAddress;
        this.isApproved = other.isApproved;
        this.comments = other.comments;
        this.studentInfoState = other.studentInfoState;
        this.wesParty = other.wesParty;
        this.consultantParty = other.consultantParty;
        this.universityParty = other.universityParty;
        this.requestStatus = other.requestStatus;
        this.studentInfoState = other.studentInfoState;
    }

    public RequestForm(RequestForm other,RequestStatus requestStatus) {
        this.isWESRequested = other.isWESRequested;
        this.isTranscriptRequested = other.isTranscriptRequested;
        this.WESReferenceNumber = other.WESReferenceNumber;
        this.universityName = other.universityName;
        this.studentName = other.studentName;
        this.rollNumber = other.rollNumber;
        this.requestId = other.requestId;
        this.degreeName = other.degreeName;
        this.duration = other.duration;
        this.universityAddress = other.universityAddress;
        this.WESAddress = other.WESAddress;
        this.isApproved = other.isApproved;
        this.comments = other.comments;
        this.studentInfoState = other.studentInfoState;
        this.wesParty = other.wesParty;
        this.consultantParty = other.consultantParty;
        this.universityParty = other.universityParty;
        this.requestStatus = requestStatus;
        this.studentInfoState = other.studentInfoState;
    }

    public RequestForm(RequestForm other,StudentInfoState studentInfoState) {
        this.isWESRequested = other.isWESRequested;
        this.isTranscriptRequested = other.isTranscriptRequested;
        this.WESReferenceNumber = other.WESReferenceNumber;
        this.universityName = other.universityName;
        this.studentName = other.studentName;
        this.rollNumber = other.rollNumber;
        this.requestId = other.requestId;
        this.degreeName = other.degreeName;
        this.duration = other.duration;
        this.universityAddress = other.universityAddress;
        this.WESAddress = other.WESAddress;
        this.isApproved = other.isApproved;
        this.comments = other.comments;
        this.studentInfoState = other.studentInfoState;
        this.wesParty = other.wesParty;
        this.consultantParty = other.consultantParty;
        this.universityParty = other.universityParty;
        this.requestStatus = other.requestStatus;
        this.studentInfoState = studentInfoState;
    }


    @NotNull
    @Override
    public UniqueIdentifier getLinearId() {
        return requestId;
    }

    @NotNull
    @Override
    public Iterable<MappedSchema> supportedSchemas() {
        return Arrays.asList(new RequestFormSchemaV1());
    }

    @NotNull
    @Override
    public PersistentState generateMappedObject(MappedSchema schema) {
        if (schema instanceof RequestFormSchemaV1) {
            return new RequestFormSchemaV1.PersistentRequestForm(this);
        } else {
            throw new IllegalArgumentException("Unrecognised schema $schema");
        }
    }

    @NotNull
    @Override
    public List<AbstractParty> getParticipants() {
        return Arrays.asList(wesParty, consultantParty, universityParty);
    }
}
