#!/bin/bash

USER=$1
HOST=$2

CONNECT_STRING=$USER@$HOST

echo "Copying deployment onto the box"
scp jenkins-tooling-application/target/codera-jenkins-tooling-application-0.0.1-SNAPSHOT.jar $CONNECT_STRING:

echo "Copied deployment onto box, stopping old deployment"
ssh $CONNECT_STRING "pkill -u $USER java"

echo "Starting new deployment"
ssh $CONNECT_STRING "nohup java -cp jenkins-cli.jar:codera-jenkins-tooling-application-0.0.1-SNAPSHOT.jar uk.co.codera.jenkins.tooling.application.JenkinsToolingApplication server jenkins-tooling-application.yaml > jenkins-tooling-application.out 2> jenkins-tooling-application.err < /dev/null  &"
