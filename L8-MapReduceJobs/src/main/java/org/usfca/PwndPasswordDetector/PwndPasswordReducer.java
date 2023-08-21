package org.usfca.PwndPasswordDetector;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class PwndPasswordReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int count = 0;
        // calculate the total count
        for(IntWritable val : values){
            count += val.get();
        }

        String shaPassword = context.getConfiguration().get("shaPassword");
        if(shaPassword.equalsIgnoreCase(key.toString())){
            context.getConfiguration().setBoolean("PasswordCompromised", true);
            String password = context.getConfiguration().get("password");
            context.write(new Text("Password: "+password+" is compromised. Breach Found: "), new IntWritable(count));

        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
       if(!context.getConfiguration().getBoolean("PasswordCompromised",false)){
           String password = context.getConfiguration().get("password");
           context.write(new Text("Password: "+password+" is safe. No breach found. Breach Found: "), new IntWritable(0));
       }
    }
}
