package uk.co.codera.ci.tooling.api.bitbucket;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import uk.co.codera.ci.tooling.git.GitPushType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RefChange {

    private String refId;

    private GitPushType type;

    public static Builder aRefChange() {
        return new Builder();
    }
    
    public String getRefId() {
        return this.refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public GitPushType getType() {
        return this.type;
    }

    public void setType(GitPushType type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    
    public static class Builder {
        
        private String refId;
        private GitPushType type;
        
        private Builder() {
            super();
        }
        
        public Builder refId(String refId) {
            this.refId = refId;
            return this;
        }
        
        public Builder type(GitPushType type) {
            this.type = type;
            return this;
        }
        
        public RefChange build() {
            RefChange refChange = new RefChange();
            refChange.setRefId(this.refId);
            refChange.setType(this.type);
            return refChange;
        }
    }
}