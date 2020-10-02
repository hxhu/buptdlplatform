package com.bupt.dlplatform.model;

import lombok.*;
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
@Document(collection = "m_monitor_entity")
public class MMonitorEntity {
    @Id
    private String id;

    private String name;

    private String desc;

    private String mapId;

    private Boolean isDeleted;
}
