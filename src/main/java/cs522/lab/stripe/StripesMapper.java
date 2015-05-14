package cs522.lab.stripe;

import cs522.lab.common.Utils;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cs522.lab.common.Utils.toMapWritable;

/**
 * Created by tilak on 5/11/15.
 */
public class StripesMapper extends Mapper<Object, Text, Text, MapWritable> {

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {

        // split into words/events
        String[] terms = value.toString().split("\\s+");

        for (int idx = 0; idx < terms.length; idx++) {
            String term = terms[idx];

            // get neighbors
            List<String> neighbors = Utils.getNeighbors(idx, terms);

            // compute count
            Map<String, Integer> map = new HashMap<>();
            neighbors.forEach(n -> map.compute(n, (k, v) -> v == null ? 1 : v + 1));

            context.write(new Text(term), toMapWritable(map));
        }
    }
}
