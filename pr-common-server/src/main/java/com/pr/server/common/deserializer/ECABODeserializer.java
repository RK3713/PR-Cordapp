package com.pr.server.common.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.pr.server.common.bo.impl.ECAStateBO;
import com.pr.server.common.bo.impl.PRBO;
import com.pr.server.common.bo.impl.RequestFormBO;

import java.io.IOException;

/**
 * @author Ajinkya Pande & Rishi Kundu
 */

public class ECABODeserializer extends JsonDeserializer<ECAStateBO> {
    @Override
    public ECAStateBO deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);
        ECAStateBO ecaStateBO = new ECAStateBO();




        return null;
    }
}
