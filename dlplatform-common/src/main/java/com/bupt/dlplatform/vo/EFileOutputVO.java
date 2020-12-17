package com.bupt.dlplatform.vo;

import com.bupt.dlplatform.model.EFileEntity;
import lombok.*;

/**
 * Created by huhx on 2020/12/16
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EFileOutputVO {
    private String id;

    private String fileName;

    private String fileDesc;

    private Long createTime;

    private String fileLocation;


    private String type; // 自己命名标签

    public EFileOutputVO(EFileEntity eFileEntity){
        this.id = eFileEntity.getId();
        this.fileName = eFileEntity.getFileName();
        this.fileDesc = eFileEntity.getFileDesc();
        this.createTime = eFileEntity.getCreateTime();
        this.fileLocation = eFileEntity.getFileLocation();
        this.type = eFileEntity.getType();
    }
}
