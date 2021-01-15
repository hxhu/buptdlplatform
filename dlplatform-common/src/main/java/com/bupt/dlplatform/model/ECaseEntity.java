package com.bupt.dlplatform.model;

import com.bupt.dlplatform.vo.ResponseVO;
import lombok.*;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.Id;
import java.util.List;
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



    /**  null不是字符串
     * 状态列表
     * 1.数据集选择   null success
     * 2.环境准备     null running success error
     * 3.开始训练
     * 4.训练情况
     * 5.训练结果
     * 6.模型测试
     * 7.测试结果
     */
//    private List<String> statusList;

}
