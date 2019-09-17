package com.bupt.dlplatform.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author LeoLv
 * @date 2019/9/17 10:29
 */

@Data
@Accessors(chain = true)
public class BaseInputVO {

    private String cellPhone;
    private String password;
    private String userType;
    private Date createdTime;

}
