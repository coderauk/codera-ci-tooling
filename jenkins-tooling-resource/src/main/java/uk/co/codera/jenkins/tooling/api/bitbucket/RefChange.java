package uk.co.codera.jenkins.tooling.api.bitbucket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import uk.co.codera.jenkins.tooling.git.GitPushType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RefChange {

    private String refId;

    private GitPushType type;

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
}