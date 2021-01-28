package com.bupt.dlplatform.vo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by huhx on 2020/12/4
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EDeviceInputVO {
    private String id;

//    private String deviceGroup;

    private String deviceName;

    private String deviceDesc;


    private String videoRtsp;

    private String videoMessage;

    private String currentModelId;

    private Set<String> currentFileIdSet;

    public EDeviceInputVO(String id, String deviceName){
        this.id = id;
        this.deviceName = deviceName;

        this.deviceDesc = null;
        this.videoRtsp = null;
        this.videoMessage = null;
        this.currentModelId = null;
        this.currentFileIdSet = null;
    }
}
