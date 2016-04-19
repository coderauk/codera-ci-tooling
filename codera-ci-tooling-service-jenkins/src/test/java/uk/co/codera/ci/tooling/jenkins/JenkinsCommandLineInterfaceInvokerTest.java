package uk.co.codera.ci.tooling.jenkins;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.co.codera.ci.tooling.jenkins.JenkinsCommandLineInterfaceInvoker;

public class JenkinsCommandLineInterfaceInvokerTest {

    private JenkinsCommandLineInterfaceInvoker invoker;

    private PrintStream standardErrorStream;
    private ByteArrayOutputStream bos;

    @Before
    public void before() {
        this.standardErrorStream = System.err;

        this.bos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(bos);
        System.setErr(printStream);

        this.invoker = new JenkinsCommandLineInterfaceInvoker();
    }

    @After
    public void after() {
        System.setErr(this.standardErrorStream);
    }

    @Test
    public void shouldPrintUsageWhenInvokedWithNoArguments() {
        this.invoker.invoke(new String[] {});
        assertThat(capturedErrorOutput(), CoreMatchers.anyOf(containsString("Jenkins CLI"),
                containsString("Failed to authenticate with your SSH keys")));
    }

    private String capturedErrorOutput() {
        return new String(bos.toByteArray());
    }

}