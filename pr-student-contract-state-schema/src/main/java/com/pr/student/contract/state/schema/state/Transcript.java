package com.pr.student.contract.state.schema.state;

import net.corda.core.serialization.ConstructorForDeserialization;
import net.corda.core.serialization.CordaSerializable;

import java.util.List;

/**
 * @author Ajinkya Pande & Rishi Kundu
 */

@CordaSerializable
public class Transcript {
    private String rollNumber;
    private String name;
    private String universityName;
    private String dateOfCompletion;
    private String degreeName;
    private Boolean isPass;
    private List<Semester> semester;

    @ConstructorForDeserialization
    public Transcript(String rollNumber, String name, String universityName, String dateOfCompletion, String degreeName, Boolean isPass, List<Semester> semester) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.universityName = universityName;
        this.dateOfCompletion = dateOfCompletion;
        this.degreeName = degreeName;
        this.isPass = isPass;
        this.semester = semester;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getName() {
        return name;
    }

    public String getUniversityName() {
        return universityName;
    }

    public String getDateOfCompletion() {
        return dateOfCompletion;
    }

    public String getDegreeName() {
        return degreeName;
    }

    public Boolean getPass() {
        return isPass;
    }

    public List<Semester> getSemester() {
        return semester;
    }
}
