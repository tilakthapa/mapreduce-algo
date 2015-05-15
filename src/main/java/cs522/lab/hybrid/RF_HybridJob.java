package cs522.lab.hybrid;

import cs522.lab.common.Pair;
import cs522.lab.common.PairPartitioner;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Created by tilak on 5/11/15.
 */
public class RF_HybridJob {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if (args.length != 2) {
            System.out.println("Arguments missing.");
            System.exit(-1);
        }

        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf, "RF Hybrid Approach");

        job.setJarByClass(RF_HybridJob.class);

        job.setMapperClass(RF_HybridMapper.class);
        job.setReducerClass(RF_HybridReducer.class);

        job.setOutputKeyClass(Pair.class);
        job.setOutputValueClass(IntWritable.class);

        job.setPartitionerClass(PairPartitioner.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
