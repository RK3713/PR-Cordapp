package com.pr.contract.state.schema.states;


import com.pr.contract.state.schema.contracts.PRContract;
import com.pr.contract.state.schema.schema.PRSchemaV1;
import net.corda.core.contracts.BelongsToContract;
import net.corda.core.contracts.LinearState;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.AbstractParty;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.schemas.PersistentState;
import net.corda.core.schemas.QueryableState;
import net.corda.core.serialization.ConstructorForDeserialization;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

// *********
// * State *
// *********
@BelongsToContract(PRContract.class)
public class PRState implements LinearState, QueryableState {

    private String firstName;
    private String lastName;
    private String courseName;
    private String courseDuration;
    private String university;
    private UniqueIdentifier wesReferenceNumber;
    private String email;
    private PRStatus prStatus;
    private AbstractParty consultantParty;
    private AbstractParty wesParty;
    private AbstractParty universityParty;


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseDuration() {
        return courseDuration;
    }

    public String getUniversity() {
        return university;
    }

    public UniqueIdentifier getWesReferenceNumber() {
        return wesReferenceNumber;
    }

    public String getEmail() {
        return email;
    }

    public PRStatus getPrStatus() {
        return prStatus;
    }



    public AbstractParty getConsultantParty() {
        return consultantParty;
    }

    public AbstractParty getWesParty() {
        return wesParty;
    }

    public AbstractParty getUniversityParty() {
        return universityParty;
    }

    @ConstructorForDeserialization
    public PRState(String firstName, String lastName, String courseName, String courseDuration, String university, UniqueIdentifier wesReferenceNumber, String email, PRStatus prStatus, AbstractParty consultantParty, AbstractParty wesParty, AbstractParty universityParty) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.courseName = courseName;
        this.courseDuration = courseDuration;
        this.university = university;
        this.wesReferenceNumber = wesReferenceNumber;
        this.email = email;
        this.prStatus = prStatus;
        this.consultantParty = consultantParty;
        this.wesParty = wesParty;
        this.universityParty = universityParty;
    }



    public PRState(String firstName, String lastName, String courseName, String courseDuration, String university, String email, PRStatus prStatus, AbstractParty consultantParty, AbstractParty wesParty, AbstractParty universityParty) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.courseName = courseName;
        this.courseDuration = courseDuration;
        this.university = university;
        this.wesReferenceNumber = new UniqueIdentifier();
        this.email = email;
        this.prStatus = prStatus;
        this.consultantParty = consultantParty;
        this.wesParty = wesParty;
        this.universityParty = universityParty;
    }





    @NotNull
    @Override
    public UniqueIdentifier getLinearId() {
        return getWesReferenceNumber();
    }

    @NotNull
    @Override
    public PersistentState generateMappedObject(@NotNull MappedSchema schema) {

        if (schema instanceof PRSchemaV1){
            return new PRSchemaV1.PersistentPR(this);
        }
        else{
            throw new IllegalArgumentException("Unrecognised schema $schema");
        }

    }

    @NotNull
    @Override
    public Iterable<MappedSchema> supportedSchemas() {
        return Arrays.asList(new PRSchemaV1());
    }

    @NotNull
    @Override
    public List<AbstractParty> getParticipants() {
        return Arrays.asList(consultantParty,wesParty);
    }
}