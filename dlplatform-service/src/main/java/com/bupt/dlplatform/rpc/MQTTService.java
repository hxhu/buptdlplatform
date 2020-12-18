package com.bupt.dlplatform.rpc;

import java.util.List;

/**
 * Created by huhx on 2020/12/6
 */
public interface MQTTService {
    public String pushModel(List<String> deviceIds, String modelId, String modelLocation, String type);

    public String fileModel(List<String> deviceIds, String fileId, String fileLocation, String type);
}
