package com.dianping.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.SecurityUtil;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by hongdi.tang on 14-3-14.
 */
@Component("hiveSecurityVerify")
public class HiveSecurityVerify {
    public static Configuration conf;

    private static Logger logger = LoggerFactory.getLogger(HiveSecurityVerify.class);

    private static HiveSecurityVerify securityLogin = null;

    private HiveSecurityVerify() {

    }

    public static HiveSecurityVerify getInstance(){
        if(HiveSecurityVerify.securityLogin == null){
            return securityLogin = new HiveSecurityVerify();
        }else {
            return securityLogin;
        }
    }

    public synchronized void verify(){
        if (conf == null){
            conf = new Configuration();
        }
        UserGroupInformation.setConfiguration(conf);
        try {
            SecurityUtil.login(conf, "test.hadoop.keytab.file", "test.hadoop.principal");
        }
        catch (IOException e) {
            logger.error("security verify error",e);
        }
    }

}
