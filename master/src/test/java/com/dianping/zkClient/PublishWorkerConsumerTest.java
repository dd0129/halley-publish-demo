package com.dianping.zkClient;

import com.dianping.model.PublishFile;
import com.dianping.zk.consumer.PublishMasterConsumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by hongdi.tang on 14-6-18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-applicationcontext.xml")
public class PublishWorkerConsumerTest {
    @Resource(name="publishMasterConsumer")
    private PublishMasterConsumer consumer;

    @Test
    public void testConsumeMessage() throws Exception {
        consumer.consumeMessage(new PublishFile("d:/test","test.dol"));
    }

    @Test
    public void testGetHostIP() throws Exception {
    }
}
