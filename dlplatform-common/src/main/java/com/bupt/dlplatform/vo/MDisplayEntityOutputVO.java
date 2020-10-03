package com.bupt.dlplatform.vo;

import com.bupt.dlplatform.model.MDataEntity;
import com.bupt.dlplatform.model.MDisplayEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.apache.poi.ss.formula.functions.T;

/**
 * Created by huhx on 2020/10/3
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MDisplayEntityOutputVO {
    @ApiModelProperty(value ="id")
    private String id;

    @ApiModelProperty(value ="配置名")
    private String name;

    @ApiModelProperty(value ="描述")
    private String desc;

    @ApiModelProperty(value ="类型")
    private String type; // list,figure,picture,video,map,heartbeat

    @ApiModelProperty(value ="配置内容")
    private Object configs;

    @ApiModelProperty(value ="关联数据ID")
    private String dataId;

    @ApiModelProperty(value ="创建时间")
    private Long createTimestamp;

    public MDisplayEntityOutputVO(MDisplayEntity mDisplayEntity){
        setId( mDisplayEntity.getId() );
        setName( mDisplayEntity.getName() );
        setDesc( mDisplayEntity.getDesc() );
        setType( mDisplayEntity.getType() );
        setConfigs( mDisplayEntity.getConfigs() );
        setDataId( mDisplayEntity.getDataId() );
        setCreateTimestamp( mDisplayEntity.getCreateTimestamp() );
    }
}
