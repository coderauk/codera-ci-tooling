package uk.co.codera.ci.tooling.scm;

@FunctionalInterface
public interface ScmEventListener {

    void on(ScmEvent event);
}