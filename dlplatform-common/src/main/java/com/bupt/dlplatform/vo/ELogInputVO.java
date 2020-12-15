package com.bupt.dlplatform.vo;

import lombok.*;

/**
 * Created by huhx on 2020/12/15
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ELogInputVO {
    private String id;

    private String modelId;

    private String deviceId;

    private String type; // 设备1 2 3  模型-1 -2 -3  (增，删，改)

    private String message; // 操作详情

    private Long timestamp;
}
