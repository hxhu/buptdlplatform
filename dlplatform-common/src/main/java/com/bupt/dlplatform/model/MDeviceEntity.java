package com.bupt.dlplatform.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by huhx on 2020/10/2
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "m_device_entity")
public class MDeviceEntity {
    @Id
    private String id;

    private InetAddress ip;

    private String userId;

    private String type; // sensor,embedded,server

    private String name;

    private String desc;

    private Integer status; // 0-off  1-on  2-running

    private ArrayList<String> displayIds;

    private String monitorId;

    private Long registerTime;

    // collect收集数据
    private Boolean collectFlag;

    private Long lastCollectTime;


    private Boolean isDeleted;
}
