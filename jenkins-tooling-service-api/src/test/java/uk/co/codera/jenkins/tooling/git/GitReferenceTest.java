package uk.co.codera.jenkins.tooling.git;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GitReferenceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    private static final String VALID_REFERENCE = "/refs/heads/feature/JT-001-first-branch";
    private static final String INVALID_REFERENCE = "invalid-reference";
    
    @Test
    public void shouldBeAbleToConstructFromValidReference() {
        assertThat(GitReference.from(VALID_REFERENCE), is(notNullValue()));
    }
    
    @Test
    public void toStringShouldRepresentFullReference() {
        assertThat(GitReference.from(VALID_REFERENCE).toString(), is(equalTo(VALID_REFERENCE)));
    }
    
    @Test
    public void shortReportBranchNameCorrectly() {
        assertThat(GitReference.from(VALID_REFERENCE).branchName(), is("feature/JT-001-first-branch"));
    }
    
    @Test
    public void invalidReferenceShouldThrowException() {
        this.expectedException.expect(IllegalArgumentException.class);
        this.expectedException.expectMessage("Invalid git reference [" + INVALID_REFERENCE + "]");;
        GitReference.from(INVALID_REFERENCE);
    }
}