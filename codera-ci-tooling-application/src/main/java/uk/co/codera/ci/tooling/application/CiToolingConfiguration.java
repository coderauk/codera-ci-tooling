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

    public CiToolingConfiguration() {
        super();
    }

    private CiToolingConfiguration(Builder builder) {
        this();
        this.sonar = builder.sonarConfiguration;

    }

    public static Builder someCiToolingConfiguration() {
        return new Builder();
    }

    public BitBucketConfiguration getBitBucket() {
        return bitBucket;
    }

    public JenkinsConfiguration getJenkins() {
        return this.jenkins;
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

    public static class Builder {

        private SonarConfiguration sonarConfiguration;

        private Builder() {
            super();
        }

        public Builder with(SonarConfiguration.Builder sonarConfiguration) {
            this.sonarConfiguration = sonarConfiguration.build();
            return this;
        }

        public CiToolingConfiguration build() {
            return new CiToolingConfiguration(this);
        }
    }
}