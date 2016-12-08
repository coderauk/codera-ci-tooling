package uk.co.codera.ci.tooling.svn;

@FunctionalInterface
public interface SvnEventListener {

    void onCommit(SvnCommitEvent event);
}