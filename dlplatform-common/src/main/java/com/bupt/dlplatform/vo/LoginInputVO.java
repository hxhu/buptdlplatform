package com.bupt.dlplatform.vo;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginInputVO {
    @NotNull(message = "手机号不能为空")
    private String cellPhone;
    @NotNull(message = "密码不能为空")
    private String password;
    @NotNull(message = "用户类型不能为空")
    private String userType;
    private Date createdTime;
}
