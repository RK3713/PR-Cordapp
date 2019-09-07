package com.pr.server.common.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.pr.server.common.bo.impl.DegreeDetailsBO;
import com.pr.server.common.bo.impl.RequestFormBO;

import java.io.IOException;

public class RequestBODeserializer extends JsonDeserializer<RequestFormBO> {
    @Override
    public RequestFormBO deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);
        RequestFormBO requestFormBO = new RequestFormBO();

        requestFormBO.setWESRequested(node.get("isWESRequested") != null ? Boolean.valueOf(node.get("isWESRequested").asText()) : null);
        requestFormBO.setTranscriptRequested(node.get("isTranscriptRequested") != null ? Boolean.valueOf(node.get("isTranscriptRequested").asText()) : null);
        requestFormBO.setWESReferenceNumber(node.get("WESReferenceNumber") != null ? node.get("WESReferenceNumber").asText() : null);
        requestFormBO.setUniversityName(node.get("universityName") != null ? node.get("universityName").asText() : null);
        requestFormBO.setStudentName(node.get("studentName") != null ? node.get("studentName").asText() : null);
        requestFormBO.setRollNumber(node.get("rollNumber") != null ? node.get("rollNumber").asText() : null);
        requestFormBO.setDegreeName(node.get("degreeName") != null ? node.get("degreeName").asText() : null);
        requestFormBO.setDuration(node.get("duration") != null ? node.get("duration").asText() : null);
        requestFormBO.setUniversityAddress(node.get("universityAddress") != null ? node.get("universityAddress").asText() : null);
        //requestFormBO.setApproved(node.get("isApproved") != null ? Boolean.valueOf(node.get("isApproved").asText()) : null);
        requestFormBO.setComments(node.get("comments") != null ? node.get("comments").asText() : null);
        requestFormBO.setWESAddress(node.get("wesAddress") != null ? node.get("WESAddress").asText() : null);
        requestFormBO.setWesParty(node.get("wesParty") != null ? node.get("wesParty").asText() : null);
        requestFormBO.setUniversityParty(node.get("universityParty") != null ? node.get("universityParty").asText() : null);
        requestFormBO.setConsultantParty(node.get("consultantParty") != null ? node.get("consultantParty").asText() : null);

        return requestFormBO;
    }
}
