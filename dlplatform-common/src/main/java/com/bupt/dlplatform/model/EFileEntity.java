package com.bupt.dlplatform.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

/**
 * Created by huhx on 2020/12/16
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "e_file")
public class EFileEntity {
    @Id
    private String id;

    private String fileName;

    private String fileDesc;

    private Long createTime;

    private String fileLocation;

    private Integer isDeleted = 0;


    private String type = ""; // 自己命名标签
}
