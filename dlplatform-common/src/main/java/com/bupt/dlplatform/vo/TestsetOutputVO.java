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
@ApiModel(value = "测试集相关输出")
public class TestsetOutputVO  extends BaseInputVO{
    @ApiModelProperty(value = "测试集Id")
    private String testsetId;
    @ApiModelProperty(value = "测试集名")
    private String testsetName;
    @ApiModelProperty(value = "上传时间")
    private Date  uploadTime;
    @ApiModelProperty(value = "测试集描述")
    private String description;

}
