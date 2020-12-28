package com.bupt.dlplatform.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Set;

/**
 * Created by huhx on 2020/12/23
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "e_case")
public class ECaseEntity {
    @Id
    private String id;

    private String caseName;

    private String caseDesc;

    private Long createTime;

    private Long updateTime;

    private String dataSetId;

    private String modelId;

    private String type;  // ssd yolo

    private String status;  // "1" —— "6"

    private Integer isDeleted = 0;



}
