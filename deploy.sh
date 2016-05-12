#!/bin/bash

USER=$1
HOST=$2

CONNECT_STRING=$USER@$HOST

echo "Copying deployment onto the box"
scp codera-ci-tooling-application/target/codera-ci-tooling-application-0.0.3-SNAPSHOT.jar $CONNECT_STRING:

echo "Copied deployment onto box, stopping old deployment"
ssh $CONNECT_STRING "pkill -u $USER java"

echo "Starting new deployment"
ssh $CONNECT_STRING "nohup java -cp jenkins-cli.jar:codera-ci-tooling-application-0.0.3-SNAPSHOT.jar uk.co.codera.ci.tooling.application.CiToolingApplication server codera-ci-tooling-application.yaml > codera-ci-tooling-application.out 2> codera-ci-tooling-application.err < /dev/null  &"
