package com.bupt.dlplatform.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@Builder
@ToString
@AllArgsConstructor
@ApiModel(value = "测试集")
public class TestsetInputVO extends BaseInputVO {
    private String testsetID;
    private String testsetName;
    private Date testsetTime;
    private Double testsetSize;
    private String testsetLocation;
    private Date createdTime;
    private String description;


}
