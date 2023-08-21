package org.usfca.LogAnalyzer2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class LogAnalyzerJob {
    public static void main(String[] args){
        try{
            Configuration configuration = new Configuration();
            Job analyzerJob = Job.getInstance(configuration, "Log Analyzer Job");

            /* Current class */
            analyzerJob.setJarByClass(LogAnalyzerJob.class);

            /* Mapper class */
            analyzerJob.setMapperClass(LogAnalyzerMapper.class);

            /* Reducer class*/
            analyzerJob.setReducerClass(LogAnalyzerReducer.class);

            /* Outputs from the Mapper. */
            analyzerJob.setMapOutputKeyClass(Text.class);
            analyzerJob.setMapOutputValueClass(IntWritable.class);

            /* Outputs from the Reducer */
            analyzerJob.setOutputKeyClass(Text.class);
            analyzerJob.setOutputValueClass(IntWritable.class);

            /* Reduce tasks */
            analyzerJob.setNumReduceTasks(1);

            /* Job input path in HDFS */
            FileInputFormat.addInputPath(analyzerJob, new Path(args[0]));

            /* Job output path in HDFS. NOTE: if the output path already exists
             * and you try to create it, the job will fail. You may want to
             * automate the creation of new output directories here */
            FileOutputFormat.setOutputPath(analyzerJob, new Path(args[1]));

            /* Wait (block) for the job to complete... */
            System.exit(analyzerJob.waitForCompletion(true) ? 0 : 1);

        }
        catch (Exception ex){
            System.err.println(ex.getMessage());
        }
    }
}
