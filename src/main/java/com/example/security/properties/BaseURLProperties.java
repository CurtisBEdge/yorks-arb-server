package com.example.security.properties;

import com.example.security.utils.BaseUrlStringUtil;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "url")
@Validated
public class BaseURLProperties {

    @NotNull
    private String baseUrl;

    public BaseURLProperties(String baseUrl) {
        this.baseUrl = BaseUrlStringUtil.duplicateSlashRemover(baseUrl);
    }

    public @NotNull String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(@NotNull String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
