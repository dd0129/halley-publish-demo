package com.dianping.hdfs;

import com.dianping.utils.HiveSecurityVerify;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

/**
 * Created by adima on 14-6-14.
 */
public class HdfsUtils {
    public static FileSystem hdfs;
    static{
        HiveSecurityVerify.getInstance().verify();
        try {
            hdfs = FileSystem.get(HiveSecurityVerify.conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean download(String hdfsFile,String localPath){
        try{
            hdfs.copyToLocalFile(false,new Path(hdfsFile),new Path(localPath));
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean upload(String file,String hdfsFile){
        try{
            hdfs.copyFromLocalFile(false, true, new Path(file), new Path(hdfsFile));
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args){
        System.out.println(HdfsUtils.class.getResource("/").getPath());
        try {
            HdfsUtils.upload("d:/555.xml","/tmp/55.xml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
