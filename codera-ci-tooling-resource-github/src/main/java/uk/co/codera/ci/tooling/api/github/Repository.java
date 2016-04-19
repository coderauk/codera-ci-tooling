package uk.co.codera.ci.tooling.api.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Repository {

	private String name;

	@JsonProperty(value = "ssh_url")
	private String sshUrl;

	public static Builder aRepository() {
		return new Builder();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setSshUrl(String sshUrl) {
		this.sshUrl = sshUrl;
	}

	public String getSshUrl() {
		return this.sshUrl;
	}

	public static class Builder {

		private String name;
		private String sshUrl;

		private Builder() {
			super();
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder sshUrl(String sshUrl) {
			this.sshUrl = sshUrl;
			return this;
		}

		public Repository build() {
			Repository repository = new Repository();
			repository.setName(this.name);
			repository.setSshUrl(this.sshUrl);
			return repository;
		}
	}
}