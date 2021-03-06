package cs522.lab.stripe;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static cs522.lab.common.Utils.round;

/**
 * Created by tilak on 5/11/15.
 */

public class RF_StripesReducer extends Reducer<Text, MapWritable, Text, Text> {
    @Override
    protected void reduce(Text key, Iterable<MapWritable> values, Context context) throws IOException, InterruptedException {

        Map<String, Double> cache = new HashMap<>();

        Iterator<MapWritable> iterator = values.iterator();

        int marginal = 0;
        while (iterator.hasNext()) {
            MapWritable next = iterator.next();

            for (Map.Entry<Writable, Writable> e : next.entrySet()) {
                int count = ((IntWritable) e.getValue()).get();
                String term = e.getKey().toString();

                // compute count
                cache.compute(term, (k, v) -> v == null ? count : v + count);

                marginal += count;
            }
        }

        // relative frequencies
        for (String kk : cache.keySet()) {
            double rf = cache.get(kk) / marginal;
            cache.put(kk, round(rf));
        }

        context.write(key, new Text(cache.toString()));
    }
}
