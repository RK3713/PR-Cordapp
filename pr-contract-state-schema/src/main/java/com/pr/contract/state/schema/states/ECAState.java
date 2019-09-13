package com.pr.contract.state.schema.states;

import net.corda.core.serialization.ConstructorForDeserialization;
import net.corda.core.serialization.CordaSerializable;

@CordaSerializable
public class ECAState {
    private String nameOnCredential;
    private String credentialAuth;
    private String country;
    private String credential;
    private String year;
    private String awardedBy;
    private String status;
    private String major;
    private String equivalency;

    public String getNameOnCredential() {
        return nameOnCredential;
    }

    public String getCredentialAuth() {
        return credentialAuth;
    }

    public String getCountry() {
        return country;
    }

    public String getCredential() {
        return credential;
    }

    public String getYear() {
        return year;
    }

    public String getAwardedBy() {
        return awardedBy;
    }

    public String getStatus() {
        return status;
    }

    public String getMajor() {
        return major;
    }

    public String getEquivalency() {
        return equivalency;
    }

    @ConstructorForDeserialization
    public ECAState(String nameOnCrdential, String credentialAuth, String country, String crdential, String year,
                    String awardedBy, String status, String major, String equivalency) {
        this.nameOnCredential = nameOnCrdential;
        this.credentialAuth = credentialAuth;
        this.country = country;
        this.credential = crdential;
        this.year = year;
        this.awardedBy = awardedBy;
        this.status = status;
        this.major = major;
        this.equivalency = equivalency;
    }

    @Override
    public String toString() {
        return "ECAState{" +
                "nameOnCrdential='" + nameOnCredential + '\'' +
                ", credentialAuth='" + credentialAuth + '\'' +
                ", country='" + country + '\'' +
                ", crdential='" + credential + '\'' +
                ", year='" + year + '\'' +
                ", awardedBy='" + awardedBy + '\'' +
                ", status='" + status + '\'' +
                ", major='" + major + '\'' +
                ", equivalency='" + equivalency + '\'' +
                '}';
    }
}
