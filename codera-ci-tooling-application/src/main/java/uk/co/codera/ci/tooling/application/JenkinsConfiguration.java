package uk.co.codera.ci.tooling.application;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JenkinsConfiguration {

    private static final String DEFAULT_JOB_NAME_TEMPLATE = "${projectName} - ${shortBranchName} - build";

    @JsonProperty
    @NotEmpty
    private String jenkinsServerUrl;

    @NotEmpty
    @JsonProperty
    private String jenkinsJobTemplateFile;

    @JsonProperty
    private String jobNameTemplate;

    public JenkinsConfiguration() {
        super();
    }

    private JenkinsConfiguration(Builder builder) {
        this();
        this.jenkinsServerUrl = builder.serverUrl;
        this.jenkinsJobTemplateFile = builder.jobTemplateFile;
        this.jobNameTemplate = builder.jobNameTemplate;
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

    public String getJobNameTemplate() {
        return this.jobNameTemplate == null ? DEFAULT_JOB_NAME_TEMPLATE : this.jobNameTemplate;
    }

    public static class Builder {

        private String serverUrl;
        private String jobTemplateFile;
        private String jobNameTemplate;

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

        public Builder jobNameTemplate(String jobNameTemplate) {
            this.jobNameTemplate = jobNameTemplate;
            return this;
        }

        public JenkinsConfiguration build() {
            return new JenkinsConfiguration(this);
        }
    }
}