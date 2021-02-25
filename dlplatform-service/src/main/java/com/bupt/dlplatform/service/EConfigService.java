package com.bupt.dlplatform.service;

import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.model.EDataSetEntity;
import com.bupt.dlplatform.vo.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huhx on 2021/2/5
 */
public interface EConfigService {
    /**
     * 增加配置
     *
     * @return
     */
    public ResponseVO addEConfig(EConfigInputVO eConfigInputVO);

    /**
     * 修改配置
     *
     * @return
     */
    public ResponseVO updateEConfig(EConfigInputVO eConfigInputVO);

    /**
     * 查询配置列表
     * @return
     */
    public ResponseVO<List<EConfigOutputVO>> getEConfigList();

    /**
     * 查询配置
     * Id方式
     *
     * @return
     */
    public ResponseVO<EConfigOutputVO> getEConfig(String configId);

    /**
     * 删除配置
     *
     * @return
     */
    public ResponseVO deleteEConfig(String configId);

    /**
     * 推送配置到设备
     *
     * @return
     */
    public ResponseVO pushDownConfigs(EConfigDownInputVO eConfigDownInputVO);
}
