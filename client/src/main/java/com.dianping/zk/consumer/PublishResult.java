package com.dianping.zk.consumer;

import com.dianping.model.PublishFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hongdi.tang on 14-6-19.
 */
public class PublishResult {
    enum ResultEnum{
        alreadyFailure(-2),failure(-1),success(1),wait(0);

        public int getCode() {
            return code;
        }

        private int code;
        private ResultEnum(int code){
            this.code = code;
        }
    }

    private static List<String> WORKER_NODE_IPS = new ArrayList<String>();

    private static Map<String,List<PublishFile>> resultMap =
            new ConcurrentHashMap<String, List<PublishFile>>();

    private static Map<String,Boolean> result = new ConcurrentHashMap<String, Boolean>();


    public static int isPublished(PublishFile file){
        int code = putResult(file);
        if(code == ResultEnum.success.getCode()){
            if(containsIP(file) ){
                if(putResultMap(file)){
                    return ResultEnum.success.getCode();
                }else{
                    return ResultEnum.wait.getCode();
                }
            }else{
                file.setFlag(false);
                putResult(file);
                return ResultEnum.failure.getCode();
            }
        }else{
            return code;
        }
    }

    private static boolean containsIP(PublishFile file){
        for(String ip :WORKER_NODE_IPS){
            if(file.getHost().equals(ip)){
                return true;
            }
        }
        return false;
    }

    private static boolean putResultMap(PublishFile file){
        List workerList = null;
        if(resultMap.containsKey(file.getPublishId())){
            workerList = resultMap.get(file.getPublishId());
            workerList.add(file);
        }else{
            workerList = new ArrayList<PublishFile>();
            workerList.add(file);
            resultMap.put(file.getPublishId(),workerList);
        }
        return WORKER_NODE_IPS.size() == workerList.size();
    }

    private static int putResult(PublishFile file){
        if(result.containsKey(file.getPublishId())){
            boolean flag = result.get(file.getPublishId());
            if(!flag){
                return ResultEnum.alreadyFailure.getCode();
            }
            result.put(file.getPublishId(),file.isFlag());
        }else{
            result.put(file.getPublishId(), file.isFlag());
        }
        return file.isFlag() ? ResultEnum.success.getCode() : ResultEnum.failure.getCode();
    }
}
