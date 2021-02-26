package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.service.EFileService;
import com.bupt.dlplatform.vo.EFileInputVO;
import com.bupt.dlplatform.vo.EFileOutputVO;
import com.bupt.dlplatform.vo.PushFileInputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by huhx on 2020/12/17
 */
// 文件、参数更新，全部算入设备更新
@Api(tags="文件接口")
@RestController
@RequestMapping("/dlplatform/EFile")
public class EFileController {
    @Autowired
    private EFileService eFileService;


    /**
     * 增加文件
     *
     * @return
     */
    @ApiOperation("增加文件")
    @PostMapping("/create")
    public ResponseVO addEFile(@RequestBody EFileInputVO eFileInputVO) {
        return eFileService.addEFile(eFileInputVO);
    }

    /**
     * 修改文件
     *
     * @return
     */
    @ApiOperation("修改文件")
    @PostMapping("/update")
    public ResponseVO updateEFile(@RequestBody EFileInputVO eFileInputVO) {
        return eFileService.updateEFile(eFileInputVO);
    }

    /**
     * 查询文件
     * Id方式
     *
     * @return
     */
    @ApiOperation("Id方式查询文件")
    @GetMapping("/getById")
    public ResponseVO<EFileOutputVO> getEFile(@RequestParam(value = "fileId") String fileId) {
        return eFileService.getEFile(fileId);
    }

    /**
     * 查询文件列表
     *
     * @return
     */
    @ApiOperation("查询文件列表")
    @GetMapping("/getList")
    public ResponseVO<List<EFileOutputVO>> getEFileList() {
        return eFileService.getEFileList();
    }

    /**
     * 删除文件
     *
     * @return
     */
    @ApiOperation("删除文件")
    @DeleteMapping("/delete")
    public ResponseVO deleteEFile(@RequestParam(value = "fileId") String fileId) {
        return eFileService.deleteEFile(fileId);
    }

    /**
     * 推送文件
     *
     * @return
     */
    @ApiOperation("推送文件")
    @PostMapping("/pushFile")
    public ResponseVO pushFileWithDevices(@RequestBody PushFileInputVO pushFileInputVO){
        return eFileService.pushFileWithDevices(pushFileInputVO);
    }
}
