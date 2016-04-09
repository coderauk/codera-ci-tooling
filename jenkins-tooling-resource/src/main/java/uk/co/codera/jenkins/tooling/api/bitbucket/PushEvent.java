package uk.co.codera.jenkins.tooling.api.bitbucket;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PushEvent {

    private List<RefChange> refChanges;

    public static Builder aPushEvent() {
        return new Builder();
    }
    
    public List<RefChange> getRefChanges() {
        return this.refChanges;
    }

    public void setRefChanges(List<RefChange> refChanges) {
        this.refChanges = refChanges;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    
    public static class Builder {
        
        private final List<RefChange> refChanges;
        
        private Builder() {
            this.refChanges = new ArrayList<>();
        }
        
        public Builder with(RefChange refChange) {
            this.refChanges.add(refChange);
            return this;
        }
        
        public PushEvent build() {
            PushEvent event = new PushEvent();
            event.setRefChanges(new ArrayList<>(this.refChanges));
            return event;
        }
    }
}