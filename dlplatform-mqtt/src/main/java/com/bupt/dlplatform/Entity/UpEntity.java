package com.bupt.dlplatform.Entity;

import lombok.*;

/**
 * Created by huhx on 2020/12/7
 */
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpEntity {
    private String deviceId;

    private String message; // videoMessage

    private String data; // modelId

    private Long timestamp;
}
