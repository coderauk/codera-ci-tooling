package uk.co.codera.ci.tooling.api.bitbucket.dto;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static uk.co.codera.ci.tooling.api.bitbucket.data.TestPushEvents.aValidRepository;

import org.junit.Test;

public class RepositoryDtoTest {

    @Test
    public void toStringShouldNotBeObjectReference() {
        assertThat(repository().toString(), containsString("slug="));
    }

    private RepositoryDto repository() {
        return aValidRepository().build();
    }
}