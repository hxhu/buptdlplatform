package com.bupt.dlplatform.service;

import com.baomidou.mybatisplus.extension.api.R;
import com.bupt.dlplatform.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by huhx on 2020/12/23
 */
public interface EDataSetService {
    /**
     * 增加数据集
     *
     * @return
     */
    public ResponseVO addEDataSet(EDataSetInputVO eDataSetInputVO);

    /**
     * 修改数据集
     *
     * @return
     */
    public ResponseVO updateEDataSet(EDataSetInputVO eDataSetInputVO);

    /**
     * 查询数据集
     * Id方式
     *
     * @return
     */
    public ResponseVO<EDataSetOutputVO> getEDataSet(String dataSetId);

    /**
     * 删除数据集
     *
     * @return
     */
    public ResponseVO deleteEDataSet(String dataSetId);

    /**
     * 上传图片
     * @param avatar
     * @return
     */
    public R uploadImage(MultipartFile avatar, String dataSetId);

    /**
     * 上传标注文件
     * @param avatar
     * @return
     */
    public R uploadAnnotation(MultipartFile avatar, String dataSetId);

    /**
     * 匹配文件名上传
     */
    public ResponseVO uploadImageSet(UploadImageSetInputVO uploadImageSetInputVO);
}
