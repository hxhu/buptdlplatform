package com.bupt.dlplatform.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.mapper.TFileRepository;
import com.bupt.dlplatform.model.TFileEntity;
import com.bupt.dlplatform.service.UploadBigFileService;
import com.bupt.dlplatform.util.UploadBigFileUtil;
import com.bupt.dlplatform.vo.FileExistsVO;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UploadBigFileServiceImpl implements UploadBigFileService {
    @Resource
    private TFileRepository tFileRepository;

    /**
     * 上传文件的name,md5
     * @param file
     * @return
     */
    @Override
    public TFileEntity uploadFile(TFileEntity file) {
        Long time =System.currentTimeMillis();
        Date createTime = new Date(time);
        String uploadTimeString = time.toString();
        String tempname=file.getName().substring(0,3);

        TFileEntity tFileEntity = new TFileEntity();
        tFileEntity.setFileId(uploadTimeString+tempname);
        tFileEntity.setCreateTime(createTime);
        tFileEntity.setName(file.getName());
        tFileEntity.setSize(file.getSize());
        tFileEntity.setMd5(file.getMd5());
        tFileRepository.insert(tFileEntity);
        log.info("开始上传文件，先获得");
        log.info("文件名："+file.getName());
        log.info("Md5"+file.getMd5());
        log.info(createTime.toString());
        return tFileEntity;
    }

    /**
     * 判断文件是否存在
     * @param md5
     * @param size
     * @return
     */
    @Override
    public FileExistsVO fileExists(String md5, Long size) {
        TFileEntity file = tFileRepository.selectOne(Wrappers.<TFileEntity>lambdaQuery()
                .eq(TFileEntity::getMd5,md5));
        if(file == null) {
            //还没有文件！
            log.info(file.getName()+"还没有");
            return FileExistsVO.nonExistent();
        }
        if(file.getSize().equals(size)) {
            //已完整返回主文件Id
            log.info(file.getName()+"已存在");
            return FileExistsVO.exists(file.getFileId());
        }
        //通过file_id（分片的parent），查找分片索引
        List<Integer> patchIndex = new ArrayList<>();
        List<TFileEntity> tempEntity = tFileRepository.selectList(Wrappers.<TFileEntity>lambdaQuery()
                .eq(TFileEntity::getParent,file.getFileId()));
        for(int i=0;i<tempEntity.size();i++){
            patchIndex.add(tempEntity.get(i).getPatchIndex());
        }
        log.info(file.getName()+"查找已上传的分片");
        return FileExistsVO.partExistent(file.getFileId(), patchIndex);
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
    @Override
    public ResponseVO filePatchExists(String name, Integer index, String parent, String md5, Long size, MultipartFile patch) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try{
            //找已存在的分片
            TFileEntity file = tFileRepository.selectOne(Wrappers.<TFileEntity>lambdaQuery().eq(TFileEntity::getMd5,md5)
                    .eq(TFileEntity::getParent,parent));
            if(file == null || !file.getSize().equals(size)){
                Optional.ofNullable(file).ifPresent(e->tFileRepository.deleteById(e.getFileId()));
                TFileEntity tFileEntity = new TFileEntity();
                tFileEntity.setPatchIndex(index);
                tFileEntity.setParent(parent);
                tFileEntity.setName(name);
                tFileEntity.setPath(UploadBigFileUtil.saveFile(patch,size));
                tFileEntity.setMd5(md5);
                tFileEntity.setSize(size);
                tFileRepository.insert(tFileEntity);
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                log.info("第"+index+"分片上传成功");
                return responseVO;

            }
            if(file.getSize().equals(size)){
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                log.info("文件已存在");

            }else {
                responseVO.setCode(ResponseCode.FAIL.value());
                responseVO.setMsg(ResponseCode.FAIL.getDescription());
                log.info("分片上传失败");
            }
        }catch (Exception e){
            log.error("fileUpload 异常", e);
            responseVO.setMsg("fileUpload 异常");

        }
        return responseVO;
    }

    /**
     * 合并
     * @param parent
     * @param size
     * @return
     */
    @Override
    public ResponseVO filePatchMerge(String parent, Long size) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try{
            TFileEntity file = tFileRepository.selectOne(Wrappers.<TFileEntity>lambdaQuery()
                    .eq(TFileEntity::getFileId,parent));
            List<TFileEntity> patches =tFileRepository.selectList(Wrappers.<TFileEntity>lambdaQuery().
                    eq(TFileEntity::getParent,parent).orderByAsc(TFileEntity::getPatchIndex));
            Long total = patches.stream().mapToLong(TFileEntity::getSize).sum();
            if(file == null || CollectionUtils.isEmpty(patches)|| !total.equals(size)){
                log.warn("total: {}, require size: {}, and delete file to retry !", total, size);
                responseVO.setCode(ResponseCode.FAIL.value());
                responseVO.setMsg(ResponseCode.FAIL.getDescription());
                return responseVO;
            }
            String fileType = UploadBigFileUtil.parseFileType(file.getName());
            String path =UploadBigFileUtil.mergeFile(fileType,patches.stream().
                    map(TFileEntity::getPath).collect(Collectors.toList()));

            TFileEntity updateInfo = new TFileEntity();
            //updateInfo.setParent(parent);
            updateInfo.setPath(path);
            updateInfo.setSize(total);
            tFileRepository.update(updateInfo,Wrappers.<TFileEntity>lambdaUpdate().
                    eq(TFileEntity::getFileId,file.getFileId()));
            tFileRepository.delete(Wrappers.<TFileEntity>lambdaQuery().eq(TFileEntity::getParent,parent));
            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            log.info("合并文件成功");
            return responseVO;

        }catch (Exception e){
            log.error("fileMerge 异常", e);
            responseVO.setMsg("fileMerge 异常");


        }
        return responseVO;
    }
}
