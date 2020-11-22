package com.bupt.dlplatform.vo;

import lombok.*;

/**
 * Created by huhx on 2020/11/22
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MLogEntityInputVO {
    private Integer type;
    private String operation; // new delete modify
    private Long timestamp;
    private String message;
}
