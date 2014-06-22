package com.dianping.zk.core;

import com.dianping.model.PublishFile;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.queue.DistributedPriorityQueue;
import org.apache.curator.framework.recipes.queue.DistributedQueue;
import org.apache.curator.framework.recipes.queue.QueueBuilder;
import org.apache.curator.framework.recipes.queue.QueueConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ZooKeeper节点
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public abstract class ZKNode implements InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(ZKNode.class);

    @Resource
    private ZKService zkService;

    private static String JOB_QUEUE_PATH;
    private static String COMPLETE_JOB_QUEUE_PATH;

	private DistributedPriorityQueue<PublishFile> jobQueue;
	private DistributedQueue<PublishFile> completeJobQueue;

    public QueueConsumer<PublishFile> jobQueueConsumer;
    public QueueConsumer<PublishFile> completeJobQueueConsumer;

    protected CuratorFramework zk;

    static {
//        JOB_QUEUE_PATH = LionUtil.getProperty("dqc.zk.job.inCompleteQueue");
//        COMPLETE_JOB_QUEUE_PATH = LionUtil.getProperty("dqc.zk.job.completeQueue");
        JOB_QUEUE_PATH = "/halley/publish_dev/inComplete";
        COMPLETE_JOB_QUEUE_PATH = "/halley/publish_dev/complete";
        System.out.println(JOB_QUEUE_PATH);
        System.out.println(COMPLETE_JOB_QUEUE_PATH);
    }

    public void init() throws Exception {
        zk = zkService.getZookeeper();
    }

    public  void pushJobQueue(PublishFile item)  {
	    try {
		    jobQueue.put(item, 100);
	    } catch (Exception ex) {
	        LOG.error("推送未完成任务队列失败", ex);
            throw new RuntimeException("推送未完成任务队列失败", ex);
	    }
    }

    /**
     * 推送到已完成任务队列
     * @param item
     */
	public  void pushCompleteQueue(PublishFile item) {
		try {
			completeJobQueue.put(item);
		} catch (Exception ex) {
		    LOG.error("推送完成任务队列失败", ex);
            throw new RuntimeException("推送完成任务队列失败", ex);
		}
	}

	/**
	 * 开始监听ZooKeeper中的队列
	 */
    public void start() {
        this.jobQueue = QueueBuilder.builder(zk, jobQueueConsumer, new QueueItemSerializer(), JOB_QUEUE_PATH).lockPath("/dqc_incomplete_lock").buildPriorityQueue(5);
        this.completeJobQueue = QueueBuilder.builder(zk, completeJobQueueConsumer, new QueueItemSerializer(), COMPLETE_JOB_QUEUE_PATH).lockPath("/dqc_complete_lock").buildQueue();

        try {
	        this.jobQueue.start();
        } catch (Exception ex) {
            LOG.error("未完成任务队列启动失败", ex);
            throw new RuntimeException("未完成任务队列启动失败", ex);
        }
        try {
            this.completeJobQueue.start();
        } catch (Exception ex) {
            LOG.error("完成任务队列启动失败", ex);
            throw new RuntimeException("完成任务队列启动失败", ex);
        }
    }

}