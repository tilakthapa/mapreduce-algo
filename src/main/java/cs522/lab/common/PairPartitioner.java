package cs522.lab.common;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * Created by tilak on 5/13/15.
 */
public class PairPartitioner extends Partitioner<Pair, IntWritable> {


    @Override
    public int getPartition(Pair pair, IntWritable intWritable, int numOfPartitions) {
        return pair.getFirst().hashCode() % numOfPartitions;
    }
}
