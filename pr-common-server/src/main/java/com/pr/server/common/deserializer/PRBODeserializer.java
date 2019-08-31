package com.pr.server.common.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.pr.server.common.bo.impl.PRBO;
import java.io.IOException;
import java.time.ZonedDateTime;

public class PRBODeserializer extends JsonDeserializer<PRBO> {


    @Override
    public PRBO deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        return new PRBO(node.get("firstName")!=null ? node.get("firstName").asText(): null,
                node.get("lastName")!=null ? node.get("lastName").asText(): null,
                node.get("address")!=null ? node.get("address").asText(): null,
                node.get("gender")!=null ? node.get("gender").asText(): null,
                node.get("dob")!=null ? (ZonedDateTime.parse(node.get("dob").asText())).toLocalDateTime(): null,
                node.get("language")!=null ? node.get("language").asText(): null,
                node.get("eyeColour")!=null ? node.get("eyeColour").asText(): null,
                node.get("martialStatus")!=null ? node.get("martialStatus").asText(): null,
                node.get("email")!=null ? node.get("email").asText(): null,
                node.get("income")!=null ? node.get("income").asDouble(): null,
                node.get("uciNumber")!=null ? node.get("uciNumber").asText(): null,
                node.get("certificateNumber")!=null ? node.get("certificateNumber").asText(): null,
                node.get("wesAck").asBoolean(),
                node.get("consultantParty")!=null ? node.get("consultantParty").asText(): null,
                node.get("wesParty")!=null ? node.get("wesParty").asText(): null,
                node.get("universityParty")!=null ? node.get("universityParty").asText(): null
                );

    }
}


