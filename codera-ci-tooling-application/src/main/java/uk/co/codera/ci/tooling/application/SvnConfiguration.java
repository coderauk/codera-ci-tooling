package uk.co.codera.ci.tooling.application;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SvnConfiguration {

    @NotEmpty
    @JsonProperty
    private String host;

    @JsonProperty
    private Integer port;

    public String getHost() {
        return this.host;
    }

    public Integer getPort() {
        return this.port;
    }
}