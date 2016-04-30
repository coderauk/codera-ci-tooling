package uk.co.codera.ci.tooling.application;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class CiToolingConfiguration extends Configuration {

    @NotEmpty
    @JsonProperty
    private String bitBucketServerName;

    @JsonProperty
    private int bitBucketServerPort;

    @JsonProperty
    private JenkinsConfiguration jenkins;

    @JsonProperty
    private SonarConfiguration sonar;

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

    public void setJenkins(JenkinsConfiguration jenkins) {
        this.jenkins = jenkins;
    }

    public JenkinsConfiguration getJenkins() {
        return this.jenkins;
    }

    public void setSonar(SonarConfiguration sonar) {
        this.sonar = sonar;
    }

    public SonarConfiguration getSonar() {
        return sonar;
    }

    public boolean isSonarConfigured() {
        return getSonar() != null;
    }

    public boolean isJenkinsConfigured() {
        return getJenkins() != null;
    }
}