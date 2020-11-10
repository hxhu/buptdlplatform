package com.bupt.dlplatform.service;

import com.bupt.dlplatform.vo.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;

/**
 * Created by huhx on 2020/10/3
 */
public interface DataDisplayService {
    /**
     * 新建配置
     */
    public ResponseVO createMDisplayEntity(MDisplayEntityInputVO request);

    /*
     * 新建数据及配置
     */
    public ResponseVO createDataAndDisplay(MDisplayEntityInputVO request);

    /**
     * 更新配置
     */
    public ResponseVO updateMDisplayEntity(MDisplayEntityInputVO request);

    /**
     * 读取一个配置（Id方式）
     */
    public ResponseVO<MDisplayEntityOutputVO> getMDisplayEntityById(String id);

    /**
     * 跟据配置ID获得数据信息
     */
//    public ResponseVO<MDataEntityOutputVO> getMDataEntityByConfigId(String configId);

    /**
     * 删除配置
     */
    public ResponseVO deleteMDisplayEntity(String id);
}
