package cs522.lab.common;

import org.apache.hadoop.io.MapWritable;

import java.util.Map;

/**
 * Created by dinecha on 5/13/15.
 */
public class MyMapWritable extends MapWritable {
    @Override
    public String toString() {

        String str = "[";
        for (Map.Entry e : this.entrySet()) {
            str += e.getKey().toString() + " : " + e.getValue().toString();
        }
        str += "]";

        return str;
    }
}
