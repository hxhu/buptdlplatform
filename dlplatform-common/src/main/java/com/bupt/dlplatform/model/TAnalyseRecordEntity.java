package com.bupt.dlplatform.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

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
public class TAnalyseRecordEntity {

    private static final long serialVersionUID = 1L;

    private Date createTime;

    private Date updateTime;

    private Integer analyseResultId;

    private String analyseRecordName;

    private Date analyseRecordTime;

    private Integer userId;

    private Integer logId;

    private Integer analyseId;

    private String analyseClass;


}
