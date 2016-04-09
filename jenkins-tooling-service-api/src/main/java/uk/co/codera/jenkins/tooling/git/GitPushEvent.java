package uk.co.codera.jenkins.tooling.git;

public class GitPushEvent {

    private final GitPushType pushType;
    private final GitReference reference;

    private GitPushEvent(Builder builder) {
        this.pushType = builder.pushType;
        this.reference = builder.reference;
    }

    public GitPushType getPushType() {
        return this.pushType;
    }
    
    public GitReference getReference() {
        return this.reference;
    }

    public static Builder aGitPushEvent() {
        return new Builder();
    }

    public static class Builder {

        private GitPushType pushType;
        private GitReference reference;

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

        public GitPushEvent build() {
            return new GitPushEvent(this);
        }
    }
}