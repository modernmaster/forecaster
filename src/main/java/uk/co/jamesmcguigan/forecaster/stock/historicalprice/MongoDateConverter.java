package uk.co.jamesmcguigan.forecaster.stock.historicalprice;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class MongoDateConverter extends JsonDeserializer {

    private static final String DATE = "$date";
    private static final String NUMBER_LONG = "$numberLong";

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.readValueAsTree();
        long dateValue = node.get(DATE).get(NUMBER_LONG).asLong();
        return new Date(dateValue);
    }
}
