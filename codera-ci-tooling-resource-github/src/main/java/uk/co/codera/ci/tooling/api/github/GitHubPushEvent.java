package uk.co.codera.ci.tooling.api.github;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubPushEvent {

    public static final String REF_TYPE_BRANCH = "branch";
    public static final String REF_TYPE_TAG = "tag";

    private String ref;

    @JsonProperty("ref_type")
    private String refType;

    private Repository repository;

    public static Builder aPushEvent() {
        return new Builder();
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getRef() {
        return this.ref;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public String getRefType() {
        return this.refType;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public Repository getRepository() {
        return repository;
    }

    public boolean isBranch() {
        return REF_TYPE_BRANCH.equals(getRefType());
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static class Builder {

        private String ref;
        private String refType;
        private Repository repository;

        private Builder() {
            super();
        }

        public Builder ref(String ref) {
            this.ref = ref;
            return this;
        }

        public Builder refType(String refType) {
            this.refType = refType;
            return this;
        }

        public Builder with(Repository repository) {
            this.repository = repository;
            return this;
        }

        public GitHubPushEvent build() {
            GitHubPushEvent event = new GitHubPushEvent();
            event.setRef(this.ref);
            event.setRefType(this.refType);
            event.setRepository(this.repository);
            return event;
        }
    }
}