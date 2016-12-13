package uk.co.codera.ci.tooling.application.data;

import static uk.co.codera.ci.tooling.application.JenkinsConfiguration.someJenkinsConfiguration;

import org.apache.commons.lang3.RandomStringUtils;

import uk.co.codera.ci.tooling.application.JenkinsConfiguration;

public class TestJenkinsConfiguration {

    private static final String EMPTY_JOB_TEMPLATE = "src/test/resources/test-job-template/empty-job.xml";

    public static JenkinsConfiguration.Builder randomJenkinsConfiguration() {
        return someJenkinsConfiguration().serverUrl(randomString()).jobTemplateFile(EMPTY_JOB_TEMPLATE);
    }

    private static String randomString() {
        return RandomStringUtils.randomAlphanumeric(10);
    }
}