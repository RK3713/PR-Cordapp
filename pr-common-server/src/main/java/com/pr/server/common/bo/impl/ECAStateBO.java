package com.pr.server.common.bo.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pr.server.common.bo.BusinessObject;
import com.pr.server.common.deserializer.ECABODeserializer;
import com.pr.server.common.deserializer.RequestBODeserializer;
import net.corda.core.serialization.CordaSerializable;

/**
 * @author Ajinkya Pande & Rishi Kundu
 */
@CordaSerializable
@JsonDeserialize(using = ECABODeserializer.class)
public class ECAStateBO implements BusinessObject {
    private String nameOnCrdential;
    private String credentialAuth;
    private String country;
    private String crdential;
    private String year;
    private String awardedBy;
    private String status;
    private String major;
    private String equivalency;

    public String getNameOnCrdential() {
        return nameOnCrdential;
    }

    public void setNameOnCrdential(String nameOnCrdential) {
        this.nameOnCrdential = nameOnCrdential;
    }

    public String getCredentialAuth() {
        return credentialAuth;
    }

    public void setCredentialAuth(String credentialAuth) {
        this.credentialAuth = credentialAuth;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCrdential() {
        return crdential;
    }

    public void setCrdential(String crdential) {
        this.crdential = crdential;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAwardedBy() {
        return awardedBy;
    }

    public void setAwardedBy(String awardedBy) {
        this.awardedBy = awardedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getEquivalency() {
        return equivalency;
    }

    public void setEquivalency(String equivalency) {
        this.equivalency = equivalency;
    }
}
