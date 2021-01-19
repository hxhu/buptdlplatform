package com.bupt.dlplatform.vo;

import lombok.*;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by huhx on 2021/1/19
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChooseDataSetInputVO {
    private String caseId;

    private String dataSetId;

    private Integer status;
}
