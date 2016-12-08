package uk.co.codera.ci.tooling.svn;

import uk.co.codera.ci.tooling.svn.SvnCommitEvent.Builder;

public class SvnCommitEvent {

    private final SvnCommitType commitType;
    private final String svnLocation;
    
    public SvnCommitEvent(Builder builder) {
        this.commitType = builder.commitType;
        this.svnLocation = builder.svnLocation;
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

    public static class Builder {
        
        private SvnCommitType commitType;
        private String svnLocation;
        
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
        
        public SvnCommitEvent build() {
            return new SvnCommitEvent(this);
        }
    }
}