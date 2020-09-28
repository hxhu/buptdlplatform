package com.bupt.dlplatform.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by huhx on 2020/9/28
 */
@Getter
@Setter
@Document(collection = "m_data_entity")
public class MDataEntity {

    @Id
    private String id;

    private String name;

    private Long create_timestamp;

    private Long last_timestamp;

    private String type;

    private String value;
}
