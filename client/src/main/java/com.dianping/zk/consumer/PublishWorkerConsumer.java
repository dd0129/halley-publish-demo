package com.dianping.zk.consumer;

import com.dianping.model.PublishFile;
import com.dianping.zk.node.ServerNode;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.queue.QueueConsumer;
import org.apache.curator.framework.state.ConnectionState;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.UnknownHostException;

@Service("publishWorkerConsumter")
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class PublishWorkerConsumer implements QueueConsumer<PublishFile> {

    @Resource(name="serverNode")
    private ServerNode serverNode;

    @Override
	public void consumeMessage(PublishFile publishFile){
        String ip = null;
        try{
            ip = this.getHostIP();
            //HdfsUtils.download(publishFile.getFilename(), Const.localPublishPath);
            publishFile.setHost(ip);
            publishFile.setFlag(true);
        }catch(Exception e){
            publishFile.setFlag(false);
        }finally{
            this.serverNode.pushCompleteQueue(publishFile);
        }
	}


    @Override
    public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {

    }

    public String getHostIP() throws UnknownHostException{
        java.net.InetAddress address =java.net.InetAddress.getByName("localhost")   ;
        return address.getLocalHost().getHostAddress();
    }

}