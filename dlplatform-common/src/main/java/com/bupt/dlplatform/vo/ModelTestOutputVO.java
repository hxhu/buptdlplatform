package com.bupt.dlplatform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "模型测试输出")
public class ModelTestOutputVO extends BaseInputVO {
    @ApiModelProperty(value = "检测Id")
    private String testId;
    @ApiModelProperty(value = "检测记录名")
    private String testName;
    @ApiModelProperty(value = "检测时间")
    private Date testTime;
    @ApiModelProperty(value = "检测网络")
    private String network;
    @ApiModelProperty(value = "检测模型Id")
    private String modelId;
    @ApiModelProperty(value = "检测模型名")
    private String modelName;
    @ApiModelProperty(value = "检测集Id")
    private String testsetId;
    @ApiModelProperty(value = "检测集名")
    private String testsetName;
    @ApiModelProperty(value = "阈值")
    private  Double threshold;
    @ApiModelProperty(value = "检测结果Id")
    private String resultId;
}
