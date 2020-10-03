package com.bupt.dlplatform.service;

import com.bupt.dlplatform.vo.*;

/**
 * Created by huhx on 2020/10/3
 */
public interface DataDisplayService {
    /**
     * 新建配置
     */
    public ResponseVO createMDisplayEntity(MDisplayEntityInputVO request);

    /**
     * 更新配置
     */
    public ResponseVO updateMDisplayEntity(MDisplayEntityInputVO request);

    /**
     * 读取一个配置（Id方式）
     */
    public ResponseVO<MDisplayEntityOutputVO> getMDisplayEntityById(String id);

    /**
     * 删除配置
     */
    public ResponseVO deleteMDisplayEntity(String id);
}
