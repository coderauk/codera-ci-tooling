# codera-ci-tooling
Tooling for Continuous Integration (CI), allows automated creation, deletion of jobs, etc

## Configuration

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

## Deployment


