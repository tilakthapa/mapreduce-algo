#!/bin/bash

INPUT_DIR="input"
OUTPUT_DIR="output"

PAIRS_OUTPUT_DIR="$OUTPUT_DIR/pairs_rf"
STRIPES_OUTPUT_DIR="$OUTPUT_DIR/stripes_rf"
HYBRID_OUTPUT_DIR="$OUTPUT_DIR/hybrid_rf"
JAR_PATH="target/bigdata-lab-1.0-SNAPSHOT.jar"

echo 'Deleting HDFS input folder ...'
hadoop fs -rm -r -skipTrash $INPUT_DIR

echo 'Creating new HDFS input folder ...'
hadoop fs -mkdir $INPUT_DIR

echo 'Copying local input files into HDFS ...'
hadoop fs -put $INPUT_DIR

echo 'Deleting HDFS output directory ...'
hadoop fs -rm -r -skipTrash $OUTPUT_DIR

echo 'Submitting RF_PairsJob ...'
hadoop jar $JAR_PATH cs522.lab.pair.RF_PairsDriver $INPUT_DIR $PAIRS_OUTPUT_DIR
echo 'Submitting RF_StripesJob ...'
hadoop jar $JAR_PATH cs522.lab.stripe.RF_StripesDriver $INPUT_DIR $STRIPES_OUTPUT_DIR
echo 'Submitting RF_HybridJob ...'
hadoop jar $JAR_PATH cs522.lab.hybrid.RF_HybridDriver $INPUT_DIR $HYBRID_OUTPUT_DIR

# copy output to local
LOCAL_OUTPUT_DIR=output

# delete local output folder
echo 'Deleting local output folder ...'
rm -r $OUTPUT_DIR

echo 'Creating new local output folder ...'
mkdir $OUTPUT_DIR

echo 'Copying HDFS output to local filesystem ...'
hadoop fs -copyToLocal $OUTPUT_DIR