package cs522.lab.hybrid;

import cs522.lab.common.Pair;
import cs522.lab.common.Utils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cs522.lab.common.Utils.getNeighbors;

/**
 * Created by tilak on 5/13/15.
 */
public class RF_HybridMapper extends Mapper<Object, Text, Pair, IntWritable> {
    private Map<Pair, Integer> cache;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        cache = new HashMap<>();
    }

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
/*
        // split into terms
        String[] terms = value.toString().split("\\s+");

        for (int idx = 0; idx < terms.length; idx++) {

            // get neighbors
            List<String> neighbors = Utils.getNeighbors(idx, terms);

            // keys
            String term = terms[idx];

            // update counts for each neighbor
            neighbors.forEach(n -> {
                Pair pair = new Pair(term, n);
                cache.compute(pair, (k, v) -> v == null ? 1 : v + 1);
            });
        }
*/
        // split into terms
        String[] terms = value.toString().split("\\s+");

        for (int idx = 0; idx < terms.length; idx++) {

            // get neighbors
            List<String> neighbors = getNeighbors(idx, terms);

            // keys
            String term = terms[idx];
//            Pair special = new Pair(term, "*");

            // update counts for each neighbor
            neighbors.forEach(n -> {
                Pair pair = new Pair(term, n);
                cache.compute(pair, (k, v) -> v == null ? 1 : v + 1);
//                cache.compute(special, (k, v) -> v == null ? 1 : v + 1);
            });
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        for (Pair pair : cache.keySet()) {
            context.write(pair, new IntWritable(cache.get(pair)));
        }
    }
}
