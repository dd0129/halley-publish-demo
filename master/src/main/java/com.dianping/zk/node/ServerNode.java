package com.dianping.zk.node;

import com.dianping.zk.consumer.PublishMasterConsumer;
import com.dianping.zk.core.ZKNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


/**
 * ServerNode
 */
@Service
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class ServerNode extends ZKNode {

    private static final Logger LOG = LoggerFactory.getLogger(ServerNode.class);

    @Autowired
    private PublishMasterConsumer ServerConsumer;

    @Override
    public void afterPropertiesSet() throws Exception {
        super.init();

        this.jobQueueConsumer = null;
        this.completeJobQueueConsumer = ServerConsumer;
        this.start();
    }

}
