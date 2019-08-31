package com.pr.server.common.bo.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pr.server.common.bo.BusinessObject;
import com.pr.server.common.deserializer.PRBODeserializer;
import kotlinx.html.B;
import net.corda.core.serialization.ConstructorForDeserialization;
import net.corda.core.serialization.CordaSerializable;

import java.time.LocalDateTime;

@JsonSerialize
@CordaSerializable
@JsonDeserialize(using = PRBODeserializer.class)
public class PRBO implements BusinessObject {


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

    private String consultantParty;
    private String wesParty;
    private String universityParty;



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDateTime getDob() {
        return dob;
    }

    public void setDob(LocalDateTime dob) {
        this.dob = dob;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getEyeColour() {
        return eyeColour;
    }

    public void setEyeColour(String eyeColour) {
        this.eyeColour = eyeColour;
    }

    public String getMartialStatus() {
        return martialStatus;
    }

    public void setMartialStatus(String martialStatus) {
        this.martialStatus = martialStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public String getUciNnumber() {
        return uciNnumber;
    }

    public void setUciNnumber(String uciNnumber) {
        this.uciNnumber = uciNnumber;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public Boolean getWesAck() { return wesAck; }

    public void setWesAck(Boolean wesAck) { this.wesAck = wesAck; }



    public String getConsultantParty() {
        return consultantParty;
    }

    public void setConsultantParty(String consultantParty) {
        this.consultantParty = consultantParty;
    }

    public String getWesParty() {
        return wesParty;
    }

    public void setWesParty(String wesParty) {
        this.wesParty = wesParty;
    }

    public String getUniversityParty() {
        return universityParty;
    }

    public void setUniversityParty(String universityParty) {
        this.universityParty = universityParty;
    }


    @ConstructorForDeserialization
    public PRBO(String firstName, String lastName, String address, String gender, LocalDateTime dob, String language, String eyeColour, String martialStatus, String email, Double income, String uciNnumber, String certificateNumber, Boolean wesAck, String consultantParty, String wesParty, String universityParty) {
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

}
