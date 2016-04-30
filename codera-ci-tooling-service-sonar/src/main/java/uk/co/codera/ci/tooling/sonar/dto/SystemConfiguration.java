package uk.co.codera.ci.tooling.sonar.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemConfiguration {

    @JsonProperty("SonarQube")
    private SonarQube sonarQube;

    public void setSonarQube(SonarQube sonarQube) {
        this.sonarQube = sonarQube;
    }

    public SonarQube getSonarQube() {
        return this.sonarQube;
    }
}