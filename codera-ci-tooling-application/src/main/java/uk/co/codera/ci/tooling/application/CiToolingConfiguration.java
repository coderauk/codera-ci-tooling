package uk.co.codera.ci.tooling.application;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class CiToolingConfiguration extends Configuration {

    @NotEmpty
    private String bitBucketServerName;

    private int bitBucketServerPort;

    @NotEmpty
    private String jenkinsServerUrl;

    @NotEmpty
    private String jenkinsJobTemplateFile;

    @JsonProperty
    public void setBitBucketServerName(String bitBucketServerName) {
        this.bitBucketServerName = bitBucketServerName;
    }

    @JsonProperty
    public String getBitBucketServerName() {
        return this.bitBucketServerName;
    }

    @JsonProperty
    public void setBitBucketServerPort(int bitBucketServerPort) {
        this.bitBucketServerPort = bitBucketServerPort;
    }

    @JsonProperty
    public int getBitBucketServerPort() {
        return this.bitBucketServerPort;
    }

    @JsonProperty
    public void setJenkinsServerUrl(String jenkinsServerUrl) {
        this.jenkinsServerUrl = jenkinsServerUrl;
    }

    @JsonProperty
    public String getJenkinsServerUrl() {
        return this.jenkinsServerUrl;
    }

    @JsonProperty
    public void setJenkinsJobTemplateFile(String jenkinsJobTemplateFile) {
        this.jenkinsJobTemplateFile = jenkinsJobTemplateFile;
    }

    @JsonProperty
    public String getJenkinsJobTemplateFile() {
        return this.jenkinsJobTemplateFile;
    }
}