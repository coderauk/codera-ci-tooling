package uk.co.codera.ci.tooling.api.bitbucket.dto;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import uk.co.codera.ci.tooling.api.bitbucket.dto.ProjectDto;
import uk.co.codera.ci.tooling.api.bitbucket.dto.PushEventDto;
import uk.co.codera.ci.tooling.api.bitbucket.dto.RefChangeDto;
import uk.co.codera.ci.tooling.api.bitbucket.dto.RepositoryDto;
import uk.co.codera.ci.tooling.git.GitPushType;
import uk.co.codera.lang.io.ClasspathResource;

public class PushEventDtoTest {

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
    public void pushEventShouldHaveRefChange() {
        assertThat(readPushEvent().getRefChanges(), hasSize(1));
    }

    @Test
    public void refChangeShouldHaveRefId() {
        assertThat(refChangeFor(readPushEvent()).getRefId(), is(notNullValue()));
    }

    @Test
    public void refChangeShouldHaveType() {
        assertThat(refChangeFor(readPushEvent()).getType(), is(notNullValue()));
    }

    @Test
    public void branchCreatedPushEventShouldHaveCorrectPushType() {
        assertThat(pushTypeFor(branchCreatedEvent()), is(GitPushType.ADD));
    }

    @Test
    public void branchUpdatedPushEventShouldHaveCorrectPushType() {
        assertThat(pushTypeFor(branchUpdatedEvent()), is(GitPushType.UPDATE));
    }

    @Test
    public void branchDeletedPushEventShouldHaveCorrectPushType() {
        assertThat(pushTypeFor(branchDeletedEvent()), is(GitPushType.DELETE));
    }

    @Test
    public void toStringShouldNotBeObjectReference() {
        assertThat(readPushEvent().toString(), containsString("refId="));
    }

    @Test
    public void pushEventShouldHaveRepositoryInformation() {
        assertThat(readPushEvent().getRepository(), is(notNullValue()));
    }

    @Test
    public void repositoryShouldHaveSlug() {
        assertThat(repository().getSlug(), is(notNullValue()));
    }

    @Test
    public void repositoryShouldHaveProject() {
        assertThat(repository().getProject(), is(notNullValue()));
    }

    @Test
    public void projectShouldHaveKey() {
        assertThat(project().getKey(), is(notNullValue()));
    }

    private RepositoryDto repository() {
        return readPushEvent().getRepository();
    }

    private ProjectDto project() {
        return repository().getProject();
    }

    private GitPushType pushTypeFor(PushEventDto push) {
        return refChangeFor(push).getType();
    }

    private RefChangeDto refChangeFor(PushEventDto push) {
        return push.getRefChanges().get(0);
    }

    private PushEventDto branchCreatedEvent() {
        return readPushEvent();
    }

    private PushEventDto branchUpdatedEvent() {
        return readPushEvent(jsonForBranchUpdated());
    }

    private PushEventDto branchDeletedEvent() {
        return readPushEvent(jsonForBranchDeleted());
    }

    private PushEventDto readPushEvent() {
        return readPushEvent(jsonForBranchCreated());
    }

    private PushEventDto readPushEvent(String json) {
        return read(json, PushEventDto.class);
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