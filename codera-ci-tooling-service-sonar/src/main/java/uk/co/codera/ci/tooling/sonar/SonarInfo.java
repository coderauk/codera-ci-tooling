package uk.co.codera.ci.tooling.sonar;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SonarInfo {

    private final String version;

    private SonarInfo(Builder builder) {
        this.version = builder.version;
    }

    public String getVersion() {
        return this.version;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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
