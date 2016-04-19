package uk.co.codera.jenkins.tooling.jenkins;

import org.apache.commons.lang3.ArrayUtils;

public class DeleteJobCommand extends JenkinsCommand {

    private static final String COMMAND_NAME = "delete-job";
    
    private final String jobName;
    
    private DeleteJobCommand(Builder builder) {
        super(builder);
        this.jobName = builder.jobName;
    }
    
    public static Builder aDeleteJobCommand() {
        return new Builder();
    }
    
    @Override
    public String getName() {
        return COMMAND_NAME;
    }
    
    @Override
    public String[] getArguments() {
        return ArrayUtils.addAll(super.getArguments(), this.jobName);
    }
    
    public static class Builder extends JenkinsCommand.Builder<Builder, DeleteJobCommand> {

        private String jobName;
        
        public Builder jobName(String jobName) {
            this.jobName = jobName;
            return this;
        }
        
        @Override
        public DeleteJobCommand build() {
            return new DeleteJobCommand(this);
        }
    }
}