package com.bupt.dlplatform.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.HashMap;

/**
 * Created by huhx on 2021/2/5
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "e_config")
public class EConfigEntity {
    @Id
    private String id;

    private HashMap<String, Object> configs;

    private String configName;

    private String configDesc;

    private Long updateTime;

    private Integer isDeleted = 0;
}
