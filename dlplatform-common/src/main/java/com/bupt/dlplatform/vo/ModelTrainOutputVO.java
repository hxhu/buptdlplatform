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
@ApiModel(value = "模型训练输出")
public class ModelTrainOutputVO extends BaseInputVO {

    @ApiModelProperty(value = "训练Id")
    private String trainId;
    @ApiModelProperty(value = "训练记录名")
    private String trainName;
    @ApiModelProperty(value = "训练开始时间")
    private Date traintStartTime;
    @ApiModelProperty(value = "训练结束时间")
    private Date traintStopTime;
    @ApiModelProperty(value = "训练网络")
    private String network;
    @ApiModelProperty(value = "训练集Id")
    private String trainsetId;
    @ApiModelProperty(value = "训练集名")
    private String trainsetName;
    @ApiModelProperty(value = "配置文件Id")
    private  String configId;
    @ApiModelProperty(value = "日志文件Id")
    private  String logId;
    @ApiModelProperty(value = "训练状态")
    private String trainStatus;

    @ApiModelProperty(value = "默认配置文件路径")
    private String defaultParam;

}
