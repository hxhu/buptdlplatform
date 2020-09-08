package com.bupt.dlplatform.service;

import com.bupt.dlplatform.model.TFileEntity;
import com.bupt.dlplatform.vo.FileExistsVO;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.web.multipart.MultipartFile;

public interface UploadBigFileService  {
    /**
     * 上传文件的name,md5
     * @param file
     * @return
     */
    TFileEntity uploadFile(TFileEntity file);

    /**
     * 判断文件是否存在
     * @param md5
     * @param size
     * @return
     */
    FileExistsVO fileExists(String md5, Long size);

    /**
     * 上传分片
     * @param name
     * @param index
     * @param parent
     * @param md5
     * @param size
     * @param patch
     * @return
     */
    ResponseVO filePatchExists(String name, Integer index, String parent, String md5, Long size, MultipartFile patch);

    /**
     * 合并
     * @param parent
     * @param size
     * @return
     */
    ResponseVO filePatchMerge(String parent, Long size);
}
