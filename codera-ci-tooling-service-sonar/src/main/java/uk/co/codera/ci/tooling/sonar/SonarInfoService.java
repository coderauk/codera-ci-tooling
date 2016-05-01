package uk.co.codera.ci.tooling.sonar;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import uk.co.codera.ci.tooling.sonar.dto.SystemConfiguration;

public class SonarInfoService extends AbstractSonarService<Void, SonarInfo> {

    private static final String ENDPOINT_URL = "api/system/info";

    private final ObjectMapper objectMapper;

    public SonarInfoService(HttpClientFactory httpClientFactory, String sonarUrl, String username, String password) {
        super(httpClientFactory, sonarUrl + ENDPOINT_URL, username, password);
        this.objectMapper = new ObjectMapper();
    }

    public SonarInfo get() {
        return execute(null);
    }

    @Override
    HttpUriRequest createRequest(Void context) {
        return new HttpGet(url());
    }

    @Override
    SonarInfo processResponse(Void context, HttpResponse response) throws IOException {
        SystemConfiguration systemConfiguration = convertResponseToObject(response);
        return SonarInfo.someSonarInfo().version(systemConfiguration.getSonarQube().getVersion()).build();
    }

    private SystemConfiguration convertResponseToObject(HttpResponse response) throws IOException {
        return this.objectMapper.readValue(response.getEntity().getContent(), SystemConfiguration.class);
    }
}