package com.bupt.dlplatform.vo;

import com.bupt.dlplatform.model.EConfigEntity;
import lombok.*;

import java.util.HashMap;

/**
 * Created by huhx on 2021/2/5
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EConfigOutputVO {
    private String id;

    private HashMap<String, Object> configs;

    private String configName;

    private String configDesc;

    private Long updateTime;

    public EConfigOutputVO(EConfigEntity eConfigEntity){
        this.id = eConfigEntity.getId();
        this.configs = eConfigEntity.getConfigs();
        this.configName = eConfigEntity.getConfigName();
        this.configDesc = eConfigEntity.getConfigDesc();
        this.updateTime = eConfigEntity.getUpdateTime();
    }
}
