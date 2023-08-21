package org.usfca.PwndPasswordDetector;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class PwndPasswordMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String strLine = value.toString();
        String[] words = strLine.split(":");
        try {
            context.write(new Text(words[0]), new IntWritable(Integer.parseInt(words[1])));
        }
        catch (Exception ex){
            System.out.println("Error in mapper");
        }
    }
}
