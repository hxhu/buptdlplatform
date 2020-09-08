package com.bupt.dlplatform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户登录输入")
public class LoginInputVO {
    @ApiModelProperty(value ="手机号")
    @NotNull(message = "手机号不能为空")
    private String cellPhone;

    @ApiModelProperty(value = "密码")
    @NotNull(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "用户类型")
    @NotNull(message = "用户类型不能为空")
    private String userType;

}
