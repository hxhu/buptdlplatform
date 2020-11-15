package com.bupt.dlplatform.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * Created by huhx on 2020/11/15
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class IdsVO {
    @ApiModelProperty(value ="ids")
    private String[] ids;
}
