package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.service.EFileService;
import com.bupt.dlplatform.vo.EFileInputVO;
import com.bupt.dlplatform.vo.EFileOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by huhx on 2020/12/17
 */
// 文件、参数更新，全部算入设备更新
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
    @PostMapping("/create")
    public ResponseVO addEFile(@RequestBody EFileInputVO eFileInputVO) {
        return eFileService.addEFile(eFileInputVO);
    }

    /**
     * 修改文件
     *
     * @return
     */
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
    @GetMapping("/getById")
    public ResponseVO<EFileOutputVO> getEFile(@RequestParam(value = "fileId") String fileId) {
        return eFileService.getEFile(fileId);
    }

    /**
     * 查询文件列表
     *
     * @return
     */
    @GetMapping("/getList")
    public ResponseVO<List<EFileOutputVO>> getEFileList() {
        return eFileService.getEFileList();
    }

    /**
     * 删除文件
     *
     * @return
     */
    @DeleteMapping("/delete")
    public ResponseVO deleteEFile(@RequestParam(value = "fileId") String fileId) {
        return eFileService.deleteEFile(fileId);
    }
}
