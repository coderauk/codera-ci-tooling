package uk.co.codera.jenkins.tooling.api.bitbucket;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/bitbucket")
@Consumes(MediaType.APPLICATION_JSON)
public class BitBucketResource {

	private final Logger logger;;

	public BitBucketResource(Logger logger) {
		this.logger = logger;
	}

	public BitBucketResource() {
		this(LoggerFactory.getLogger(BitBucketResource.class));
	}

	@POST
	public void push(PushEvent push) {
		logger.debug("Received push event [{}]", push);
	}
}