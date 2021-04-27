package uk.co.jamesmcguigan.forecaster.configuration;

import org.springframework.context.annotation.Bean;
import uk.co.jamesmcguigan.forecaster.repository.PatternRepository;
import uk.co.jamesmcguigan.forecaster.service.pattern.strategies.BullishStrategy;
import uk.co.jamesmcguigan.forecaster.service.pattern.strategies.PatternStrategy;

public class PatternConfig {
    @Bean
    public PatternStrategy bullishStrategy(PatternRepository patternRepository) {
        return new BullishStrategy(patternRepository);

    }
}
