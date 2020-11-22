package com.bupt.dlplatform.service;

import com.bupt.dlplatform.vo.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huhx on 2020/11/22
 */
public interface LogService {

    /**
     * 更新日志
     */
    public ResponseVO updateMLogEntity(MLogEntityInputVO data);

    /**
     * 查询日志
     */
    public ResponseVO<ArrayList<ArrayList<MLogEntityOutputVO>>> getLogEntities();
}
