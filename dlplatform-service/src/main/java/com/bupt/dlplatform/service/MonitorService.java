package com.bupt.dlplatform.service;

import com.bupt.dlplatform.vo.*;

/**
 * Created by huhx on 2020/10/3
 */
public interface MonitorService {
    /**
     * 新建监控
     */
    public ResponseVO createMMonitorEntity(MMonitorEntityInputVO request);

    /**
     * 更新监控
     */
    public ResponseVO updateMMonitorEntity(MMonitorEntityInputVO request);

    /**
     * 读取一个监控（Id方式）
     */
    public ResponseVO<MMonitorEntityOutputVO> getMMonitorEntityById(String id);

    /**
     * 删除监控
     */
    public ResponseVO deleteMMonitorEntity(String id);
}
