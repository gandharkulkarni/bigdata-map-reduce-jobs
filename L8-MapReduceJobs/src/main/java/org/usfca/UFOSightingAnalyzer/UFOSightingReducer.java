package org.usfca.UFOSightingAnalyzer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.*;

public class UFOSightingReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    TreeMap<Text, Integer> locationMap = new TreeMap<Text, Integer>();
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int count = 0;
        // calculate the total count
        for(IntWritable val : values){
            count += val.get();
        }

        // Add the <key, sum> pair to the TreeMap
        locationMap.put(new Text(key), count);
    }
    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        int i=0;
        List<Map.Entry<Text, Integer>> sortedList = new ArrayList<>(locationMap.entrySet());
        sortedList.sort(new Comparator<Map.Entry<Text, Integer>>() {
            public int compare(Map.Entry<Text, Integer> o1, Map.Entry<Text, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        for (Map.Entry<Text, Integer> entry : sortedList) {
            if(i<10) {
                context.write(entry.getKey(), new IntWritable(entry.getValue()));
            } else{
                break;
            }
            i++;
        }
    }
}
