package com.bupt.dlplatform.vo;

import lombok.*;

import java.util.Date;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TestResultParamVO extends BaseInputVO {

    private Date createTime;

    private String resultId;

    private String resultName;

    private String resultLocation;

    private Date resultTime;


}
