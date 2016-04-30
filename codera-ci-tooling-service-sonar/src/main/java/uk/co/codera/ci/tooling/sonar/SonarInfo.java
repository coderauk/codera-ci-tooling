package uk.co.codera.ci.tooling.sonar;

public class SonarInfo {

    private final String version;

    private SonarInfo(Builder builder) {
        this.version = builder.version;
    }

    public String getVersion() {
        return this.version;
    }

    public static Builder someSonarInfo() {
        return new Builder();
    }

    public static class Builder {

        private String version;

        private Builder() {
            super();
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public SonarInfo build() {
            return new SonarInfo(this);
        }
    }
}
