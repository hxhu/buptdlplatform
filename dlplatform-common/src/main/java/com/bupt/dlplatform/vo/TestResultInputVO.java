package com.bupt.dlplatform.vo;


import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.Date;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "检测结果相关内容")
public class TestResultInputVO extends BaseInputVO {
    private String resultName;
    private String resultLocation;
    private Date resultTime;
    private String network;
    private String modelName;
}
