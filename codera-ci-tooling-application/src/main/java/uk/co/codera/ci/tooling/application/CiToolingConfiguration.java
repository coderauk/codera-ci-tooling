package uk.co.codera.ci.tooling.application;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class CiToolingConfiguration extends Configuration {

    @JsonProperty
    private BitBucketConfiguration bitBucket;
    
    @JsonProperty
    private JenkinsConfiguration jenkins;

    @JsonProperty
    private SonarConfiguration sonar;

    public void setBitBucket(BitBucketConfiguration bitbucket) {
        this.bitBucket = bitbucket;
    }
    
    public BitBucketConfiguration getBitBucket() {
        return bitBucket;
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

    public boolean isBitBucketConfigured() {
        return getBitBucket() != null;
    }
    
    public boolean isJenkinsConfigured() {
        return getJenkins() != null;
    }

    public boolean isSonarConfigured() {
        return getSonar() != null;
    }
}