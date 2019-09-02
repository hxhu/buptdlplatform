package com.bupt.dlplatform.t_trainset.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bupt.dlplatform.model.base.SuperEntity;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhongling
 * @since 2019-09-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_trainset")
public class TTrainsetEntity extends SuperEntity {

    private static final long serialVersionUID = 1L;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer trainsetId;

    private String trainsetName;

    private LocalDateTime trainsetTime;

    private Double trainsetSize;

    private String trainsetLocation;

    /**
     * 0表示通用
     */
    private Boolean trainsetCommon;

    private String trainsetType;

    private String trainsetTask;


}
