package uk.co.codera.ci.tooling.jenkins;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.lang3.ArrayUtils;

public class CreateJobCommand extends JenkinsCommand {

	private static final String COMMAND_NAME = "create-job";

	private final String jobDefinition;
	private final String jobName;

	private CreateJobCommand(Builder builder) {
		super(builder);
		this.jobDefinition = builder.jobDefinition;
		this.jobName = builder.jobName;
	}

	public static Builder aCreateJobCommand() {
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

	@Override
	public void execute(JenkinsCommandLineInterfaceInvoker cliInvoker) {
		InputStream oldSystemInput = System.in;
		try {
			InputStream in = new ByteArrayInputStream(jobDefinition.getBytes());
			System.setIn(in);
			super.execute(cliInvoker);
		} finally {
			System.setIn(oldSystemInput);
		}
	}

	public static class Builder
			extends
				JenkinsCommand.Builder<Builder, CreateJobCommand> {

		private String jobDefinition;
		private String jobName;

		public Builder jobDefinition(String jobDefinition) {
			this.jobDefinition = jobDefinition;
			return this;
		}

		public Builder jobName(String jobName) {
			this.jobName = jobName;
			return this;
		}

		@Override
		public CreateJobCommand build() {
			return new CreateJobCommand(this);
		}
	}
}