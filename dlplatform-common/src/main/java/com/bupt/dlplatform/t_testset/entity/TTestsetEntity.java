package com.bupt.dlplatform.t_testset.entity;

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
@TableName("t_testset")
public class TTestsetEntity extends SuperEntity {

    private static final long serialVersionUID = 1L;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer testsetId;

    private String testsetName;

    private LocalDateTime testsetTime;

    private Double testsetSize;

    private String testsetLocation;


}
