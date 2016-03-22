package uk.co.codera.jenkins.tooling.api.bitbucket;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PushEventTest {

	private static final String PATH_JSON_BRANCH_CREATED = "/git/branch-created.json";
	private static final String PATH_JSON_BRANCH_UPDATED = "/git/branch-updated.json";
	private static final String PATH_JSON_BRANCH_DELETED = "/git/branch-deleted.json";
	
	private ObjectMapper objectMapper;

	@Before
	public void before() {
		this.objectMapper = new ObjectMapper();
	}

	@Test
	public void shouldBeAbleToReadValidPushEvent() {
		assertThat(readPushEvent(), is(notNullValue()));
	}
	
	@Test
	public void pushEventShouldRefChange() {
		assertThat(readPushEvent().getRefChanges(), hasSize(1));
	}
	
	@Test
	public void refChangeShouldHaveType() {
		assertThat(refChangeFor(readPushEvent()).getType(), is(notNullValue()));
	}
	
	@Test
	public void branchCreatedPushEventShouldHaveCorrectPushType() {
		assertThat(pushTypeFor(branchCreatedEvent()), is(PushType.ADD));
	}
	
	@Test
	public void branchUpdatedPushEventShouldHaveCorrectPushType() {
		assertThat(pushTypeFor(branchUpdatedEvent()), is(PushType.UPDATE));
	}
	
	@Test
	public void branchDeletedPushEventShouldHaveCorrectPushType() {
		assertThat(pushTypeFor(branchDeletedEvent()), is(PushType.DELETE));
	}
	
	private PushType pushTypeFor(PushEvent push) {
		return refChangeFor(push).getType();
	}
	
	private RefChange refChangeFor(PushEvent push) {
		return push.getRefChanges().get(0);
	}
	
	private PushEvent branchCreatedEvent() {
		return readPushEvent();
	}
	
	private PushEvent branchUpdatedEvent() {
		return readPushEvent(jsonForBranchUpdated());
	}
	
	private PushEvent branchDeletedEvent() {
		return readPushEvent(jsonForBranchDeleted());
	}
	
	private PushEvent readPushEvent() {
		return readPushEvent(jsonForBranchCreated());
	}
	
	private PushEvent readPushEvent(String json) {
		return read(json, PushEvent.class);
	}
	
	private <T> T read(String json, Class<T> targetClass) {
		try {
			return this.objectMapper.readValue(json, targetClass);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
	private String jsonForBranchCreated() {
		return json(PATH_JSON_BRANCH_CREATED);
	}
	
	private String jsonForBranchUpdated() {
		return json(PATH_JSON_BRANCH_UPDATED);
	}
	
	private String jsonForBranchDeleted() {
		return json(PATH_JSON_BRANCH_DELETED);
	}
	
	private String json(String path) {
		return new ClasspathResource(path).getAsString();
	}
}