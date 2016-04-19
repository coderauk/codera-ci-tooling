package uk.co.codera.ci.tooling.api.github;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubPushEvent {

    private String ref;
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

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public Repository getRepository() {
        return repository;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static class Builder {

        private String ref;
        private Repository repository;

        private Builder() {
            super();
        }

        public Builder ref(String ref) {
            this.ref = ref;
            return this;
        }

        public Builder with(Repository repository) {
            this.repository = repository;
            return this;
        }

        public GitHubPushEvent build() {
            GitHubPushEvent event = new GitHubPushEvent();
            event.setRef(this.ref);
            event.setRepository(this.repository);
            return event;
        }
    }
}