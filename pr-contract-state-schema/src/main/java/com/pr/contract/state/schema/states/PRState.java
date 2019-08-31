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

    private UniqueIdentifier id;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;
    LocalDateTime dob;
    private String language;
    private String eyeColour;
    private String martialStatus;
    private String email;
    private Double income;
    private String uciNnumber;
    private String certificateNumber;
    private Boolean wesAck;

    private AbstractParty consultantParty;
    private AbstractParty wesParty;
    private AbstractParty universityParty;


    public UniqueIdentifier getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public LocalDateTime getDob() {
        return dob;
    }

    public String getLanguage() {
        return language;
    }

    public String getEyeColour() {
        return eyeColour;
    }

    public String getMartialStatus() {
        return martialStatus;
    }

    public String getEmail() {
        return email;
    }

    public double getIncome() {
        return income;
    }

    public String getUciNnumber() {
        return uciNnumber;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public Boolean getWesAck() { return wesAck; }


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
    public PRState(UniqueIdentifier id, String firstName, String lastName, String address, String gender, LocalDateTime dob, String language, String eyeColour, String martialStatus, String email, Double income, String uciNnumber, String certificateNumber, Boolean wesAck, AbstractParty consultantParty, AbstractParty wesParty, AbstractParty universityParty) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.gender = gender;
        this.dob = dob;
        this.language = language;
        this.eyeColour = eyeColour;
        this.martialStatus = martialStatus;
        this.email = email;
        this.income = income;
        this.uciNnumber = uciNnumber;
        this.certificateNumber = certificateNumber;
        this.wesAck = wesAck;
        this.consultantParty = consultantParty;
        this.wesParty = wesParty;
        this.universityParty = universityParty;
    }

    public PRState(String firstName, String lastName, String address, String gender, LocalDateTime dob, String language, String eyeColour, String martialStatus, String email, Double income, String uciNnumber, String certificateNumber, Boolean wesAck, AbstractParty consultantParty, AbstractParty wesParty, AbstractParty universityParty) {
        this.id = new UniqueIdentifier();
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.gender = gender;
        this.dob = dob;
        this.language = language;
        this.eyeColour = eyeColour;
        this.martialStatus = martialStatus;
        this.email = email;
        this.income = income;
        this.uciNnumber = uciNnumber;
        this.certificateNumber = certificateNumber;
        this.wesAck = wesAck;
        this.consultantParty = consultantParty;
        this.wesParty = wesParty;
        this.universityParty = universityParty;
    }

//    public PRState(String firstName, String lastName, String address, String gender, LocalDateTime dob, String language, String eyeColour, String martialStatus, String email, Double income, String uciNnumber, String certificateNumber, Boolean wesAck, AbstractParty consultantParty, AbstractParty wesParty, AbstractParty universityParty) {
//        this.id = new UniqueIdentifier();
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.address = address;
//        this.gender = gender;
//        this.dob = dob;
//        this.language = language;
//        this.eyeColour = eyeColour;
//        this.martialStatus = martialStatus;
//        this.email = email;
//        this.income = income;
//        this.uciNnumber = uciNnumber;
//        this.certificateNumber = certificateNumber;
//        this.wesAck = wesAck;
//        this.consultantParty = consultantParty;
//        this.wesParty = wesParty;
//        this.universityParty = universityParty;
//    }




    @NotNull
    @Override
    public UniqueIdentifier getLinearId() {
        return getId();
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