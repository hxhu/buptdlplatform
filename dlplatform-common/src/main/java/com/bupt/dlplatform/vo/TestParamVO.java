package com.bupt.dlplatform.vo;

import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TestParamVO extends BaseInputVO {
    /**
     * testId,
     * resultId,
     * userId
     * testnetwork,
     * testsetpath,
     * testsetname,
     * testmodelpath,
     * testmodelname,
     * testconfigpath,
     * testconfigname
     * testThreshold,
     * testlabel,
     */
    private String testId;
    private String resultId;
    private String testNetwork;
    private String testsetPath;
    private String testsetName;
    private String testmodelPath;
    private String testmodelName;
    private String configPath;
    private String configName;
    private String testThreshold;
    private String testlabel;
}
