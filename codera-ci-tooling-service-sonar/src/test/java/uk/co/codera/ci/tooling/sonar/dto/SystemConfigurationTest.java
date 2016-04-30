package uk.co.codera.ci.tooling.sonar.dto;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import uk.co.codera.lang.io.ClasspathResource;

public class SystemConfigurationTest {

    private static final String PATH_SONAR_CONFIGURATION_JSON = "/sonar-configuration.json";

    private ObjectMapper objectMapper;

    @Before
    public void before() {
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void shouldConvertFromJsonToNonNullSystemConfiguration() {
        assertThat(readSystemConfiguration(), is(notNullValue()));
    }

    @Test
    public void shouldHaveSonarQubeSection() {
        assertThat(readSystemConfiguration().getSonarQube(), is(notNullValue()));
    }

    @Test
    public void shouldHaveVersionOnSonarQubeSection() {
        assertThat(sonarQube().getVersion(), is(notNullValue()));
    }

    private SonarQube sonarQube() {
        return readSystemConfiguration().getSonarQube();
    }

    private SystemConfiguration readSystemConfiguration() {
        String json = new ClasspathResource(PATH_SONAR_CONFIGURATION_JSON).getAsString();
        try {
            return this.objectMapper.readValue(json, SystemConfiguration.class);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}