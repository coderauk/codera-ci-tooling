package uk.co.codera.ci.tooling.application;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SonarConfiguration {

    @NotEmpty
    @JsonProperty
    private String sonarUrl;

    @NotEmpty
    @JsonProperty
    private String user;

    @NotEmpty
    @JsonProperty
    private String password;

    @JsonProperty
    private String jobKeyTemplate;

    public SonarConfiguration() {
        super();
    }

    private SonarConfiguration(Builder builder) {
        this();
        this.sonarUrl = builder.sonarUrl;
        this.user = builder.user;
        this.password = builder.password;
        this.jobKeyTemplate = builder.jobKeyTemplate;
    }

    public static Builder someSonarConfiguration() {
        return new Builder();
    }

    public String getSonarUrl() {
        return this.sonarUrl;
    }

    public String getUser() {
        return this.user;
    }

    public String getPassword() {
        return this.password;
    }

    public String getJobKeyTemplate() {
        return this.jobKeyTemplate;
    }

    public boolean hasJobKeyTemplate() {
        return getJobKeyTemplate() != null;
    }

    public static class Builder {

        private String sonarUrl;
        private String user;
        private String password;
        private String jobKeyTemplate;

        private Builder() {
            super();
        }

        public Builder sonarUrl(String sonarUrl) {
            this.sonarUrl = sonarUrl;
            return this;
        }

        public Builder user(String user) {
            this.user = user;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder jobKeyTemplate(String jobKeyTemplate) {
            this.jobKeyTemplate = jobKeyTemplate;
            return this;
        }

        public SonarConfiguration build() {
            return new SonarConfiguration(this);
        }
    }
}