package cs522.lab.pair;

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
public class RF_PairsJob {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if (args.length != 2) {
            System.out.println("Arguments missing.");
            System.exit(-1);
        }

        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf, "RF Pairs Approach");

        job.setJarByClass(RF_PairsJob.class);

        job.setMapperClass(RF_PairsMapper.class);
        job.setReducerClass(RF_PairsReducer.class);

        job.setPartitionerClass(PairPartitioner.class);

        job.setOutputKeyClass(Pair.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
