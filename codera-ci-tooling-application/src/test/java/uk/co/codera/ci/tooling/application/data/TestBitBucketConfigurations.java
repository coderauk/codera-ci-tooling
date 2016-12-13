package uk.co.codera.ci.tooling.application.data;

import static uk.co.codera.ci.tooling.application.BitBucketConfiguration.someBitBucketConfiguration;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import uk.co.codera.ci.tooling.application.BitBucketConfiguration;

public class TestBitBucketConfigurations {

    public static BitBucketConfiguration.Builder randomBitBucketConfiguration() {
        return someBitBucketConfiguration().host(randomString()).port(RandomUtils.nextInt(81, 8081));
    }

    private static String randomString() {
        return RandomStringUtils.randomAlphanumeric(10);
    }
}