package com.bupt.dlplatform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;

import java.util.Date;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "模型记录输出")
public class ModelRecordOutputVO extends BaseInputVO {
    @ApiModelProperty(value = "生成时间")
    private Date createTime;
    @ApiModelProperty(value = "模型Id")
    private String modelId;
    @ApiModelProperty(value = "模型名")
    private String modelName;
    @ApiModelProperty(value = "训练名")
    private String trainName;
    @ApiModelProperty(value = "任务")
    private String task;
}
