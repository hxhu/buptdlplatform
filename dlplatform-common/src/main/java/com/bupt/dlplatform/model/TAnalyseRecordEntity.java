package com.bupt.dlplatform.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import lombok.Data;
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
@Accessors(chain = true)
@TableName("t_analyse_record")
public class TAnalyseRecordEntity {

    private static final long serialVersionUID = 1L;

    private Date createTime;

    private Date updateTime;

    private String analyseRecordId;

    private String analyseRecordName;

    private Date analyseRecordTime;

    private String userId;

    private String logId;

    private String analyseId;

    private String analyseClass;

    private String trainId;


}
