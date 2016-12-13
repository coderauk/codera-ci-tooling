package uk.co.codera.ci.tooling.application;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import uk.co.codera.ci.tooling.scm.ScmEventBroadcaster;
import uk.co.codera.ci.tooling.scm.ScmEventListener;

public class CiToolingApplication extends Application<CiToolingConfiguration> {

    public static void main(String[] args) throws Exception {
        new CiToolingApplication().run(args);
    }

    @Override
    public void run(CiToolingConfiguration configuration, Environment environment) throws Exception {
        ScmEventListener scmEventBroadcaster = scmEventBroadcaster(configuration);
        ResourceConfigurer.configure(environment.jersey(), configuration, scmEventBroadcaster);
    }

    private ScmEventListener scmEventBroadcaster(CiToolingConfiguration configuration) {
        ScmEventBroadcaster scmEventBroadcaster = new ScmEventBroadcaster();
        ScmEventBroadcasterConfigurer.configure(scmEventBroadcaster, configuration);
        return scmEventBroadcaster;
    }
}