package uk.co.jamesmcguigan.forecaster.repository.auditing;

import lombok.NonNull;

import java.util.Optional;

public class AuditorAware implements org.springframework.data.domain.AuditorAware<String> {
    @Override
    public @NonNull Optional<String> getCurrentAuditor() {
        String loggedName = "Jim"; //SecurityContextHolder.getContext().getAuthentication().getName();
        return Optional.of(loggedName);
    }
}
