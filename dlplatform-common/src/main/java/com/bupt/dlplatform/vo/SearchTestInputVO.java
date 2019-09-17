package com.bupt.dlplatform.vo;


import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户查询模型测试记录")
public class SearchTestInputVO {
    @NotNull(message = "手机号不能为空")
    private String cellPhone;
    private Date createdTime;
}
