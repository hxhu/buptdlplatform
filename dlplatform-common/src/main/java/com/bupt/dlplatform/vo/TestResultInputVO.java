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
@ApiModel(value = "检测结果相关内容")
public class TestResultInputVO extends BaseInputVO {
    @ApiModelProperty(value = "测试Id")
    private String testId;

    @ApiModelProperty(value = "测试结果名")
    private String resultName;
    @ApiModelProperty(value = "测试时间")
    private Date resultTime;
    @ApiModelProperty(value = "测试网络")
    private String network;
    @ApiModelProperty(value = "模型名")
    private String modelName;
    @ApiModelProperty(value = "结果Id")
    private String resultId;
    @ApiModelProperty(value = "图片列表")
    private String[] picList;
    @ApiModelProperty(value = "一张图片名")
    private String  picOne;

}
