package uk.co.codera.jenkins.tooling.jenkins;

import java.util.HashMap;
import java.util.Map;

import uk.co.codera.jenkins.tooling.git.GitPushEvent;
import uk.co.codera.templating.TemplateEngine;

public class JenkinsJobFactory {

    public static final String PARAMETER_BRANCH_NAME = "branchName";
    public static final String PARAMETER_SHORT_BRANCH_NAME = "shortBranchName";
    public static final String PARAMETER_REPOSITORY_URL = "repositoryUrl";
    public static final String PARAMETER_REPOSITORY_NAME = "repositoryName";
    
    private final TemplateEngine templateEngine;
    private final String jobTemplate;
    
    public JenkinsJobFactory(TemplateEngine templateEngine, String jobTemplate) {
        this.templateEngine = templateEngine;
        this.jobTemplate = jobTemplate;
    }

    public String create(GitPushEvent event) {
        Map<String, Object> params = new HashMap<>();
        params.put(PARAMETER_BRANCH_NAME, event.getReference().branchName());
        params.put(PARAMETER_SHORT_BRANCH_NAME, event.getReference().shortBranchName());
        params.put(PARAMETER_REPOSITORY_URL, event.getRepositoryUrl());
        params.put(PARAMETER_REPOSITORY_NAME, event.getRepositoryName());
        return this.templateEngine.merge(this.jobTemplate, params);
    }
}