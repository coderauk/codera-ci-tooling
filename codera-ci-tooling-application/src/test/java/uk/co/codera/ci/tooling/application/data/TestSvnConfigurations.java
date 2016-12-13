package uk.co.codera.ci.tooling.application.data;

import static uk.co.codera.ci.tooling.application.SvnConfiguration.someSvnConfiguration;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import uk.co.codera.ci.tooling.application.SvnConfiguration;

public class TestSvnConfigurations {

    public static SvnConfiguration.Builder randomSvnConfiguration() {
        return someSvnConfiguration().host(randomString()).port(RandomUtils.nextInt(81, 8081));
    }

    private static String randomString() {
        return RandomStringUtils.randomAlphanumeric(10);
    }
}