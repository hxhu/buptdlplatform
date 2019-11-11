package com.bupt.dlplatform.vo;

import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.Date;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "测试集相关输入")
public class TestsetInputVO extends BaseInputVO {
    private String testsetId;
    private String testsetName;
    private Date testsetTime;
    private Double testsetSize;
    private String testsetLocation;
    private Date createdTime;
    private String description;


}
