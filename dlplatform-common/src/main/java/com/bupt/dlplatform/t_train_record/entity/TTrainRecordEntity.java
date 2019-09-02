package com.bupt.dlplatform.t_train_record.entity;

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
@TableName("t_train_record")
public class TTrainRecordEntity extends SuperEntity {

    private static final long serialVersionUID = 1L;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer trainId;

    private String trainName;

    private LocalDateTime trainStartTime;

    private LocalDateTime trainStopTime;

    private String trainStatus;

    private Integer trainsetId;

    private Integer userId;

    private Integer configId;

    private String trainTask;

    private Integer logId;


}
