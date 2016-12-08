package uk.co.codera.ci.tooling.svn;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SvnCommitEvent {

    private final SvnCommitType commitType;
    private final String svnLocation;
    private final String branchName;
    private final String projectName;

    public SvnCommitEvent(Builder builder) {
        this.commitType = builder.commitType;
        this.svnLocation = builder.svnLocation;
        this.branchName = builder.branchName;
        this.projectName = builder.projectName;
    }

    public static Builder anSvnCommitEvent() {
        return new Builder();
    }

    public SvnCommitType commitType() {
        return this.commitType;
    }

    public String svnLocation() {
        return this.svnLocation;
    }

    public String branchName() {
        return this.branchName;
    }

    public String projectName() {
        return this.projectName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static class Builder {

        private SvnCommitType commitType;
        private String svnLocation;
        private String branchName;
        private String projectName;

        private Builder() {
            super();
        }

        public Builder commitType(SvnCommitType commitType) {
            this.commitType = commitType;
            return this;
        }

        public Builder svnLocation(String svnLocation) {
            this.svnLocation = svnLocation;
            return this;
        }

        public Builder branchName(String branchName) {
            this.branchName = branchName;
            return this;
        }

        public Builder projectName(String projectName) {
            this.projectName = projectName;
            return this;
        }

        public SvnCommitEvent build() {
            return new SvnCommitEvent(this);
        }
    }
}