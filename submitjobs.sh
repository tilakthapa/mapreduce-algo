#!/bin/bash

INPUT_DIR="input"
OUTPUT_DIR="output"

PAIRS_OUTPUT_DIR="$OUTPUT_DIR/pairs_rf"
STRIPES_OUTPUT_DIR="$OUTPUT_DIR/stripes_rf"
HYBRID_OUTPUT_DIR="$OUTPUT_DIR/hybrid_rf"

echo 'Deleting HDFS input folder ...'
hadoop fs -rm -r -skipTrash $INPUT_DIR

echo 'Creating new HDFS input folder ...'
hadoop fs -mkdir $INPUT_DIR

echo 'Copying local input files into HDFS ...'
hadoop fs -put $INPUT_DIR

echo 'Deleting HDFS output directory ...'
hadoop fs -rm -r -skipTrash $OUTPUT_DIR

echo 'Submitting RF_PairsJob ...'
hadoop jar target/bigdata-lab-1.0-SNAPSHOT.jar cs522.lab.pairs.RF_PairsJob $INPUT_DIR $PAIRS_OUTPUT_DIR
echo 'Submitting RF_StripesJob ...'
hadoop jar target/bigdata-lab-1.0-SNAPSHOT.jar cs522.lab.stripes.RF_StripesJob $INPUT_DIR $STRIPES_OUTPUT_DIR
echo 'Submitting RF_HybridJob ...'
hadoop jar target/bigdata-lab-1.0-SNAPSHOT.jar cs522.lab.hybrid.RF_HybridJob $INPUT_DIR $HYBRID_OUTPUT_DIR

# copy output to local
LOCAL_OUTPUT_DIR=output

# delete local output folder
echo 'Deleting local output folder ...'
rm -r $OUTPUT_DIR

echo 'Creating new local output folder ...'
mkdir $OUTPUT_DIR

echo 'Copying HDFS output to local filesystem ...'
hadoop fs -copyToLocal $OUTPUT_DIR