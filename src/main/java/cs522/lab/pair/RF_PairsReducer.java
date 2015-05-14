package cs522.lab.pair;

import cs522.lab.common.Pair;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

import static cs522.lab.common.Utils.round;

/**
 * Created by tilak on 5/11/15.
 */

public class RF_PairsReducer extends Reducer<Pair, IntWritable, Pair, DoubleWritable> {

    private double marginal = 0.0;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        marginal = 0.0;
    }

    @Override
    protected void reduce(Pair key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        Pair special = new Pair(key.getFirst(), "*");

        double sum = doSum(values);
        if (key.equals(special)) {
            marginal = sum;
        } else {
            double rf = round(sum / marginal);
            context.write(key, new DoubleWritable(rf));
        }
    }

    private int doSum(Iterable<IntWritable> values) {
        int sum = 0;
        Iterator<IntWritable> iterator = values.iterator();
        while (iterator.hasNext()) {
            IntWritable next = iterator.next();
            sum += next.get();
        }
        return sum;
    }
}
