package com.bupt.dlplatform.vo;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author LeoLv
 * @date 2019/9/17 10:29
 */

@Data
@Accessors(chain = true)
public class BaseInputVO {

    private String cellPhone;
    private String userType;
}
