package com.dianping.model;

import com.dianping.common.Const;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by hongdi.tang on 14-6-17.
 */
public class PublishFile implements Serializable {

    private static final long serialVersionUID = 2867745590025783560L;

    private String filename;
    private String projectName;
    private String publishId;
    private String host;
    private boolean flag;
    private long firstReceivedTimeMills;


    public long getFirstReceivedTimeMills() {
        return firstReceivedTimeMills;
    }

    public void setFirstReceivedTimeMills(long firstReceivedTimeMills) {
        this.firstReceivedTimeMills = firstReceivedTimeMills;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPublishId() {
        return publishId;
    }

    public void setPublishId(String publishId) {
        this.publishId = publishId;
    }

    public PublishFile(String projectName,String fileName){
        this.publishId = this.generatePublishID();
        this.projectName = projectName;
        this.filename = fileName;
    }

    private synchronized String generatePublishID(){
        Random random = new Random();
        int num = random.nextInt(1000);
        return Const.PUBLISH_ID_PREFIX + new SimpleDateFormat("yyyyMMddHHmmssS").format(new Date()) + "_" + num;
    }
}
