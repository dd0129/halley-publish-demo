package com.dianping.zk.consumer;

import com.dianping.model.PublishFile;
import com.dianping.service.PublishService;
import com.dianping.zk.node.WorkerNode;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.queue.QueueConsumer;
import org.apache.curator.framework.state.ConnectionState;
import org.springframework.stereotype.Service;

@Service
public class PublishMasterConsumer implements QueueConsumer<PublishFile> {

    //@Resource(name="callbackService")
    private PublishService service;

    private WorkerNode workerNode;

    public void setWorkerNode(WorkerNode workerNode) {
        this.workerNode = workerNode;
    }

    @Override
	public void consumeMessage(PublishFile publishFile){
        publishFile.setFirstReceivedTimeMills(System.currentTimeMillis());
        int retCode = PublishResult.isPublished(publishFile);

        if(retCode == PublishResult.ResultEnum.success.getCode()){
            //回调成功函数
           // service.callback(publishFile, true);
        }else if(retCode == PublishResult.ResultEnum.failure.getCode()){
            //service.callback(publishFile, false);
        }else{

        }
	}


    @Override
    public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {

    }

}