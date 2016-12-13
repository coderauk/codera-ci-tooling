package uk.co.codera.ci.tooling.scm;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ScmEvent {

    private final ScmEventType eventType;
    private final String repositoryUrl;
    private final String projectName;
    private final String branchName;
    private final String shortBranchName;

    private ScmEvent(Builder builder) {
        this.eventType = builder.eventType;
        this.repositoryUrl = builder.repositoryUrl;
        this.projectName = builder.projectName;
        this.branchName = builder.branchName;
        this.shortBranchName = builder.shortBranchName;
    }

    public static Builder anScmEvent() {
        return new Builder();
    }

    public ScmEventType eventType() {
        return this.eventType;
    }

    public String repositoryUrl() {
        return this.repositoryUrl;
    }

    public String projectName() {
        return this.projectName;
    }

    public String branchName() {
        return this.branchName;
    }

    public String shortBranchName() {
        return this.shortBranchName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static class Builder {

        private ScmEventType eventType;
        private String repositoryUrl;
        private String projectName;
        private String branchName;
        private String shortBranchName;

        private Builder() {
            super();
        }

        public Builder eventType(ScmEventType eventType) {
            this.eventType = eventType;
            return this;
        }

        public Builder repositoryUrl(String repositoryUrl) {
            this.repositoryUrl = repositoryUrl;
            return this;
        }

        public Builder projectName(String projectName) {
            this.projectName = projectName;
            return this;
        }

        public Builder branchName(String branchName) {
            this.branchName = branchName;
            return this;
        }

        public Builder shortBranchName(String shortBranchName) {
            this.shortBranchName = shortBranchName;
            return this;
        }

        public ScmEvent build() {
            return new ScmEvent(this);
        }
    }
}