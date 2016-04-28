# codera-ci-tooling
Tooling for Continuous Integration (CI), allows automated creation, deletion of jobs, etc

## Application Configuration

The application server is configured using a YAML file. An example configuration is shown below:

```yaml
# Standard dropwizard configuration for the application and admin http connection. 
# Could be configured to use https in the standard way. See the dropwizard guide for more information
server:
  applicationConnectors:
    - type: http
      port: 7060
  adminConnectors:
    - type: http
      port: 7061

# The name of the server hosting bitbucket
# If the url is https://myserver:8080/bitbucket then the server name is myserver
bitBucketServerName: {serverName}

# The port of the server hosting bitbucket
# If the url is https://myserver:8080/bitbucket then the port is 8080
bitBucketServerPort: {serverPort}

# The full url of the jenkins server
# If the jenkins page is accessed at https://myserver:8081/jenkins then the url is https://myserver:8081/jenkins
jenkinsServerUrl: {serverUrl}

# The location of the template file that will be used to create jobs in jenkins. 
# Currently the application only supports one template for all jobs. 
# The path can be relative to where the application is started from or an absolute path
jenkinsJobTemplateFile: {templateFile}

# Optional block, if not set then sonar listener will not be instantiated. 
# If set then it will delete sonar projects when the branch is deleted. 
# Currently it is coded to assume the project key is in the form <repositoryName>:<shortBranchName>. 
# Future versions will allow this to be templated.
sonar:
  # The url of the sonar server, e.g. https://myserver.co.uk/sonar/. 
  # If the block is set this is required.
  sonarUrl: {sonarUrl}

  # The sonar user name. This user must have admin privilages to be able to delete projects. 
  # If the block is set this is required.
  user: <username>

  # The password for the sonar user. 
  # If the block is set this is required.
  password: <password>

```

Replace the tokens in braces with the appropriate values for your setup.

## Deployment and running

1. Copy the codera-ci-tooling-application-<version>.jar file to the server which will run the application.
2. Create a configuration file using the template defined above. The following steps assume the config file is called codera-ci-tooling-application.yaml and is created in the same directory as the jar file.
3. Download the correct Jenkins CLI library for you Jenkins instance. This can be obtained from Jenkins using the URL <jenkins-url>/jnlpJars/jenkins-cli.jar.
4. Place the Jenkins CLI jar file in the same directory as the application, it should be called jenkins-cli.jar.
5. The application can now be started using the command:

    ```bash
    nohup java -cp jenkins-cli.jar:codera-ci-tooling-application-<version>.jar uk.co.codera.ci.tooling.application.CiToolingApplication server codera-ci-tooling-application.yaml > codera-ci-tooling-application.out 2> codera-ci-tooling-application.err < /dev/null  &
    ```

6. Check the application has started by looking in the codera-ci-tooling-application.out file. It should report it is up and running:

    ```bash
    INFO  [2016-04-19 20:27:58,822] org.eclipse.jetty.server.handler.ContextHandler: Started i.d.j.MutableServletContextHandler@441cc260{/,null,AVAILABLE}
    INFO  [2016-04-19 20:27:58,826] org.eclipse.jetty.server.ServerConnector: Started application@7756c3cd{HTTP/1.1}{0.0.0.0:7060}
    INFO  [2016-04-19 20:27:58,827] org.eclipse.jetty.server.ServerConnector: Started admin@2313052e{HTTP/1.1}{0.0.0.0:7061}
    INFO  [2016-04-19 20:27:58,827] org.eclipse.jetty.server.Server: Started @1050ms
    ```



