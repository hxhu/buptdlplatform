package com.bupt.dlplatform.service;

import com.bupt.dlplatform.model.MDataEntity;
import com.bupt.dlplatform.vo.MDataEntityInputVO;
import com.bupt.dlplatform.vo.MDataEntityOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;

/**
 * Created by huhx on 2020/9/29
 */
public interface DataSourceService {
    /**
     * 新建数据
     */
    public ResponseVO createMDataEntity(MDataEntityInputVO request);

    /**
     * 更新数据
     */
    public ResponseVO updateMDataEntity(MDataEntityInputVO request);

    /**
     * 读取一条数据（Id方式）
     */
    public ResponseVO<MDataEntityOutputVO> getMDataEntityById(String id);

    /**
     * 删除数据
     */
    public ResponseVO deleteMDataEntity(String id);
}
