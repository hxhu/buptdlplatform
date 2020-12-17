package com.bupt.dlplatform.vo;

import lombok.*;

/**
 * Created by huhx on 2020/12/16
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EFileInputVO {
    private String id;

    private String fileName;

    private String fileDesc;

    private Long createTime;

    private String fileLocation;


    private String type; // 自己命名标签
}
