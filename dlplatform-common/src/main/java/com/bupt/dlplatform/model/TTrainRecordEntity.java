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
@TableName("t_train_record")
public class TTrainRecordEntity  {

    private static final long serialVersionUID = 1L;

    private Date createTime;

    private Date updateTime;

    private String trainId;

    private String trainName;

    private Date trainStartTime;

    private Date trainStopTime;

    private String trainStatus;

    private String trainsetId;

    private String userId;

    private String configId;

    private String description;

    private String logId;

    private String network;

    private String gpus;

}
