package uk.co.codera.ci.tooling.application;

import uk.co.codera.ci.tooling.api.bitbucket.BitBucketResource;
import uk.co.codera.ci.tooling.api.bitbucket.PushEventDtoAdapter;
import uk.co.codera.ci.tooling.git.GitEventListener;

public class BitBucketResourceFactory {

    private BitBucketResourceFactory() {
        super();
    }

    public static BitBucketResource create(BitBucketConfiguration configuration, GitEventListener gitEventBroadcaster) {
        PushEventDtoAdapter gitPushEventAdapter = new PushEventDtoAdapter(configuration.getBitBucketServerName(),
                configuration.getBitBucketServerPort());
        return new BitBucketResource(gitPushEventAdapter, gitEventBroadcaster);
    }
}