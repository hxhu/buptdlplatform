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
@ApiModel(value = "评估分析输出")
public class AnalyzeOutputVO extends BaseInputVO {
    @ApiModelProperty(value = "评估记录Id")
    private String analyzeRecordId;
    @ApiModelProperty(value = "评估记录名")
    private String analyzeRecordName;
    @ApiModelProperty(value = "评估记录时间")
    private Date analyzeRecordTime;
    @ApiModelProperty(value = "评估结果id")
    private String analyzeId;
    @ApiModelProperty(value = "训练名")
    private String trainName;
    @ApiModelProperty(value = "训练Id")
    private String trainId;
    @ApiModelProperty(value = "评估类型")
    private String type;
    @ApiModelProperty(value = "使用网络")
    private String network;
    @ApiModelProperty(value = "日志Id")
    private String logId;



}
