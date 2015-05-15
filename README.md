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
1. Install and configure - Java 8, Hadoop 2.7.0 & Maven.
2. Clone this project repo. It's a maven project.
3. Create jar file by executing:
```
% mvn clean package
```
This will generate jar file under ```target``` folder.

4. Start HDFS and YARN
```
% start-dfs.sh
% start-yarn.sh
```
5. Run the ```submitjobs.sh``` script available in project's root folder. First make sure that script is executable.
```
% chmod +x submitjobs.sh
% ./submitjobs.sh
```
MapReduce jobs' outputs will be available in ```output``` folder under project's root folder.

#### Demo Jobs Details:
- 3 sample input files
    - [events01.txt](./input/events01.txt)
    - [events02.txt](./input/events02.txt)
    - [events03.txt](./input/events03.txt)
- One job per approach
    - [```RF_PairsJob```](./src/main/java/cs522/lab/pair/RF_PairsJob.java)
    - [```RF_StripesJob```](mapreduce-algo/src/main/java/cs522/lab/stripe/RF_StripesJob.java)
    - [```RF_HybridJob```](mapreduce-algo/src/main/java/cs522/lab/hybrid/RF_HybridJob.java)
- Each job is configured to have 3 reducers.
- Outputs of a demo run on pseudo-distributed mode
    - [Pairs approach output](./output/pairs_rf/)
    - [Stripe approach output](./output/stripes_rf/)
    - [Hybrid approach output](./output/hybrid_rf/)

#### What does [```submitjobs.sh```](submitjobs.sh) do?
1. It moves sample input data available in [```input```](./input) folder to HDFS. Before that it deletes and creates HDFS ```input``` folder.
2. Deletes HDFS ```output``` folder.
3. Deletes ```input``` and ```output``` folder in HDFS
4. Submits ```RF_PairsJob```, ```RF_StripesJob``` and ```RF_HybridJob``` sequentially.
5. Once jobs are completed, deletes and creates local ```output``` folder.
6. Copies MapReduce outputs to local ```output``` folder.

>NOTE: Script deletes and creates ```input``` and ```output``` folders (under project's root path) on every execution to avoid ... If folders are not available script will print error messages, ignore it.
