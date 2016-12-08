package uk.co.codera.ci.tooling.api.bitbucket.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PushEventDto {

    private RepositoryDto repository;
    private List<RefChangeDto> refChanges;

    public static Builder aPushEvent() {
        return new Builder();
    }

    public List<RefChangeDto> getRefChanges() {
        return this.refChanges;
    }

    public void setRefChanges(List<RefChangeDto> refChanges) {
        this.refChanges = refChanges;
    }

    public void setRepository(RepositoryDto repository) {
        this.repository = repository;
    }

    public RepositoryDto getRepository() {
        return this.repository;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static class Builder {

        private RepositoryDto repository;
        private final List<RefChangeDto> refChanges;

        private Builder() {
            this.refChanges = new ArrayList<>();
        }

        public Builder with(RepositoryDto repository) {
            this.repository = repository;
            return this;
        }

        public Builder with(RefChangeDto refChange) {
            this.refChanges.add(refChange);
            return this;
        }

        public Builder noRefChanges() {
            this.refChanges.clear();
            return this;
        }

        public PushEventDto build() {
            PushEventDto event = new PushEventDto();
            event.setRepository(this.repository);
            event.setRefChanges(new ArrayList<>(this.refChanges));
            return event;
        }
    }
}