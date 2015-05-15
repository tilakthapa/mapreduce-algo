package cs522.lab.common;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tilak on 5/12/15.
 */
public class Utils {
    public static MapWritable toMapWritable(Map<String, Integer> map) {
        MapWritable result = new MapWritable();
        if (map != null) {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                result.put(new Text(entry.getKey()), new IntWritable(entry.getValue()));
            }
        }
        return result;
    }

    public static MyMapWritable toMapWritable1(Map<String, Double> map) {
        MyMapWritable result = new MyMapWritable();
        if (map != null) {
            for (Map.Entry<String, Double> entry : map.entrySet()) {
                result.put(new Text(entry.getKey()), new DoubleWritable(entry.getValue()));
            }
        }
        return result;
    }

    public static double round(double value) {
        return Math.round(value * 100000.0) / 100000.0;
    }

    public static List<String> getNeighbors(int idx, String[] terms) {
        List<String> list = new ArrayList<>();
        String term = terms[idx];
        for (int i = idx + 1; i < terms.length && !term.equals(terms[i]); i++) {
            list.add(terms[i]);
        }
        return list;
    }

//    public static String toString(MapWritable mw) {
//        String str = "";
//        for (Map.Entry e : mw.entrySet()) {
//            str += "[" + e.getKey().toString() + " : " + e.getValue().toString() + "]";
//        }
//        return str;
//    }
}
