# codera-ci-tooling
Tooling for Continuous Integration (CI), allows automated creation, deletion of jobs, etc

## Configuration

The application server is configured using a YAML file. An example configuration is shown below:

```
server:
  applicationConnectors:
    - type: http
      port: 7060
  adminConnectors:
    - type: http
      port: 7061

bitBucketServerName: {serverName}
bitBucketServerPort: {serverPort}

jenkinsServerUrl: {serverUrl}
jenkinsJobTemplateFile: {templateFile}
```

Replace the tokens in braces with the appropriate values for your setup.


