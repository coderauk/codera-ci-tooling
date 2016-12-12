package uk.co.codera.ci.tooling.application.data;

import org.apache.commons.lang3.RandomStringUtils;

import uk.co.codera.ci.tooling.application.SonarConfiguration;

public class TestSonarConfigurations {

    public static SonarConfiguration.Builder randomSonarConfiguration() {
        return SonarConfiguration.someSonarConfiguration().sonarUrl(randomString()).user(randomString())
                .password(randomString()).jobKeyTemplate(randomString());
    }

    private static String randomString() {
        return RandomStringUtils.randomAlphanumeric(10);
    }
}