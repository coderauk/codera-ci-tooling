package uk.co.codera.jenkins.tooling.api.github;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/github")
@Consumes(MediaType.APPLICATION_JSON)
public class GitHubResource {

    private static final Logger logger = LoggerFactory.getLogger(GitHubResource.class);

    @POST
    public void onEvent(@HeaderParam("X-GitHub-Event") String eventType, String event) {
        logger.info("Received event type [{}] with content [{}]", eventType, event);
    }
}