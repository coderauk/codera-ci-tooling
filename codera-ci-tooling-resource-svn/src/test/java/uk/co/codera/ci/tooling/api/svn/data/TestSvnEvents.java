package uk.co.codera.ci.tooling.api.svn.data;

import static uk.co.codera.ci.tooling.api.svn.dto.SvnEventDto.anSvnEventDto;

import uk.co.codera.ci.tooling.api.svn.dto.SvnEventDto;

public class TestSvnEvents {

    public static SvnEventDto.Builder aValidSvnEvent() {
        return anSvnEventDto();
    }
}