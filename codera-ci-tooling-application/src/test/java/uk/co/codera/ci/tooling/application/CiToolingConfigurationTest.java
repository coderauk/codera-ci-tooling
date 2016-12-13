package uk.co.codera.ci.tooling.application;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import io.dropwizard.configuration.ConfigurationException;
import io.dropwizard.configuration.ConfigurationFactory;
import io.dropwizard.setup.Bootstrap;

public class CiToolingConfigurationTest {

    private static final String PROPERTY_PREFIX_DROP_WIZARD = "dw";
    private static final String TEMPLATE_CONFIG_LOCATIOn = "src/test/resources/test-configuration/%s.yaml";

    private ConfigurationFactory<CiToolingConfiguration> configurationFactory;

    @Before
    public void before() {
        Bootstrap<CiToolingConfiguration> bootstrap = new Bootstrap<>(new CiToolingApplication());
        this.configurationFactory = new ConfigurationFactory<>(CiToolingConfiguration.class, bootstrap
                .getValidatorFactory().getValidator(), bootstrap.getObjectMapper(), PROPERTY_PREFIX_DROP_WIZARD);
    }

    @Test
    public void shouldBeAbleToBuildMostBasicConfiguration() {
        assertThat(buildMostBasicConfiguration(), is(notNullValue()));
    }

    @Test
    public void shouldNotHaveJenkinsConfigurationSetIfNotPresentInConfigurationFile() {
        assertThat(buildMostBasicConfiguration().isJenkinsConfigured(), is(false));
    }

    @Test
    public void shouldNotHaveBitBucketConfigurationSetIfNotPresentInConfigurationFile() {
        assertThat(buildMostBasicConfiguration().isBitBucketConfigured(), is(false));
    }

    @Test
    public void shouldNotHaveSonarConfigurationSetIfNotPresentInConfigurationFile() {
        assertThat(buildMostBasicConfiguration().isSonarConfigured(), is(false));
    }

    @Test
    public void shouldNotHaveSvnConfigurationSetIfNotPresentInConfigurationFile() {
        assertThat(buildMostBasicConfiguration().isSvnConfigured(), is(false));
    }

    @Test
    public void shouldBeAbleToBuildFullConfiguration() {
        assertThat(buildFullConfiguration(), is(notNullValue()));
    }

    @Test
    public void shouldHaveJenkinsConfigurationSetIfPresentInConfigurationFile() {
        assertThat(buildFullConfiguration().isJenkinsConfigured(), is(true));
    }

    @Test
    public void shouldHaveBitBucketConfigurationSetIfPresentInConfigurationFile() {
        assertThat(buildFullConfiguration().isBitBucketConfigured(), is(true));
    }

    @Test
    public void shouldHaveSonarConfigurationSetIfPresentInConfigurationFile() {
        assertThat(buildFullConfiguration().isSonarConfigured(), is(true));
    }

    @Test
    public void shouldHaveSvnConfigurationSetIfPresentInConfigurationFile() {
        assertThat(buildFullConfiguration().isSvnConfigured(), is(true));
    }

    private CiToolingConfiguration buildFullConfiguration() {
        return build("everything-specified-configuration");
    }

    private CiToolingConfiguration buildMostBasicConfiguration() {
        return build("most-basic-valid-configuration");
    }

    private CiToolingConfiguration build(String configurationFileName) {
        return build(new File(String.format(TEMPLATE_CONFIG_LOCATIOn, configurationFileName)));
    }

    private CiToolingConfiguration build(File file) {
        try {
            return this.configurationFactory.build(file);
        } catch (IOException | ConfigurationException e) {
            throw new IllegalStateException(e);
        }
    }
}