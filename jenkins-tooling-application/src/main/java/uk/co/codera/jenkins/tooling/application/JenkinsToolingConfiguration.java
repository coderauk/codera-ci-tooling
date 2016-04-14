package uk.co.codera.jenkins.tooling.application;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class JenkinsToolingConfiguration extends Configuration {

    @NotEmpty
    private String bitBucketServerName;

    private int bitBucketServerPort;
    
    @NotEmpty
    private String jenkinsServerName;
    
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
    public void setJenkinsServerName(String jenkinsServerName) {
        this.jenkinsServerName = jenkinsServerName;
    }
    
    @JsonProperty
    public String getJenkinsServerName() {
        return this.jenkinsServerName;
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