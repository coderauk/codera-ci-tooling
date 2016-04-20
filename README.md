# codera-ci-tooling
Tooling for Continuous Integration (CI), allows automated creation, deletion of jobs, etc

## Application Configuration

The application server is configured using a YAML file. An example configuration is shown below:

```
# Standard dropwizard configuration for the application and admin http connection. Could be configured to use https in the standard way. See the dropwizard guide for more information
server:
  applicationConnectors:
    - type: http
      port: 7060
  adminConnectors:
    - type: http
      port: 7061

# The name of the server hosting bitbucket, for example if the url is https://myserver:8080/bitbucket then the server name is myserver
bitBucketServerName: {serverName}

# The port of the server hosting bitbucket, for example if the url is https://myserver:8080/bitbucket then the port is 8080
bitBucketServerPort: {serverPort}

# The full url of the jenkins server, if the jenkins page is accessed at https://myserver:8081/jenkins then the url is https://myserver:8081/jenkins
jenkinsServerUrl: {serverUrl}

# The location of the template file that will be used to create jobs in jenkins. Currently the application only supports one template for all jobs. The path can be relative to where the application is started from or an absolute path
jenkinsJobTemplateFile: {templateFile}
```

Replace the tokens in braces with the appropriate values for your setup.

## Deployment and running

1. Copy the codera-ci-tooling-application-<version>.jar file to the server which will run the application.
1. Create a configuration file using the template defined above. The following steps assume the config file is called codera-ci-tooling-application.yaml and is created in the same directory as the jar file.
1. Download the correct Jenkins CLI library for you Jenkins instance. This can be obtained from Jenkins using the URL <jenkins-url>/jnlpJars/jenkins-cli.jar.
1. Place the Jenkins CLI jar file in the same directory as the application, it should be called jenkins-cli.jar.
1. The application can now be started using the command `nohup java -cp jenkins-cli.jar:codera-ci-tooling-application-<version>.jar uk.co.codera.ci.tooling.application.CiToolingApplication server codera-ci-tooling-application.yaml > codera-ci-tooling-application.out 2> codera-ci-tooling-application.err < /dev/null  &`.



