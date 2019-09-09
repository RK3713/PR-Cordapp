package com.pr.student.contract.state.schema.state;

import net.corda.core.serialization.ConstructorForDeserialization;
import net.corda.core.serialization.CordaSerializable;

/**
 * @author Ajinkya Pande & Rishi Kundu
 */

@CordaSerializable
public class Subjects {
    private String subjectName;
    private Double marksObtained;
    private Boolean isPassed;

    @ConstructorForDeserialization
    public Subjects(String subjectName, Double marksObtained, Boolean isPassed) {
        this.subjectName = subjectName;
        this.marksObtained = marksObtained;
        this.isPassed = isPassed;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public Double getMarksObtained() {
        return marksObtained;
    }

    public Boolean getPassed() {
        return isPassed;
    }
}
