package com.bupt.dlplatform.vo;

import lombok.*;

/**
 * Created by huhx on 2020/12/9
 */
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ERHeartbeatInputVO {
    private String deviceId;

    private String type; // message

    private String status; //data 0, 1, 2

    private Long timestamp;
}
