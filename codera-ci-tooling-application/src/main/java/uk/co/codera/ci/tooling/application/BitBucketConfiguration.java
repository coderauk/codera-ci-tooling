package uk.co.codera.ci.tooling.application;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitBucketConfiguration {

    @NotEmpty
    @JsonProperty
    private String bitBucketServerName;

    @JsonProperty
    private int bitBucketServerPort;

    public BitBucketConfiguration() {
        super();
    }

    private BitBucketConfiguration(Builder builder) {
        this();
        this.bitBucketServerName = builder.bitBucketServerName;
        this.bitBucketServerPort = builder.bitBucketServerPort;
    }

    public static Builder someBitBucketConfiguration() {
        return new Builder();
    }

    public String getBitBucketServerName() {
        return this.bitBucketServerName;
    }

    public int getBitBucketServerPort() {
        return this.bitBucketServerPort;
    }

    public static class Builder {

        private String bitBucketServerName;
        private int bitBucketServerPort = 80;

        private Builder() {
            super();
        }

        public Builder host(String host) {
            this.bitBucketServerName = host;
            return this;
        }

        public Builder port(int port) {
            this.bitBucketServerPort = port;
            return this;
        }

        public BitBucketConfiguration build() {
            return new BitBucketConfiguration(this);
        }
    }
}