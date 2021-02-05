package com.bupt.dlplatform.vo;

import lombok.*;

import java.util.HashMap;

/**
 * Created by huhx on 2021/2/5
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EConfigInputVO {
    private String id;

    private HashMap<String, Object> configs;

    private String configName;

    private String configDesc;

    private Long updateTime;
}
