package com.bupt.dlplatform.vo;

import com.bupt.dlplatform.model.MDataEntity;
import com.bupt.dlplatform.model.MDisplayEntity;
import lombok.*;

/**
 * Created by huhx on 2020/11/25
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MDataAndDisplayOutputVO {
    private MDataEntityOutputVO dataInfo;
    private MDisplayEntityOutputVO displayInfo;

    public MDataAndDisplayOutputVO( MDataEntity mDataEntity,MDisplayEntity mDisplayEntity ){
        setDataInfo( new MDataEntityOutputVO(mDataEntity) );
        setDisplayInfo( new MDisplayEntityOutputVO(mDisplayEntity) );
    }
}
