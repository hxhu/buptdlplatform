package com.bupt.dlplatform.service.impl;

import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.exception.ServiceException;
import com.bupt.dlplatform.mapper.EUserRepository;
import com.bupt.dlplatform.model.EModelEntity;
import com.bupt.dlplatform.model.EUserEntity;
import com.bupt.dlplatform.service.EUserService;
import com.bupt.dlplatform.util.IdGenerator;
import com.bupt.dlplatform.vo.EModelOutputVO;
import com.bupt.dlplatform.vo.EUserInputVO;
import com.bupt.dlplatform.vo.EUserOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by huhx on 2020/12/4
 */
@Service
@Slf4j
public class EUserServiceImpl implements EUserService {
    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private EUserRepository eUserRepository;

    /**
     * 增加用户
     * @return
     */
    public ResponseVO addEUser(EUserInputVO eUserInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EUserEntity eUserEntity = new EUserEntity();
            eUserEntity.setId(idGenerator.nextId());
            if( eUserInputVO.getUserName() != null ){
                eUserEntity.setUserName( eUserInputVO.getUserName() );
            }else{
                throw new ServiceException("必须提供userName");
            }
            if( eUserInputVO.getPassword() != null ){
                eUserEntity.setPassword( eUserInputVO.getPassword() );
            }else{
                throw new ServiceException("必须提供password");
            }
            eUserEntity.setPhoneNumber( eUserInputVO.getPhoneNumber() != null ? eUserInputVO.getPhoneNumber() : "" );
            eUserEntity.setUserType( eUserInputVO.getUserType() != null ? eUserInputVO.getUserType() : "user" );
            eUserEntity.setIsDeleted(0);

            eUserRepository.save(eUserEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        }catch ( ServiceException e ){
            log.error("EUser新建异常", e);
            return responseVO;
        }catch (Exception e){
            log.error("EUser新建异常", e);
            return responseVO;
        }
    }

    /**
     * 修改用户
     * @return
     */
    public ResponseVO updateEUser(EUserInputVO eUserInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EUserEntity eUserEntity;
            if( eUserInputVO.getId() != null ){
                Optional<EUserEntity> opt = eUserRepository.findById(eUserInputVO.getId());
                if( opt.isPresent() && opt.get().getIsDeleted() == 0 ){
                    eUserEntity = opt.get();
                }else{
                    throw new ServiceException("未找到该数据");
                }
            }else{
                throw new ServiceException("必须提供userId");
            }


            if( eUserEntity.getUserName() != null ){
                eUserEntity.setUserName( eUserInputVO.getUserName() );
            }
            if( eUserEntity.getPassword() != null ){
                eUserEntity.setPassword( eUserInputVO.getPassword() );
            }
            if( eUserEntity.getPhoneNumber() != null ){
                eUserEntity.setPhoneNumber( eUserInputVO.getPhoneNumber() );
            }
            if( eUserEntity.getUserType() != null ){
                eUserEntity.setUserType( eUserInputVO.getUserType() );
            }

            eUserRepository.save(eUserEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        }catch ( ServiceException e ){
            log.error("EUser更新异常", e);
            return responseVO;
        }catch (Exception e){
            log.error("EUser更新异常", e);
            return responseVO;
        }
    }

    /**
     * 查询用户
     * Id方式
     * @return
     */
    public ResponseVO<EUserOutputVO> getEUser(String userId){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EUserEntity eUserEntity;
            Optional<EUserEntity> opt = eUserRepository.findById(userId);
            if( opt.isPresent() && opt.get().getIsDeleted() == 0 ){
                eUserEntity = opt.get();
            }else{
                throw new ServiceException("未找到该数据");
            }

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData( new EUserOutputVO(eUserEntity) );
            return responseVO;

        }catch ( ServiceException e ){
            log.error("EModel查询异常", e);
            return responseVO;
        }catch (Exception e){
            log.error("EModel查询异常", e);
            return responseVO;
        }
    }

    /**
     * 删除用户
     * @return
     */
    public ResponseVO deleteEUser(String userId){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EUserEntity eUserEntity;
            Optional<EUserEntity> opt = eUserRepository.findById(userId);
            if( opt.isPresent() && opt.get().getIsDeleted() == 0 ){
                eUserEntity = opt.get();
            }else{
                throw new ServiceException("未找到该数据");
            }

            eUserEntity.setIsDeleted(1);

            eUserRepository.save(eUserEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        }catch ( ServiceException e ){
            log.error("EUser删除异常", e);
            return responseVO;
        }catch (Exception e){
            log.error("EUser删除异常", e);
            return responseVO;
        }
    }
}
