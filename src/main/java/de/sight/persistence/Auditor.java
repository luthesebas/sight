package de.sight.persistence;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/** @author Sebastian Luther (https://github.com/luthesebas) */
public class Auditor implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor () {
        return Optional.of("editor");
    }

}
