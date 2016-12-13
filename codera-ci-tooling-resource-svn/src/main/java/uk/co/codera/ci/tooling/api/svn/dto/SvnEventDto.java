package uk.co.codera.ci.tooling.api.svn.dto;

public class SvnEventDto {

    private String location;
    private SvnEventType eventType;
    private String branch;
    private String project;

    public SvnEventDto() {
        super();
    }

    private SvnEventDto(Builder builder) {
        this();
        this.location = builder.location;
        this.eventType = builder.eventType;
        this.branch = builder.branch;
        this.project = builder.project;
    }

    public static Builder anSvnEventDto() {
        return new Builder();
    }

    public String getLocation() {
        return this.location;
    }

    public SvnEventType getEventType() {
        return this.eventType;
    }

    public String getBranch() {
        return this.branch;
    }

    public String getProject() {
        return this.project;
    }

    public static class Builder {

        private String location;
        private SvnEventType eventType;
        private String branch;
        private String project;

        private Builder() {
            super();
        }

        public Builder location(String location) {
            this.location = location;
            return this;
        }

        public Builder eventType(SvnEventType eventType) {
            this.eventType = eventType;
            return this;
        }

        public Builder branch(String branch) {
            this.branch = branch;
            return this;
        }

        public Builder project(String project) {
            this.project = project;
            return this;
        }

        public SvnEventDto build() {
            return new SvnEventDto(this);
        }
    }
}