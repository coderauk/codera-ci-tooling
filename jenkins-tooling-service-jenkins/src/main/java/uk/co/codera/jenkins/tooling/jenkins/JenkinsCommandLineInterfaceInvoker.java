package uk.co.codera.jenkins.tooling.jenkins;

import hudson.cli.CLI;

public class JenkinsCommandLineInterfaceInvoker {

    public JenkinsCommandLineInterfaceInvoker() {
        super();
    }
    
    public void invoke(String[] arguments) {
        try {
            CLI._main(arguments);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}