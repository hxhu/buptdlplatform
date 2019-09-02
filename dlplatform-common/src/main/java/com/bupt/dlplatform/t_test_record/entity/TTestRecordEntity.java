package com.bupt.dlplatform.t_test_record.entity;

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
@TableName("t_test_record")
public class TTestRecordEntity extends SuperEntity {

    private static final long serialVersionUID = 1L;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer testId;

    private String testName;

    private LocalDateTime testTime;

    private Integer testsetId;

    private Integer modelId;

    private Integer resultId;

    private Integer userId;

    private String testNetwork;


}
