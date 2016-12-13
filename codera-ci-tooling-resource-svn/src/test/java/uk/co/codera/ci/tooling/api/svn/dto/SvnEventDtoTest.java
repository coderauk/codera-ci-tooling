package uk.co.codera.ci.tooling.api.svn.dto;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static uk.co.codera.ci.tooling.api.svn.data.TestSvnEvents.aValidSvnEvent;

import org.junit.Test;

public class SvnEventDtoTest {

    @Test
    public void toStringShouldNotBeObjectReference() {
        assertThat(aValidSvnEvent().build().toString(), containsString("location="));
    }
}