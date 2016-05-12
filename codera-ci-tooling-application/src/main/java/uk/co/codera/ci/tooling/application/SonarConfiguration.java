package uk.co.codera.ci.tooling.application;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SonarConfiguration {

    @NotEmpty
    @JsonProperty
    private String sonarUrl;

    @NotEmpty
    @JsonProperty
    private String user;

    @NotEmpty
    @JsonProperty
    private String password;

    @JsonProperty
    private String jobKeyTemplate;

    public void setSonarUrl(String sonarUrl) {
        this.sonarUrl = sonarUrl;
    }

    public String getSonarUrl() {
        return this.sonarUrl;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return this.user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setJobKeyTemplate(String jobKeyTemplate) {
        this.jobKeyTemplate = jobKeyTemplate;
    }

    public String getJobKeyTemplate() {
        return this.jobKeyTemplate;
    }

    public boolean hasJobKeyTemplate() {
        return getJobKeyTemplate() != null;
    }
}