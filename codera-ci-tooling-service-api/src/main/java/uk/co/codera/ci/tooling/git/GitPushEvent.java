package uk.co.codera.ci.tooling.git;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class GitPushEvent {

    private final GitPushType pushType;
    private final GitReference reference;
    private final String repositoryName;
    private final String repositoryUrl;

    private GitPushEvent(Builder builder) {
        this.pushType = builder.pushType;
        this.reference = builder.reference;
        this.repositoryName = builder.repositoryName;
        this.repositoryUrl = builder.repositoryUrl;
    }

    public static Builder aGitPushEvent() {
        return new Builder();
    }

    public GitPushType getPushType() {
        return this.pushType;
    }

    public GitReference getReference() {
        return this.reference;
    }

    public String getRepositoryName() {
        return this.repositoryName;
    }

    public String getRepositoryUrl() {
        return this.repositoryUrl;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static class Builder {

        private GitPushType pushType;
        private GitReference reference;
        private String repositoryName;
        private String repositoryUrl;

        private Builder() {
            super();
        }

        public Builder pushType(GitPushType type) {
            this.pushType = type;
            return this;
        }

        public Builder reference(GitReference reference) {
            this.reference = reference;
            return this;
        }

        public Builder repositoryName(String name) {
            this.repositoryName = name;
            return this;
        }

        public Builder repositoryUrl(String url) {
            this.repositoryUrl = url;
            return this;
        }

        public GitPushEvent build() {
            return new GitPushEvent(this);
        }
    }
}