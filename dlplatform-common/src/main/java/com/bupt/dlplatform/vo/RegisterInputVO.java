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
@ApiModel(value = "用户注册输入")
public class RegisterInputVO {
    @ApiModelProperty(value = "用户名")
    @NotNull(message = "用户名不能为空")
    private String userName;
    @ApiModelProperty(value = "手机号")
    @NotNull(message = "手机号不能为空")
    private String cellPhone;
    @ApiModelProperty(value = "密码")
    @NotNull(message = "密码不能为空")
    private String password;
    //@NotNull(message = "用户类型不能为空")
    //private String userType;
    //private Date createdTime;
}
