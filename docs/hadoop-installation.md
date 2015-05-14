# Installing Apache Hadoop (Mac Yosemite)
### 1. Pre-requisites
Download and install Java from [Oracle](http://www.oracle.com/technetwork/java/javase/downloads/index.html). This project requires Java 8. Make sure that Java is installed and PATH variable is set properly.
```
% java -version
java version "1.8.0_31"
Java(TM) SE Runtime Environment (build 1.8.0_31-b13)
Java HotSpot(TM) 64-Bit Server VM (build 25.31-b07, mixed mode)
```

### 2. Insall Hadoop
1. Get Hadoop distribution from [Apache](https://hadoop.apache.org/#Download+Hadoop) site. This project was developed and tested with version 2.7.0.
2. Set JAVA_HOME environment variable to point to a Java home directory.
```
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_31.jdk/Contents/Home
```
3. Set HADOOP_HOME environment variable to point your Hadoop installation directory:
```
% export HADOOP_HOME=~/dev/hadoop-2.7.0
% export PATH=$PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin
```
Check that Hadoop runs:
```
% hadoop version
Hadoop 2.7.0
Subversion https://git-wip-us.apache.org/repos/asf/hadoop.git -r d4c8d4d4d203c934e8074b31289a28724c0842cf
Compiled by jenkins on 2015-04-10T18:40Z
Compiled with protoc 2.5.0
From source with checksum a9e90912c37a35c3195d23951fd18f
This command was run using /Users/dinecha/dev/hadoop-2.7.0/share/hadoop/common/hadoop-common-2.7.0.jar
```

### 3. Hadoop Configuraiton
Hadoop can be run in one of three modes:

1. Local (Standalone) Mode:
By default, Hadoop is configured to run in a non-distributed mode, as a single Java process. There are no daemons running and everything runs in a single JVM. Standalone mode is suitable for running MapReduce programs during development, since it is easy to test and debug them.
2. Pseudo-Distributed Mode:
Hadoop can also be run on a single-node in a pseudo-distributed mode where each Hadoop daemon runs in a separate Java process.
3. Fully-Distributed Mode:
The Hadoop daemons run on a cluster of machines.

To run Hadoop in a particular mode, you need to do two things:
1. set the appropriate properties, and
2. start the Hadoop daemons.

Key Configuration properties for different modes

| Component |           Properties          |     Standalone     | Pseudo-distributed |    Distributed    |
|:---------:|:-----------------------------:|:------------------:|:-----------------:|:-----------------:|
|   common  |          fs.defaultFS (core-site.xml)         | file:/// (default) | hdfs://localhost/ |  hdfs://namenode/ |
|    HDFS   |        dfs.replication        |         N/A        |         1         |    3 (default)    |
| MapReduce |    mapreduce.framework.name   |   local (default)  |        yarn       |        yarn       |
|    YARN   | yarn.resourcemanager.hostname |         N/A        |     localhost     |  resourcemanager  |
|    YARN   | yarn.nodemanager.aux-services |         N/A        | mapreduce_shuffle | mapreduce_shuffle |

### 4. Configurations for Pseudo-distributed Mode:
etc/hadoop/core-site.xml
```
<?xml version="1.0"?>
<!-- core-site.xml -->
<configuration>
  <property>
    <name>fs.defaultFS</name>
    <value>hdfs://localhost/</value>
  </property>
</configuration>
```
etc/hadoop/hdfs-site.xml
```
<?xml version="1.0"?>
<!-- hdfs-site.xml -->
<configuration>
  <property>
    <name>dfs.replication</name>
    <value>1</value>
  </property>
</configuration>
```
etc/hadoop/mapred-site.xml
```
 <?xml version="1.0"?>
<!-- mapred-site.xml -->
<configuration>
  <property>
    <name>mapreduce.framework.name</name>
    <value>yarn</value>
  </property>
</configuration>
```
etc/hadoop/yarn-site.xml
```
<?xml version="1.0"?>
<!-- yarn-site.xml -->
<configuration>
  <property>
    <name>yarn.resourcemanager.hostname</name>
    <value>localhost</value>
  </property>
  <property>
    <name>yarn.nodemanager.aux-services</name>
    <value>mapreduce_shuffle</value>
  </property>
</configuration>
```

###5. Configuring SSH
In pseudodistributed mode, we have to start daemons, and to do that using the supplied scripts we need to have SSH installed. Install it if not already installed:
```
% sudo apt-get install ssh
```

> On Mac OS X, make sure Remote Login (under System Preferences→Sharing) is enabled for the current user (or all users).

Then, to enable passwordless login, generate a new SSH key with an empty passphrase:
```
% ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa
% cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
```
Test that you can connect with:
```
% ssh localhost
```
If successful, you should not have to type in a password.

### 6. Formatting the HDFS filesystem
Before HDFS can be used for the first time, the filesystem must be formatted. This is done by running the following command:
```
% hdfs namenode -format
```

### 7. Starting and stopping the daemons
To start the HDFS and YARN, type:
```
% start-dfs.sh
% start-yarn.sh
```
Check if daemons are started successfully:

>http://localhost:50070/ for the namenode

>http://localhost:8088/ for the YARN resource manager

Or You can also use Java’s jps command to see whether the processes are running.
```
% jps
13619 ResourceManager
13715 NodeManager
13381 DataNode
13288 NameNode
13499 SecondaryNameNode
13741 Jps
```
To stop deamons, type:
```
% stop-yarn.sh
% stop-dfs.sh
```

### 8. Creating a user directory

Create a home directory for yourself by running the following:
```
% hadoop fs -mkdir -p /user/$USER
```

### 9. Persisting NameNode metadata
Whenever you restart and execute ```start-dfs.sh``` NameNode is not up and you have to do ```hadoop namenode -format``` and then ```start-dfs.sh``` and ```start-mapred.sh``` which forces you to load the data every time. This happens because by default it points to /tmp directory which will be cleared when your machine restarts. Inorder to persist the metadata you have to change this from /tmp to another location in your home directory by overriding ```dfs.name.dir``` and ```dfs.data.dir``` in hdfs-site.xml file.

First create a directory, e.g
```
% mkdir /home/<USER>/pseudo/
```
Then modify hdfs-site.xmls to point to this directory:
```
<?xml version="1.0"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<configuration>
<property>
  <name>dfs.name.dir</name>
  <value>file:///home/<USER>/pseudo/dfs/name</value>
</property>
<property>
  <name>dfs.data.dir</name>
  <value>file:///home/<USER>/pseudo/dfs/data</value>
</property>
</configuration>
```
Now format your noamenode one lasttime and  restart your deamons. You don't have to do the formatting everytime you restart your machine.

### References
1. http://zhongyaonan.com/hadoop-tutorial/setting-up-hadoop-2-6-on-mac-osx-yosemite.html
2. Hadoop The Definitive Guide - 4th Edition.
