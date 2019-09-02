package com.bupt.dlplatform.t_model.entity;

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
@TableName("t_model")
public class TModelEntity extends SuperEntity {

    private static final long serialVersionUID = 1L;

    private Integer modelId;

    private String modelName;

    private LocalDateTime modelTime;

    private String network;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String task;

    private String modelLocation;

    /**
     * 0表示通用
     */
    private Boolean modelCommon;


}
