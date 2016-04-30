package uk.co.codera.ci.tooling.application;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitBucketConfiguration {

    @NotEmpty
    @JsonProperty
    private String bitBucketServerName;

    @JsonProperty
    private int bitBucketServerPort;

    public void setBitBucketServerName(String bitBucketServerName) {
        this.bitBucketServerName = bitBucketServerName;
    }

    public String getBitBucketServerName() {
        return this.bitBucketServerName;
    }

    public void setBitBucketServerPort(int bitBucketServerPort) {
        this.bitBucketServerPort = bitBucketServerPort;
    }

    public int getBitBucketServerPort() {
        return this.bitBucketServerPort;
    }
}