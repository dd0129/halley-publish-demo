package com.dianping.service;

import com.dianping.common.Const;
import com.dianping.common.GitEnum;
import com.dianping.hdfs.HdfsUtils;
import com.dianping.jgit.JGitUtils;
import com.dianping.model.PublishFile;
import com.dianping.zk.node.ServerNode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;

/**
 * Created by hongdi.tang on 14-6-19.
 */
@Service
public class PublishService {
    @Resource
    private ServerNode serverNode;

    public void callback(PublishFile file, boolean flag){
        if(flag){
            System.out.println("publish file successfully");
        }else{
            System.out.println("publish file fail");
        }
    }

    public void publish(String projectName,String fileName){
        try{
            String gitPath = GitEnum.getGitPath(projectName);
            if(GitEnum.getGitPath(projectName)==null){
                System.out.println(projectName + "is not correct");
                return;
            }
            JGitUtils.pull(gitPath,projectName);
            String path = Const.MASTER_GIT_DIR + File.separator + projectName + File.separator+ fileName;
            HdfsUtils.upload(path, Const.HDFS_DIR+"/"+projectName+"/"+fileName);
            PublishFile file = new PublishFile(projectName,fileName);
            serverNode.pushJobQueue(file);
        }catch(Exception e){
            e.printStackTrace();
        }finally{

        }
    }
}
