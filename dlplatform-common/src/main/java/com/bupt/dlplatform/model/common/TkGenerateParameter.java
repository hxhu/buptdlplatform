package com.bupt.dlplatform.model.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class TkGenerateParameter {
    @ApiModelProperty("Token生成时的时间戳")
    public Long iat;

    @ApiModelProperty("Token过期的时间戳")
    public Long exp;

    @ApiModelProperty("用户名")
    public String userName;

    @ApiModelProperty("手机号")
    public String cellPhone;

    @ApiModelProperty("用户角色类型")
    public String userType;

}
