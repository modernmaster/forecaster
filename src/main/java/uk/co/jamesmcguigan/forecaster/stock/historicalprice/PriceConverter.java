package uk.co.jamesmcguigan.forecaster.stock.historicalprice;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.jamesmcguigan.forecaster.Application;

public class PriceConverter extends JsonDeserializer {

    private static final String NUMBER_INT = "$numberInt";
    private static final String NUMBER_DOUBLE = "$numberDouble";
    private static final String UNABLE_TO_DESERIALIZE = "Unable to deserialize: %s";

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {

        JsonNode node = jsonParser.readValueAsTree();
        if (node.has(NUMBER_INT)) {
            return node.get(NUMBER_INT).asDouble();
        } else if (node.has(NUMBER_DOUBLE)) {
            return node.get(NUMBER_DOUBLE).asDouble();
        }
        Application.logger.error(String.format(UNABLE_TO_DESERIALIZE, node));
        return -1;
    }
}
