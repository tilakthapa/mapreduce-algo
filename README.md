# MapReduce Algorithms
Calculating relative frequencies using

1. Pair Approach
    * [Mapper](./src/main/java/cs522/lab/pair/RF_PairsMapper.java)
    * [Reducer](./src/main/java/cs522/lab/pair/RF_PairsReducer.java)
2. Stripe Approach
    * [Mapper](./src/main/java/cs522/lab/stripe/StripesMapper.java)
    * [Reducer](./src/main/java/cs522/lab/stripe/RF_StripesReducer.java)
3. Hybrid Approach
    * [Mapper](./src/main/java/cs522/lab/hybrid/RF_HybridMapper.java)
    * [Reducer](./src/main/java/cs522/lab/hybrid/RF_HybridReducer.java)

#### [Hadoop Installation & Configurations](./docs/hadoop-installation.md)

#### Developed and tested with:
1. Java 8, and
2. Hadoop 2.7.0

#### Executing Jobs:
1. Install maven if you haven't already.
1. Checkout the project.
2. Create jar file by executing:
```
% mvn clean package
```
This will generate jar file under ```target``` folder.

3. Run the ```submitjobs.sh``` script available in project's root folder.
```
% ./submitjobs.sh
```
MapReduce jobs' outputs will be available in ```output``` folder under project's root folder.

#### What dos [```submitjobs.sh```](submitjobs.sh) does?
1. It moves sample input data available in ```input``` folder to HDFS. Before that it deletes and creates HDFS ```input``` folder.
2. Deletes HDFS ```output``` folder.
3. Deletes ```input``` and ```output``` folder in HDFS
4. Submits [```RF_PairsJob```](./src/main/java/cs522/lab/pair/RF_PairsJob.java), [```RF_StripesJob```](mapreduce-algo/src/main/java/cs522/lab/stripe/RF_StripesJob.java) and [```RF_HybridJob```](mapreduce-algo/src/main/java/cs522/lab/hybrid/RF_HybridJob.java) sequentially.
5. Once jobs are completed, deletes local ```output``` folder.
6. Copies MapReduce outputs to local ```output``` folder.

NOTE: it deletes ```input``` and ```output``` folders on every execution to avoid ...
