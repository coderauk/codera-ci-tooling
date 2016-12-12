package uk.co.codera.ci.tooling.api.bitbucket.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryDto {

    private String slug;
    private ProjectDto project;

    public static RepositoryDto.Builder aRepository() {
        return new Builder();
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSlug() {
        return this.slug;
    }

    public void setProject(ProjectDto project) {
        this.project = project;
    }

    public ProjectDto getProject() {
        return this.project;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static class Builder {

        private String slug;
        private ProjectDto project;

        private Builder() {
            super();
        }

        public Builder slug(String slug) {
            this.slug = slug;
            return this;
        }

        public Builder with(ProjectDto project) {
            this.project = project;
            return this;
        }

        public RepositoryDto build() {
            RepositoryDto repository = new RepositoryDto();
            repository.setSlug(this.slug);
            repository.setProject(this.project);
            return repository;
        }
    }
}