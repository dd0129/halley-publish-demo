package com.dianping.model;


import org.junit.Assert;
import org.junit.Test;

/**
 * Created by hongdi.tang on 14-6-18.
 */
public class PublishFileTest  {
    @Test
    public void testCreateInstance(){
        PublishFile file = new PublishFile("d:/test","test.dol");
        System.out.println(file);
        Assert.assertNotNull(file.getPublishId());
    }
}
