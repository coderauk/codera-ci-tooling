package uk.co.codera.ci.tooling.jenkins;

public class JenkinsConfiguration {

    private final String serverUrl;

    private JenkinsConfiguration(Builder builder) {
        this.serverUrl = builder.serverUrl;
    }

    public static Builder aJenkinsConfiguration() {
        return new Builder();
    }

    public String getServerUrl() {
        return this.serverUrl;
    }

    public static class Builder {

        private String serverUrl;

        private Builder() {
            super();
        }

        public Builder serverUrl(String serverUrl) {
            this.serverUrl = serverUrl;
            return this;
        }

        public JenkinsConfiguration build() {
            return new JenkinsConfiguration(this);
        }
    }
}