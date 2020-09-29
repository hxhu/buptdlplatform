package com.bupt.dlplatform.service;

import com.bupt.dlplatform.model.MDataEntity;
import com.bupt.dlplatform.vo.ResponseVO;

/**
 * Created by huhx on 2020/9/29
 */
public interface DataDisplayService {
    /**
     * 新建数据
     */
    public ResponseVO createMDataEntity();

    /**
     * 更新数据
     */
    public ResponseVO updateMDataEntity();

    /**
     * 读取一条数据（Id方式）
     */
    public ResponseVO<MDataEntity> getMDataEntityById();

    /**
     * 删除数据
     */
    public ResponseVO daleteMDataEntity();
}
