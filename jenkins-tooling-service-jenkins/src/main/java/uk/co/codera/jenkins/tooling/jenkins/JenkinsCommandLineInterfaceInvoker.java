package uk.co.codera.jenkins.tooling.jenkins;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hudson.cli.CLI;

public class JenkinsCommandLineInterfaceInvoker {

    private static final Logger logger = LoggerFactory.getLogger(JenkinsCommandLineInterfaceInvoker.class);
    
    public JenkinsCommandLineInterfaceInvoker() {
        super();
    }
    
    public void invoke(String[] arguments) {
        logger.info("Invoking jenkins cli with arguments {}", Arrays.toString(arguments));
        try {
            CLI._main(arguments);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}