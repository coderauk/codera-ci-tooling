package uk.co.codera.ci.tooling.application;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SvnConfiguration {

    @NotEmpty
    @JsonProperty
    private String host;

    @JsonProperty
    private Integer port;

    public SvnConfiguration() {
        super();
    }

    private SvnConfiguration(Builder builder) {
        this();
        this.host = builder.host;
        this.port = builder.port;
    }

    public static Builder someSvnConfiguration() {
        return new Builder();
    }

    public String getHost() {
        return this.host;
    }

    public Integer getPort() {
        return this.port;
    }

    public static class Builder {

        private String host;
        private Integer port;

        private Builder() {
            super();
        }

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder port(Integer port) {
            this.port = port;
            return this;
        }

        public SvnConfiguration build() {
            return new SvnConfiguration(this);
        }
    }
}