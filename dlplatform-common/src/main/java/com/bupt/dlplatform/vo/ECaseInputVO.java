package com.bupt.dlplatform.vo;

import lombok.*;

/**
 * Created by huhx on 2020/12/23
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ECaseInputVO {
    private String id;

    private String caseName;

    private String caseDesc;

    private String dataSetId;

    private String modelId;

    private String type;  // ssd yolo

    private String status;  // "0"  "1" —— "6"
}
