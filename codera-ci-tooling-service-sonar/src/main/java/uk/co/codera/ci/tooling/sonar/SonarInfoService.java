package uk.co.codera.ci.tooling.sonar;

import java.io.IOException;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import uk.co.codera.ci.tooling.sonar.dto.SystemConfiguration;

public class SonarInfoService {

    private static final String ENDPOINT_URL = "api/system/info";

    private final String url;
    private final HttpClientFactory httpClientFactory;
    private final String authHeader;

    public SonarInfoService(HttpClientFactory httpClientFactory, String sonarUrl, String username, String password) {
        this.url = sonarUrl + ENDPOINT_URL;
        this.httpClientFactory = httpClientFactory;
        this.authHeader = AuthHeaderFactory.create(username, password);
    }

    public SonarInfo get() {
        CloseableHttpClient httpClient;

        try {
            httpClient = this.httpClientFactory.create();
            HttpGet request = new HttpGet(this.url);
            request.setHeader(HttpHeaders.AUTHORIZATION, this.authHeader);
            CloseableHttpResponse response = httpClient.execute(request);
            ObjectMapper objectMapper = new ObjectMapper();
            SystemConfiguration systemConfiguration = objectMapper.readValue(response.getEntity().getContent(),
                    SystemConfiguration.class);
            return SonarInfo.someSonarInfo().version(systemConfiguration.getSonarQube().getVersion()).build();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } finally {
        }
    }
}