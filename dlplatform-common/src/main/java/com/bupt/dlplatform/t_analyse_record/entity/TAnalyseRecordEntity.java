package com.bupt.dlplatform.t_analyse_record.entity;

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
@TableName("t_analyse_record")
public class TAnalyseRecordEntity extends SuperEntity {

    private static final long serialVersionUID = 1L;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer analyseResultId;

    private String analyseRecordName;

    private LocalDateTime analyseRecordTime;

    private Integer userId;

    private Integer logId;

    private Integer analyseId;

    private String analyseClass;


}
