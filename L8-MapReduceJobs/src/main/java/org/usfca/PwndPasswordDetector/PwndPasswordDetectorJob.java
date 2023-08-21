package org.usfca.PwndPasswordDetector;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class PwndPasswordDetectorJob {
    public static void main(String[] args){
        try{

            Configuration configuration = new Configuration();

            String password = args[2];

            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            String shaPassword = bytesToHex(hash);
            configuration.set("password", password);
            configuration.set("shaPassword", shaPassword);
            configuration.setBoolean("PasswordCompromised",false);

            System.out.println("Checking breach for password : "+ password);
            System.out.println("Checking breach for password : "+ shaPassword);

            Job analyzerJob = Job.getInstance(configuration, "Pwnd Password Detector Job");

            /* Current class */
            analyzerJob.setJarByClass(PwndPasswordDetectorJob.class);

            /* Mapper class */
            analyzerJob.setMapperClass(PwndPasswordMapper.class);

            /* Reducer class*/
            analyzerJob.setReducerClass(PwndPasswordReducer.class);

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

    private static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

}
