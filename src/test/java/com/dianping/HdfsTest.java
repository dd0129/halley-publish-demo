package com.dianping;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by adima on 14-6-14.
 */
public class HdfsTest {

    static Configuration conf = new Configuration();
    static FileSystem hdfs;
    static{
        String path = "/usr/java/hadoop-1.0.3/conf/";
        conf.addResource(new Path(path + "core-site.xml"));
        conf.addResource(new Path(path + "hdfs-site.xml"));
        conf.addResource(new Path(path + "mapred-site.xml"));
        path = "/usr/java/hbase-0.90.3/conf/";
        conf.addResource(new Path(path + "hbase-site.xml"));
        try {
            hdfs = FileSystem.get(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void upload(){
        try{
            hdfs.copyToLocalFile(false,new Path("/user/xxx"),new Path("D:/ASEF/"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
