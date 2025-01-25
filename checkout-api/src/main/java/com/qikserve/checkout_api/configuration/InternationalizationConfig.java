package com.qikserve.checkout_api.configuration;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

@Configuration
public class InternationalizationConfig extends AcceptHeaderLocaleResolver {
    private static final Logger logger = LoggerFactory.getLogger(InternationalizationConfig.class);

    @NonNull
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String headerLanguage = request.getHeader("Accept-Language");
        logger.info("Resolving locale for Accept-Language: {}", headerLanguage);

        List<Locale> locales = List.of(Locale.ENGLISH, Locale.forLanguageTag("es"), Locale.forLanguageTag("pt"));
        Locale resolvedLocale = headerLanguage == null || headerLanguage.isEmpty() ? Locale.getDefault()
                : Locale.lookup(Locale.LanguageRange.parse(headerLanguage), locales);

        logger.info("Resolved locale: {}", resolvedLocale);
        return resolvedLocale;
    }

    @Bean
    ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("internationalization/messages");
        source.setDefaultEncoding(StandardCharsets.UTF_8.name());
        source.setDefaultLocale(Locale.ENGLISH);
        return source;
    }
}
