package uk.co.codera.ci.tooling.api.bitbucket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Project {

    private String key;
    
    public static Project.Builder aProject() {
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
        
        public Project build() {
            Project project = new Project();
            project.setKey(this.key);
            return project;
        }
    }
}