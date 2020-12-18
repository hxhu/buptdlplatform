package com.bupt.dlplatform.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.Id;
import java.util.Map;
import java.util.Set;

/**
 * Created by huhx on 2020/12/4
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "e_device")
public class EDeviceEntity {
    @Id
    private String id;

//    private String deviceGroup;

    private String deviceName;

    private String deviceDesc;

    private Integer isDeleted = 0;


    private String videoRtsp;

    private String videoMessage;

    private String currentModelId;

    private Set<String> currentFileIdSet;
}
