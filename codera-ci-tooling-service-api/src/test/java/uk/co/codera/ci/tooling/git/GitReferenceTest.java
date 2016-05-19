package uk.co.codera.ci.tooling.git;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import uk.co.codera.ci.tooling.git.GitReference;

public class GitReferenceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static final String VALID_HEADS_REFERENCE = "refs/heads/feature/JT-001-first-branch";
    private static final String VALID_TAGS_REFERENCE = "refs/tags/feature/JT-001-first-branch";
    private static final String INVALID_REFERENCE = "invalid-reference";

    @Test
    public void shouldBeAbleToConstructFromValidReference() {
        assertThat(GitReference.from(VALID_HEADS_REFERENCE), is(notNullValue()));
    }

    @Test
    public void toStringShouldRepresentFullReference() {
        assertThat(GitReference.from(VALID_HEADS_REFERENCE).toString(), is(equalTo(VALID_HEADS_REFERENCE)));
    }

    @Test
    public void shouldReportBranchNameCorrectly() {
        assertThat(GitReference.from(VALID_HEADS_REFERENCE).branchName(), is("feature/JT-001-first-branch"));
    }

    @Test
    public void shouldNotShortenBranchNameForMaster() {
        assertThat(GitReference.from("refs/heads/master").shortBranchName(), is("master"));
    }

    @Test
    public void shouldNotShortenBranchNameForCustomBranchName() {
        assertThat(GitReference.from("refs/heads/custom-branch-name").shortBranchName(), is("custom-branch-name"));
    }

    @Test
    public void shouldShortenBranchNameForFeatureBranchName() {
        assertThat(GitReference.from("refs/heads/feature/some-feature").shortBranchName(), is("some-feature"));
    }

    @Test
    public void invalidReferenceShouldThrowException() {
        this.expectedException.expect(IllegalArgumentException.class);
        this.expectedException.expectMessage("Invalid git reference [" + INVALID_REFERENCE + "]");
        GitReference.from(INVALID_REFERENCE);
    }

    @Test
    public void sameInstanceShouldBeEqual() {
        GitReference reference = GitReference.from("refs/heads/master");
        assertThat(reference, is(equalTo(reference)));
    }

    @Test
    public void differentInstanceButSameReferenceShouldBeEqual() {
        assertThat(GitReference.from("refs/heads/master"), is(equalTo(GitReference.from("refs/heads/master"))));
    }

    @Test
    public void differentReferenceShouldNotBeEqual() {
        assertThat(GitReference.from("refs/heads/feature/AB-123"),
                is(not(equalTo(GitReference.from("refs/heads/master")))));
    }

    @Test
    public void shouldNotBeEqualToNull() {
        assertThat(GitReference.from("refs/heads/master"), is(not(equalTo(null))));
    }

    @Test
    public void shouldNotBeEqualToDifferentType() {
        assertThat(GitReference.from("refs/heads/master"), is(not(equalTo(new Object()))));
    }

    @Test
    public void sameInstanceShouldHaveSameHashCode() {
        GitReference reference = GitReference.from("refs/heads/master");
        assertThat(reference.hashCode(), is(equalTo(reference.hashCode())));
    }

    @Test
    public void differentInstanceButSameReferenceShouldHaveSameHashCode() {
        assertThat(GitReference.from("refs/heads/master").hashCode(), is(equalTo(GitReference.from("refs/heads/master")
                .hashCode())));
    }

    @Test
    public void differentReferenceShouldNotHaveSameHashCode() {
        assertThat(GitReference.from("refs/heads/feature/AB-123").hashCode(),
                is(not(equalTo(GitReference.from("refs/heads/master").hashCode()))));
    }
    
    @Test
    public void shouldBeAbleToConstructFromValidTagsReference() {
        assertThat(GitReference.from(VALID_TAGS_REFERENCE), is(notNullValue()));
    }

    @Test
    public void shouldIdentifyTagReferencesAsTag() {
        assertThat(GitReference.from(VALID_TAGS_REFERENCE).isTag(), is(true));
    }

    @Test
    public void shouldNotIdentifyHeadReferencesAsTag() {
        assertThat(GitReference.from(VALID_HEADS_REFERENCE).isTag(), is(false));
    }

    @Test
    public void shouldReportTagReferencesBranchNameCorrectly() {
        assertThat(GitReference.from(VALID_TAGS_REFERENCE).branchName(), is("feature/JT-001-first-branch"));
    }

    @Test
    public void tagAndHeadReferencesWithSameBranchNameShouldNotBeEqual() {
        assertThat(GitReference.from(VALID_HEADS_REFERENCE), is(not(equalTo(GitReference.from(VALID_TAGS_REFERENCE)))));
    }
}