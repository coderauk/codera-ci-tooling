package uk.co.codera.jenkins.tooling.api.bitbucket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import uk.co.codera.jenkins.tooling.git.PushType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RefChange {

	private String refId;
	
	private PushType type;
	
	public String getRefId() {
		return this.refId;
	}
	
	public void setRefId(String refId) {
		this.refId = refId;
	}

	public PushType getType() {
		return this.type;
	}
	
	public void setType(PushType type) {
		this.type = type;
	}
}