package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.model.TFileEntity;
import com.bupt.dlplatform.service.UploadBigFileService;
import com.bupt.dlplatform.vo.FileExistsVO;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadBigFileController {
    @Autowired
    UploadBigFileService uploadBigFileService;
    /**
     * 判断文件是否存在【md5,size】
     * @param md5
     * @param size
     * @return
     */
    @GetMapping("/dlplatform/file/exists")
    public FileExistsVO fileExists(String md5, Long size) {

        return uploadBigFileService.fileExists(md5, size);
    }

    /**
     * 上传文件的name,md5
     * @param file
     * @return
     */
    @PostMapping("/dlplatform/file/new")
    public TFileEntity uploadFile(@RequestBody TFileEntity file) {
        return uploadBigFileService.uploadFile(file);
    }



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
    @PostMapping("/dlplatform/file/patch/upload")
    public ResponseVO filePatchExists(String name, Integer index, String parent, String md5, Long size, MultipartFile patch)  {
        return uploadBigFileService.filePatchExists(name, index, parent, md5, size, patch);
    }

    /**
     * 合并
     * @param parent
     * @param size
     * @return
     */
    @Transactional
    @PostMapping("/dlplatform/file/patch/merge")
    public ResponseVO filePatchMerge(String parent, Long size){
        return uploadBigFileService.filePatchMerge(parent, size);
    }

}
