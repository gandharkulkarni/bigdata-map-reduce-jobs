package org.usfca.LogAnalyzer2;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.net.URI;

public class LogAnalyzerMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        String strLine = value.toString();
        strLine = strLine.replaceAll(" ", "\t");
        String[] words = strLine.split("\t");
        if (!words[3].isBlank()) {
            try {
                URI uri = new URI(words[3]);
                String host = uri.getHost();
                String domainName = host.startsWith("www.") ? host.substring(4) : host;
                context.write(new Text(domainName), new IntWritable(1));
            }
            catch (Exception ex){
                System.out.println("Error in mapper");
            }
        }
    }
}
