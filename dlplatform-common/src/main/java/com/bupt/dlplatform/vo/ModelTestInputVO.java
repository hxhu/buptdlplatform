package com.bupt.dlplatform.vo;


import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.Date;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "模型测试相关")
public class ModelTestInputVO extends  BaseInputVO {

    private Date createdTime;
    private String testID;
    private String testName;
    private Date testTime;
    private String testsetID;
    private String testsetName;
    private String modelID;
    private String modelName;
    private String label;
    private Double threshold;
    private String configId;
    private String testNetwork;
    private String modelLabel;
    private String resultID;

}
