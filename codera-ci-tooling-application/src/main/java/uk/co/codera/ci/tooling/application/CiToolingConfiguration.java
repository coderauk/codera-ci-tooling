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

    @JsonProperty
    private SvnConfiguration svn;

    public CiToolingConfiguration() {
        super();
    }

    private CiToolingConfiguration(Builder builder) {
        this();
        this.sonar = builder.sonarConfiguration;
        this.jenkins = builder.jenkinsConfiguration;
        this.bitBucket = builder.bitBucketConfiguration;
    }

    public static Builder someCiToolingConfiguration() {
        return new Builder();
    }

    public BitBucketConfiguration getBitBucket() {
        return this.bitBucket;
    }

    public JenkinsConfiguration getJenkins() {
        return this.jenkins;
    }

    public SonarConfiguration getSonar() {
        return this.sonar;
    }

    public SvnConfiguration getSvn() {
        return this.svn;
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

    public boolean isSvnConfigured() {
        return getSvn() != null;
    }

    public static class Builder {

        private SonarConfiguration sonarConfiguration;
        private JenkinsConfiguration jenkinsConfiguration;
        private BitBucketConfiguration bitBucketConfiguration;

        private Builder() {
            super();
        }

        public Builder with(SonarConfiguration.Builder sonarConfiguration) {
            this.sonarConfiguration = sonarConfiguration.build();
            return this;
        }

        public Builder with(JenkinsConfiguration.Builder jenkinsConfiguration) {
            this.jenkinsConfiguration = jenkinsConfiguration.build();
            return this;
        }
        
        public Builder with(BitBucketConfiguration.Builder bitBucketConfiguration) {
            this.bitBucketConfiguration = bitBucketConfiguration.build();
            return this;
        }

        public CiToolingConfiguration build() {
            return new CiToolingConfiguration(this);
        }
    }
}