package uk.co.codera.jenkins.tooling.git;

public class GitPushEvent {

	private final GitPushType pushType;
	
	private GitPushEvent(Builder builder) {
		this.pushType = builder.pushType;
	}
	
	public GitPushType getPushType() {
		return pushType;
	}
	
	public static Builder aGitPushEvent() {
		return new Builder();
	}
	
	public static class Builder {
		
		private GitPushType pushType;
		
		private Builder() {
			super();
		}
		
		public Builder pushType(GitPushType type) {
			this.pushType = type;
			return this;
		}
		
		public GitPushEvent build() {
			return new GitPushEvent(this);
		}
	}
}