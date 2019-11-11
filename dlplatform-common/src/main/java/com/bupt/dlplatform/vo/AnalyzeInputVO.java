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
@ApiModel(value = "评估分析输入")
public class AnalyzeInputVO extends BaseInputVO{

    @ApiModelProperty(value = "评估记录Id")
    private String analyzeRecordId;

    @ApiModelProperty(value = "评估记录名")
    private String analyzeName;

    @ApiModelProperty(value = "评估类型")
    private String type;

    @ApiModelProperty(value = "训练集Id")
    private String trainId;

    @ApiModelProperty(value = "训练名")
    private String trainName;

    @ApiModelProperty(value = "日志Id")
    private String logId;

    @ApiModelProperty(value = "下载本地目录")
    private String localPath;
}
