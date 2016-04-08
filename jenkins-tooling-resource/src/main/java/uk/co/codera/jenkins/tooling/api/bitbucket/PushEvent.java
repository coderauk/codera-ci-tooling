package uk.co.codera.jenkins.tooling.api.bitbucket;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PushEvent {

    private List<RefChange> refChanges;

    public List<RefChange> getRefChanges() {
        return this.refChanges;
    }

    public void setRefChanges(List<RefChange> refChanges) {
        this.refChanges = refChanges;
    }
}