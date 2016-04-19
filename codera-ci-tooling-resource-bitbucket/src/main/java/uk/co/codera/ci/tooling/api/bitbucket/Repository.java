package uk.co.codera.ci.tooling.api.bitbucket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Repository {

	private String slug;
	private Project project;

	public static Repository.Builder aRepository() {
		return new Builder();
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getSlug() {
		return this.slug;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Project getProject() {
		return this.project;
	}

	public static class Builder {

		private String slug;
		private Project project;

		private Builder() {
			super();
		}

		public Builder slug(String slug) {
			this.slug = slug;
			return this;
		}

		public Builder with(Project project) {
			this.project = project;
			return this;
		}

		public Repository build() {
			Repository repository = new Repository();
			repository.setSlug(this.slug);
			repository.setProject(this.project);
			return repository;
		}
	}
}