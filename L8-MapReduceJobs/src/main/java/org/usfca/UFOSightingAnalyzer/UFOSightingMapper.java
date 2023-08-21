package org.usfca.UFOSightingAnalyzer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class UFOSightingMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String strLine = value.toString();
        String[] words = strLine.split(",");
        try {
            String location = words[1] + ", " + words[2] + ", " + words[3];
            context.write(new Text(location), new IntWritable(1));
        }
        catch (Exception ex){
            System.out.println("Error in mapper");
        }
    }
}
