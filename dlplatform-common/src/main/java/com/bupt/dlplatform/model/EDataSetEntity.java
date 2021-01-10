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
@Document(collection = "e_dataset")
public class EDataSetEntity {
    @Id
    private String id;

    private String dataSetName;  //数据集路径为 /dataset/{id}

    private String dataSetDesc;

    private Long createTime;

    private Long updateTime;

    private String type; // ssd yolo

    private Integer isDeleted = 0;

}
