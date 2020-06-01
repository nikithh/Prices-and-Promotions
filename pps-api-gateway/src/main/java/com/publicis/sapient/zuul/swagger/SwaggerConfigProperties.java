package com.publicis.sapient.zuul.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration("swaggerConfigProperties")
public class SwaggerConfigProperties {

    @Value("${api.version}")
    private String apiVersion;

    @Value("${swagger.enabled}")
    private String enabled = "false";

    @Value("${swagger.title}")
    private String title;

    @Value("${swagger.description}")
    private String description;

    @Value("${swagger.useDefaultResponseMessages}")
    private String useDefaultResponseMessages;

    @Value("${swagger.enableUrlTemplating}")
    private String enableUrlTemplating;

    @Value("${swagger.deepLinking}")
    private String deepLinking;

    @Value("${swagger.defaultModelsExpandDepth}")
    private String defaultModelsExpandDepth;

    @Value("${swagger.defaultModelExpandDepth}")
    private String defaultModelExpandDepth;

    @Value("${swagger.displayOperationId}")
    private String displayOperationId;

    @Value("${swagger.displayRequestDuration}")
    private String displayRequestDuration;

    @Value("${swagger.filter}")
    private String filter;

    @Value("${swagger.maxDisplayedTags}")
    private String maxDisplayedTags;

    @Value("${swagger.showExtensions}")
    private String showExtensions;

    public String getApiVersion() {
        return this.apiVersion;
    }

    public void setApiVersion(final String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getEnabled() {
        return this.enabled;
    }

    public void setEnabled(final String enabled) {
        this.enabled = enabled;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getUseDefaultResponseMessages() {
        return this.useDefaultResponseMessages;
    }

    public void setUseDefaultResponseMessages(final String useDefaultResponseMessages) {
        this.useDefaultResponseMessages = useDefaultResponseMessages;
    }

    public String getEnableUrlTemplating() {
        return this.enableUrlTemplating;
    }

    public void setEnableUrlTemplating(final String enableUrlTemplating) {
        this.enableUrlTemplating = enableUrlTemplating;
    }

    public String getDeepLinking() {
        return this.deepLinking;
    }

    public void setDeepLinking(final String deepLinking) {
        this.deepLinking = deepLinking;
    }

    public String getDefaultModelsExpandDepth() {
        return this.defaultModelsExpandDepth;
    }

    public void setDefaultModelsExpandDepth(final String defaultModelsExpandDepth) {
        this.defaultModelsExpandDepth = defaultModelsExpandDepth;
    }

    public String getDefaultModelExpandDepth() {
        return this.defaultModelExpandDepth;
    }

    public void setDefaultModelExpandDepth(final String defaultModelExpandDepth) {
        this.defaultModelExpandDepth = defaultModelExpandDepth;
    }

    public String getDisplayOperationId() {
        return this.displayOperationId;
    }

    public void setDisplayOperationId(final String displayOperationId) {
        this.displayOperationId = displayOperationId;
    }

    public String getDisplayRequestDuration() {
        return this.displayRequestDuration;
    }

    public void setDisplayRequestDuration(final String displayRequestDuration) {
        this.displayRequestDuration = displayRequestDuration;
    }

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(final String filter) {
        this.filter = filter;
    }

    public String getMaxDisplayedTags() {
        return this.maxDisplayedTags;
    }

    public void setMaxDisplayedTags(final String maxDisplayedTags) {
        this.maxDisplayedTags = maxDisplayedTags;
    }

    public String getShowExtensions() {
        return this.showExtensions;
    }

    public void setShowExtensions(final String showExtensions) {
        this.showExtensions = showExtensions;
    }

    public SwaggerConfigProperties() {
    }

    public SwaggerConfigProperties(final String apiVersion,
            final String enabled,
            final String title,
            final String description,
            final String useDefaultResponseMessages,
            final String enableUrlTemplating,
            final String deepLinking,
            final String defaultModelsExpandDepth,
            final String defaultModelExpandDepth,
            final String displayOperationId,
            final String displayRequestDuration,
            final String filter,
            final String maxDisplayedTags,
            final String showExtensions) {
        super();
        this.apiVersion = apiVersion;
        this.enabled = enabled;
        this.title = title;
        this.description = description;
        this.useDefaultResponseMessages = useDefaultResponseMessages;
        this.enableUrlTemplating = enableUrlTemplating;
        this.deepLinking = deepLinking;
        this.defaultModelsExpandDepth = defaultModelsExpandDepth;
        this.defaultModelExpandDepth = defaultModelExpandDepth;
        this.displayOperationId = displayOperationId;
        this.displayRequestDuration = displayRequestDuration;
        this.filter = filter;
        this.maxDisplayedTags = maxDisplayedTags;
        this.showExtensions = showExtensions;
    }

    @Override
    public String toString() {
        return "SwaggerConfigProperties [apiVersion=" + this.apiVersion + ", enabled=" + this.enabled + ", title=" + this.title
                + ", description=" + this.description + ", useDefaultResponseMessages=" + this.useDefaultResponseMessages
                + ", enableUrlTemplating=" + this.enableUrlTemplating + ", deepLinking=" + this.deepLinking
                + ", defaultModelsExpandDepth=" + this.defaultModelsExpandDepth + ", defaultModelExpandDepth="
                + this.defaultModelExpandDepth + ", displayOperationId=" + this.displayOperationId + ", displayRequestDuration="
                + this.displayRequestDuration + ", filter=" + this.filter + ", maxDisplayedTags=" + this.maxDisplayedTags
                + ", showExtensions=" + this.showExtensions + "]";
    }

}
