package com.bupt.dlplatform.model;

import com.bupt.dlplatform.model.common.LogElement;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Queue;

/**
 * Created by huhx on 2020/11/22
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "m_log_entity")
public class MLogEntity {
    @Id
    private String id;
    private Integer type;                 // 1 2 3 4
    private List<LogElement> value;
    private Boolean isDeleted;
}
