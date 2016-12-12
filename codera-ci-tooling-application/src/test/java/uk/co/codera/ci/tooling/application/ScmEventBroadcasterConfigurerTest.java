package uk.co.codera.ci.tooling.application;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static uk.co.codera.ci.tooling.application.CiToolingConfiguration.someCiToolingConfiguration;
import static uk.co.codera.ci.tooling.application.data.TestSonarConfigurations.randomSonarConfiguration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.codera.ci.tooling.scm.ScmEventBroadcaster;
import uk.co.codera.ci.tooling.scm.ScmEventListener;
import uk.co.codera.ci.tooling.scm.ScmEventLogger;

@RunWith(MockitoJUnitRunner.class)
public class ScmEventBroadcasterConfigurerTest {

    @Mock
    private ScmEventBroadcaster mockScmEventBroadcaster;

    @Test
    public void shouldAddEventLogger() {
        configure(someCiToolingConfiguration());
        verify(this.mockScmEventBroadcaster).registerListener(isA(ScmEventLogger.class));
        verifyNoMoreInteractions(this.mockScmEventBroadcaster);
    }

    @Test
    public void shouldConfigureSonarListenerIfRelevantConfigurationProvided() {
        configure(someCiToolingConfiguration().with(randomSonarConfiguration()));
        verify(this.mockScmEventBroadcaster, times(2)).registerListener(any(ScmEventListener.class));
    }

    private void configure(CiToolingConfiguration.Builder configuration) {
        ScmEventBroadcasterConfigurer.configure(this.mockScmEventBroadcaster, configuration.build());
    }
}