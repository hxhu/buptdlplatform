package com.bupt.dlplatform.Hystrix;

import com.bupt.dlplatform.consumer.UploadBigFileConsumer;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.model.TFileEntity;
import com.bupt.dlplatform.vo.FileExistsVO;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class UploadBigFileApiHystrix implements UploadBigFileConsumer {
    private ResponseVO rtn = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

//    @Override
//    public FileExistsVO fileExists(String md5, Long size) {
//        return null;
//    }
//
//    @Override
//    public TFileEntity uploadFile(TFileEntity file) {
//        return null;
//    }
//
//    @Override
//    public ResponseVO filePatchExists(String name, Integer index, String parent, String md5, Long size) {
//        return rtn;
//    }
//
//    @Override
//    public ResponseVO filePatchMerge(String parent, Long size) {
//        return rtn;
//    }
}
