package uk.co.codera.jenkins.tooling.application;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class JenkinsToolingConfiguration extends Configuration {

    @NotEmpty
    private String bitBucketServerName;

    @NotEmpty
    private int bitBucketServerPort;

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
}