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

    public String getJenkinsServerUrl() {
        return this.jenkinsServerUrl;
    }

    public String getJenkinsJobTemplateFile() {
        return this.jenkinsJobTemplateFile;
    }
}