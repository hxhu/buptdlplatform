package com.bupt.dlplatform.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.bupt.dlplatform.model.common.ConstantProperties;
import com.bupt.dlplatform.service.EDataSetService;
import com.bupt.dlplatform.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by huhx on 2020/12/26
 */
@Api(tags="数据集接口")
@RestController
@RequestMapping("/dlplatform/EDataSet")
public class EDataSetController {
    @Autowired
    private ConstantProperties constantProperties;

    @Autowired
    private EDataSetService eDataSetService;

    /**
     * 增加数据集
     *
     * @return
     */
    @ApiOperation("增加数据集")
    @PostMapping("/create")
    public ResponseVO addEDataSet(@RequestBody EDataSetInputVO eDataSetInputVO){
        return eDataSetService.addEDataSet(eDataSetInputVO);
    }

    /**
     * 修改数据集
     *
     * @return
     */
    @ApiOperation("修改数据集")
    @PostMapping("/update")
    public ResponseVO updateEDataSet(@RequestBody EDataSetInputVO eDataSetInputVO){
        return eDataSetService.updateEDataSet(eDataSetInputVO);
    }

    /**
     * 查询数据集
     * Id方式
     *
     * @return
     */
    @ApiOperation("查询数据集")
    @GetMapping("/getById")
    public ResponseVO<EDataSetOutputVO> getEDataSet(@RequestParam(value = "dataSetId")String dataSetId){
        return eDataSetService.getEDataSet(dataSetId);
    }

    /**
     * 查询数据集列表
     * @return
     */
    @ApiOperation("查询数据集列表")
    @GetMapping("/getList")
    public ResponseVO<List<EDataSetOutputVO>> getEDataSetList(){
        return eDataSetService.getEDataSetList();
    }

    /**
     * 删除数据集
     *
     */
    @ApiOperation("删除数据集")
    @DeleteMapping("/delete")
    public ResponseVO deleteEDataSet(@RequestParam(value = "dataSetId")String dataSetId){
        return eDataSetService.deleteEDataSet(dataSetId);
    }

    /**
     * 图片上传
     */
    @ApiOperation("图片上传")
    @PostMapping("/uploadImage/{dataSetId}")
    public R uploadImage(@RequestParam(value = "avatar") MultipartFile avatar, @PathVariable(value = "dataSetId")String dataSetId){
        return eDataSetService.uploadImage(avatar, dataSetId);
    }

    /**
     * 文件上传
     */
    @ApiOperation("文件上传")
    @PostMapping("/uploadAnnotation/{dataSetId}")
    public R uploadAnnotation(@RequestParam(value = "avatar") MultipartFile avatar, @PathVariable(value = "dataSetId")String dataSetId){
        return eDataSetService.uploadAnnotation(avatar, dataSetId);
    }

    /**
     * 匹配文件名上传
     */
    @ApiOperation("匹配文件名上传")
    @PostMapping("/uploadImageSet")
    public ResponseVO uploadImageSet(@RequestBody UploadImageSetInputVO uploadImageSetInputVO){
        return eDataSetService.uploadImageSet(uploadImageSetInputVO);
    }
}
