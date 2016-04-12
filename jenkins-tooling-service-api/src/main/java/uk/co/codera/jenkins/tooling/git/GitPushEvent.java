package uk.co.codera.jenkins.tooling.git;

public class GitPushEvent {

    private final GitPushType pushType;
    private final GitReference reference;
    private final String repositoryUrl;

    private GitPushEvent(Builder builder) {
        this.pushType = builder.pushType;
        this.reference = builder.reference;
        this.repositoryUrl = builder.repositoryUrl;
    }

    public GitPushType getPushType() {
        return this.pushType;
    }
    
    public GitReference getReference() {
        return this.reference;
    }
    
    public String getRepositoryUrl() {
        return this.repositoryUrl;
    }

    public static Builder aGitPushEvent() {
        return new Builder();
    }

    public static class Builder {

        private GitPushType pushType;
        private GitReference reference;
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
        
        public Builder repositoryUrl(String url) {
            this.repositoryUrl = url;
            return this;
        }

        public GitPushEvent build() {
            return new GitPushEvent(this);
        }
    }
}