package com.bupt.dlplatform.model;

import lombok.*;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by huhx on 2020/10/2
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "m_display_entity")
public class MDisplayEntity<T> {
    @Id
    private String id;

    private String name;

    private String desc;

    private String type; // list,figure,picture,video,map,heartbeat

    private T configs;

    private String dataId;

    private Long createTimestamp;

    private Boolean isDeleted;
}
