package com.bupt.dlplatform.rpc;

/**
 * Created by huhx on 2020/12/6
 */
public interface DLPlatformService {
    public String updateVideoMessage(String deviceId, String videoMessage, String modelId, Long timestamp);

    public String updateERHeartbeat( String deviceId, String type, String status, Long timestamp );
}
