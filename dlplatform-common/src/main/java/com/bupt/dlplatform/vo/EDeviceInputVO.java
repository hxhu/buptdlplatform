package com.bupt.dlplatform.vo;

import lombok.*;

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
}
