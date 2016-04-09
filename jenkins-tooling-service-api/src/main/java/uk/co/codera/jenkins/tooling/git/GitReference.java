package uk.co.codera.jenkins.tooling.git;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitReference {

    private static final Pattern PATTERN_VALID_GIT_REFERENCE = Pattern.compile("/refs/\\w*/(.*)");
    
    private final String fullReference;
    private final String shortReference;
    
    private GitReference(String reference) {
        Matcher matcher = validate(reference);
        this.fullReference = reference;
        this.shortReference = matcher.group(1);
    }
    
    public static GitReference from(String reference) {
        return new GitReference(reference);
    }
    
    public String branchName() {
        return this.shortReference;
    }
    
    @Override
    public String toString() {
        return this.fullReference;
    }
    
    private Matcher validate(String reference) {
        Matcher matcher = matcher(reference);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(String.format("Invalid git reference [%s]", reference));
        }
        return matcher;
    }

    private Matcher matcher(String reference) {
        return PATTERN_VALID_GIT_REFERENCE.matcher(reference);
    }
}