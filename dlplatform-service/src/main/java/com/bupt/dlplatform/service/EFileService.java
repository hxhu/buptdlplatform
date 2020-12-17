package com.bupt.dlplatform.service;

import com.bupt.dlplatform.vo.*;

import java.util.List;

/**
 * Created by huhx on 2020/12/16
 */
public interface EFileService {
    /**
     * 增加文件
     *
     * @return
     */
    public ResponseVO addEFile(EFileInputVO eFileInputVO);

    /**
     * 修改文件
     *
     * @return
     */
    public ResponseVO updateEFile(EFileInputVO eFileInputVO);

    /**
     * 查询文件
     * Id方式
     *
     * @return
     */
    public ResponseVO<EFileOutputVO> getEFile(String fileId);

    /**
     * 查询文件列表
     * @return
     */
    public ResponseVO<List<EFileOutputVO>> getEFileList();

    /**
     * 删除文件
     *
     * @return
     */
    public ResponseVO deleteEFile(String fileId);


    /**
     * 推送模型
     *
     * @return
     */
    public ResponseVO pushFileWithDevices(PushFileInputVO pushFileInputVO);
}
