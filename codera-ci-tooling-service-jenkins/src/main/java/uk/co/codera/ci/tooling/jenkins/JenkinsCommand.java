package uk.co.codera.ci.tooling.jenkins;

public abstract class JenkinsCommand {

    private final JenkinsConnectionDetails jenkinsConfiguration;

    protected JenkinsCommand(Builder<?, ?> builder) {
        this.jenkinsConfiguration = builder.jenkinsConfiguration;
    }

    public abstract String getName();

    public String[] getArguments() {
        return new String[] { "-s", this.jenkinsConfiguration.getServerUrl(), getName() };
    }

    public void execute(JenkinsCommandLineInterfaceInvoker cliInvoker) {
        cliInvoker.invoke(getArguments());
    }

    @SuppressWarnings("unchecked")
    public abstract static class Builder<T, C extends JenkinsCommand> {
        private JenkinsConnectionDetails jenkinsConfiguration;

        public T with(JenkinsConnectionDetails jenkinsConfiguration) {
            this.jenkinsConfiguration = jenkinsConfiguration;
            return (T) this;
        }

        public abstract C build();
    }
}