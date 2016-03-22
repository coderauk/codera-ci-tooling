package uk.co.codera.jenkins.tooling.git;

public class GitPushEvent {

	private final PushType type;
	
	private GitPushEvent(Builder builder) {
		this.type = builder.type;
	}
	
	public PushType getType() {
		return type;
	}
	
	public static Builder aGitPushEvent() {
		return new Builder();
	}
	
	public static class Builder {
		
		private PushType type;
		
		private Builder() {
			super();
		}
		
		public Builder type(PushType type) {
			this.type = type;
			return this;
		}
		
		public GitPushEvent build() {
			return new GitPushEvent(this);
		}
	}
}