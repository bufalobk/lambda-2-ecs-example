#
# OpenJDK Java 7 JRE Dockerfile
#
# https://github.com/dockerfile/java
# https://github.com/dockerfile/java/tree/master/openjdk-7-jre
#

# Pull base image.
FROM ubuntu:14.04

# Install Java.
RUN \
  sed -i 's/# \(.*multiverse$\)/\1/g' /etc/apt/sources.list && \
  apt-get update && \
  apt-get -y upgrade && \
  apt-get install -y build-essential && \
  apt-get install -y software-properties-common && \
  apt-get install -y byobu curl git htop man unzip vim wget && \
  apt-get install -y openjdk-7-jre && \
  apt-get install -y python python-pip && \
  rm -rf /var/lib/apt/lists/*

RUN \
  pip install awscli
  
# Define working directory.
WORKDIR /data

RUN \
  echo "#!/bin/bash\nif [ \"\$DEDICATE_ECS_INSTANCE\" == \"True\" ]; then aws ec2 terminate-instances --instance-ids \$(curl http://169.254.169.254/latest/dynamic/instance-identity/document | grep instanceId |awk -F\\\" '{print \$4}') --region \$(curl http://169.254.169.254/latest/dynamic/instance-identity/document|grep region|awk -F\\\" '{print \$4}'); fi" \
  >> terminateme.sh 

RUN \
  chmod +x terminateme.sh 

ADD Lambda2ECSExample.jar Lambda2ECSExample.jar

# Define commonly used JAVA_HOME variable
ENV JAVA_HOME /usr/lib/jvm/java-7-openjdk-amd64

# Define default command.
CMD ["java", "-jar", "Lambda2ECSExample.jar"]

CMD ["./terminateme.sh"]
