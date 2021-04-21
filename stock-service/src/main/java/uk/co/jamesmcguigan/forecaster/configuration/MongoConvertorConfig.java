package uk.co.jamesmcguigan.forecaster.configuration;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@Configuration
@Slf4j
@ReadingConverter
public class MongoConvertorConfig implements Converter<String, Number> {
    @Override
    public Double convert(@NonNull String source) {
        try {
            return Double.valueOf(source.toString());
        } catch (NumberFormatException e) {
            log.debug("Unable to convert {}", source);
            return 0d;
        }
    }
}