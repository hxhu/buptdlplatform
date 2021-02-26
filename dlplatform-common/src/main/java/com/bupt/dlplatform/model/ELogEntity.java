package com.bupt.dlplatform.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

/**
 * Created by huhx on 2020/12/15
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "e_log")
public class ELogEntity {
    @Id
    private String id;

    private String modelId;

    private String deviceId;

    private String type; // 设备1 2 3   模型-1 -2 -3  配置文件 -4、-5、-6。参数组 -7、-8、-9。   // 推送模型"-2,2"   推送配置文件"-5,2"   推送参数组"-8,2"

    private String message; // 操作详情

    private Long timestamp;

    private Integer isDeleted = 0;
}
