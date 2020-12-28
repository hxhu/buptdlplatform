package com.bupt.dlplatform.vo;

import lombok.*;

import javax.persistence.Id;

/**
 * Created by huhx on 2020/12/23
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EDataSetInputVO {
    private String id;

    private String dataSetName;

    private String dataDesc;

    private String type; // ssd yolo
}
