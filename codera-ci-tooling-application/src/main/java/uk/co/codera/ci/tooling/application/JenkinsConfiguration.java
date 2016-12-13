package uk.co.codera.ci.tooling.application;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JenkinsConfiguration {

    @JsonProperty
    @NotEmpty
    private String jenkinsServerUrl;

    @NotEmpty
    @JsonProperty
    private String jenkinsJobTemplateFile;

    public JenkinsConfiguration() {
        super();
    }

    private JenkinsConfiguration(Builder builder) {
        this();
        this.jenkinsServerUrl = builder.serverUrl;
        this.jenkinsJobTemplateFile = builder.jobTemplateFile;
    }

    public static Builder someJenkinsConfiguration() {
        return new Builder();
    }

    public String getJenkinsServerUrl() {
        return this.jenkinsServerUrl;
    }

    public String getJenkinsJobTemplateFile() {
        return this.jenkinsJobTemplateFile;
    }

    public static class Builder {

        private String serverUrl;
        private String jobTemplateFile;

        private Builder() {
            super();
        }

        public Builder serverUrl(String serverUrl) {
            this.serverUrl = serverUrl;
            return this;
        }

        public Builder jobTemplateFile(String jobTemplateFile) {
            this.jobTemplateFile = jobTemplateFile;
            return this;
        }

        public JenkinsConfiguration build() {
            return new JenkinsConfiguration(this);
        }
    }
}