package uk.co.jamesmcguigan.forecaster.stock.historicalprice;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.joda.time.DateTime;
import uk.co.jamesmcguigan.forecaster.StockServiceApplication;

public class DateConverter extends JsonDeserializer<Date> {

    private static final String DATE = "$date";
    private static final String NUMBER_LONG = "$numberLong";
    private static final String UNABLE_TO_DESERIALIZE = "Unable to deserialize: %s";
    private static final String DESERIALIZATION_ERROR_IN_DATE_CONVERTER = "Deserialization error in Date Converter";

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        try {
            long dateValue = deserializationContext.getParser().getLongValue();
            return new Date(dateValue);
        } catch (Exception ex) {
            try {
                return deserializeMongodbDate(jsonParser);
            } catch (Exception e) {
                StockServiceApplication.logger.error(DESERIALIZATION_ERROR_IN_DATE_CONVERTER, e);
            }
        }
        return new Date(Long.MIN_VALUE);
    }

    private Date deserializeMongodbDate(JsonParser jsonParser) throws IOException {
        JsonNode node = jsonParser.readValueAsTree();
        if (node.has(DATE)) {
            long dateValue = node.get(DATE).get(NUMBER_LONG).asLong();
            return new Date(dateValue);
        }
        throw new IOException(String.format(UNABLE_TO_DESERIALIZE, node.asText()));
    }
}
