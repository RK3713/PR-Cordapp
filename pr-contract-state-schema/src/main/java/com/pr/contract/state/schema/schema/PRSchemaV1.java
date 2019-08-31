package com.pr.contract.state.schema.schema;

import com.google.common.collect.ImmutableList;
import com.pr.contract.state.schema.states.PRState;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.AbstractParty;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.schemas.PersistentState;
import net.corda.core.schemas.PersistentStateRef;
import net.corda.core.serialization.ConstructorForDeserialization;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

public class PRSchemaV1 extends MappedSchema {

    public PRSchemaV1() {
        super(PRSchema.class, 1, ImmutableList.of(PersistentPR.class));
    }

    @Entity
    @Table(name = "PR")
    public static class PersistentPR extends PersistentState{

        @Column(name = "Id")
        private String id;

        @Column(name = "FirstName")
        private String firstName;

        @Column(name = "LastName")
        private String lastName;

        @Column(name = "Address")
        private String address;

        @Column(name = "Gender")
        private String gender;

        @Column(name = "DOB")
        private LocalDateTime dob;

        @Column(name = "Language")
        private String language;

        @Column(name = "EyeColour")
        private String eyeColour;

        @Column(name = "MartialStatus")
        private String martialStatus;

        @Column(name = "Email")
        private String email;

        @Column(name = "Income")
        private Double income;

        @Column(name = "UCINumber")
        private String uciNumber;

        @Column(name = "CertificateNumber")
        private String certificateNumber;

        @Column(name = "WesAck")
        private Boolean wesAck;

        @Column(name = "ConsultantParty")
        private AbstractParty consultantParty;

        @Column(name = "WesParty")
        private AbstractParty wesParty;

        @Column(name = "UniversityParty")
        private AbstractParty universityParty;

        @ConstructorForDeserialization
        public PersistentPR(UniqueIdentifier id, String firstName, String lastName, String address, String gender, LocalDateTime dob, String language, String eyeColour, String martialStatus, String email, Double income, String uciNumber, String certificateNumber, Boolean wesAck, AbstractParty consultantParty, AbstractParty wesParty, AbstractParty universityParty) {
            super();
            this.id = id.toString();
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
            this.uciNumber = uciNumber;
            this.certificateNumber = certificateNumber;
            this.wesAck = wesAck;
            this.consultantParty = consultantParty;
            this.wesParty = wesParty;
            this.universityParty = universityParty;
        }


        public PersistentPR(PRState prState) {
            super();
            this.id = prState.getId().toString();
            this.firstName = prState.getFirstName();
            this.lastName = prState.getLastName();
            this.address = prState.getAddress();
            this.gender = prState.getGender();
            this.dob = prState.getDob();
            this.language = prState.getLanguage();
            this.eyeColour = prState.getEyeColour();
            this.martialStatus = prState.getMartialStatus();
            this.email = prState.getEmail();
            this.income = prState.getIncome();
            this.uciNumber = prState.getUciNnumber();
            this.certificateNumber = prState.getCertificateNumber();
            this.wesAck = prState.getWesAck();
            this.consultantParty = prState.getConsultantParty();
            this.wesParty = prState.getWesParty();
            this.universityParty = prState.getUniversityParty();
        }



    }

}
