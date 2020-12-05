package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.service.EUserService;
import com.bupt.dlplatform.vo.EUserInputVO;
import com.bupt.dlplatform.vo.EUserOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by huhx on 2020/12/4
 */
@RestController
@RequestMapping("/dlplatform/EUser")
public class EUserController {
    @Autowired
    private EUserService eUserService;

    /**
     * 增加用户
     * @return
     */
    @PostMapping("/create")
    public ResponseVO addEUser(@RequestBody EUserInputVO request){
        return eUserService.addEUser(request);
    }

    /**
     * 修改用户
     * @return
     */
    @PostMapping("/update")
    public ResponseVO updateEUser(@RequestBody EUserInputVO request){
        return eUserService.updateEUser(request);
    }

    /**
     * 查询用户
     * Id方式
     * @return
     */
    @GetMapping("/getById")
    public ResponseVO<EUserOutputVO> getEUser(@RequestParam(value = "userId")String userId){
        return eUserService.getEUser(userId);
    }

    /**
     * 删除用户
     * @return
     */
    @DeleteMapping("/delete")
    public ResponseVO deleteEUser(@RequestParam(value = "userId")String userId){
        return eUserService.deleteEUser(userId);
    }
}
