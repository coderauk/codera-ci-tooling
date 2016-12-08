package uk.co.codera.ci.tooling.api.bitbucket.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDto {

    private String key;

    public static ProjectDto.Builder aProject() {
        return new Builder();
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public static class Builder {

        private String key;

        private Builder() {
            super();
        }

        public Builder key(String key) {
            this.key = key;
            return this;
        }

        public ProjectDto build() {
            ProjectDto project = new ProjectDto();
            project.setKey(this.key);
            return project;
        }
    }
}