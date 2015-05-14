package cs522.lab.hybrid;

import cs522.lab.common.Pair;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static cs522.lab.common.Utils.round;

/**
 * Created by tilak on 5/13/15.
 */
public class RF_HybridReducer extends Reducer<Pair, IntWritable, Text, Text> {
    private Map<String, Double> cache;
    private String current;
    private double marginal;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        cache = new HashMap<>();
        current = null;
        marginal = 0.0;
    }

    @Override
    protected void reduce(Pair key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        if (current == null) {
            current = key.getFirst();
        }

        // when new term is encountered
        if (!current.equals(key.getFirst())) {
            for (String k : cache.keySet()) {
                double rf = round(cache.get(k) / marginal);
                cache.put(k, rf);
            }

            context.write(new Text(current), new Text(cache.toString()));

            // reset
            marginal = 0.0;
            cache = new HashMap<>();
            current = key.getFirst();
        }

        Iterator<IntWritable> itr = values.iterator();
        double sum = 0.0;
        while (itr.hasNext()) {
            int c = itr.next().get();

            sum += c;
            marginal += c;
        }
        cache.put(key.getSecond(), sum);

    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        for (String k : cache.keySet()) {
            double rf = round(cache.get(k) / marginal);
            cache.put(k, rf);
        }
        context.write(new Text(current), new Text(cache.toString()));
    }

}
