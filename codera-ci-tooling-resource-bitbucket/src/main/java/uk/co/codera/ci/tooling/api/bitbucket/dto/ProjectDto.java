package uk.co.codera.ci.tooling.api.bitbucket.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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