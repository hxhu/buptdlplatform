package com.bupt.dlplatform.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "选项")
public class OptionVO extends BaseInputVO {
    @ApiModelProperty(value = "Id")
    private String id;
    @ApiModelProperty(value = "名")
    private String name;
}
