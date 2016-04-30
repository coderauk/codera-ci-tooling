package uk.co.codera.ci.tooling.sonar.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SonarQube {

    @JsonProperty("Version")
    private String version;

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return this.version;
    }
}