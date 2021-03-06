package com.bupt.dlplatform.rpc;

/**
 * Created by huhx on 2020/12/6
 */
public interface DLPlatformService {
    public String updateVideoMessage( String deviceId, String videoMessage, String modelId, Long timestamp );

    public String updateERHeartbeat( String deviceId, String type, String status, Long timestamp, String targets );

    public String updateModelMessage( String deviceId, String type, String modelId, Long timestamp);

    public String updateFileMessage( String deviceId, String type, String fileId, Long timestamp);
}
