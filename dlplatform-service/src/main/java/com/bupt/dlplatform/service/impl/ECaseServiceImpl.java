package com.bupt.dlplatform.service.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.exception.ServiceException;
import com.bupt.dlplatform.mapper.ECaseRepository;
import com.bupt.dlplatform.mapper.EDataSetRepository;
import com.bupt.dlplatform.model.ECaseEntity;
import com.bupt.dlplatform.model.EDataSetEntity;
import com.bupt.dlplatform.model.common.ConstantProperties;
import com.bupt.dlplatform.service.ECaseService;
import com.bupt.dlplatform.util.IdGenerator;
import com.bupt.dlplatform.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import java.io.*;
import java.util.*;

/**
 * Created by huhx on 2020/12/23
 */
@Service
@Slf4j
@PropertySource(value = {"classpath:application-dev.yml" })
public class ECaseServiceImpl implements ECaseService {
    @Value("${ssd-server.host}")
    private String ssdServerHost;
    @Value("${ssd-server.username}")
    private String ssdUsername;
    @Value("${ssd-server.password}")
    private String ssdPassword;

    @Autowired
    private ConstantProperties constantProperties;

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private ECaseRepository eCaseRepository;

    @Autowired
    private EDataSetRepository eDataSetRepository;


    /**
     * 增加用例
     *
     * @return
     */
    public ResponseVO addECase(ECaseInputVO eCaseInputVO) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            ECaseEntity eCaseEntity = new ECaseEntity();
            eCaseEntity.setId(idGenerator.nextId());
            eCaseEntity.setCaseName(eCaseInputVO.getCaseName() != null ? eCaseInputVO.getCaseName() : "");
            eCaseEntity.setCaseDesc(eCaseInputVO.getCaseDesc() != null ? eCaseInputVO.getCaseDesc() : "");
            eCaseEntity.setDataSetId(eCaseInputVO.getDataSetId() != null ? eCaseInputVO.getDataSetId() : "");
            eCaseEntity.setModelId(eCaseInputVO.getModelId() != null ? eCaseInputVO.getModelId() : "");

            eCaseEntity.setStatus("0");
            if (eCaseInputVO.getType() != null) {
                eCaseEntity.setType(eCaseInputVO.getType());
            } else {
                throw new ServiceException("必须提供type");
            }

            // 状态列表
//            int N = 8;
//            List<String> statusList = new ArrayList<String>(N);
//            for (int i = 0; i < N; i++) {
//                statusList.add(null);
//            }
//            eCaseEntity.setStatusList(statusList);

            Long nowTime = System.currentTimeMillis();
            eCaseEntity.setCreateTime(nowTime);
            eCaseEntity.setUpdateTime(nowTime);

            eCaseEntity.setIsDeleted(0);

            eCaseRepository.save(eCaseEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        } catch (Exception e) {
            log.error("ECase新建异常", e);
            return responseVO;
        }
    }

    /**
     * 修改用例
     *
     * @return
     */
    public ResponseVO updateECase(ECaseInputVO eCaseInputVO) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            ECaseEntity eCaseEntity;
            if (eCaseInputVO.getId() != null) {
                Optional<ECaseEntity> opt = eCaseRepository.findById(eCaseInputVO.getId());
                if (opt.isPresent() && opt.get().getIsDeleted() == 0) {
                    eCaseEntity = opt.get();
                } else {
                    throw new ServiceException("未找到该数据");
                }
            } else {
                throw new ServiceException("必须提供caseId");
            }

            if (eCaseInputVO.getCaseName() != null && !eCaseInputVO.getCaseName().equals("")) {
                eCaseEntity.setCaseName(eCaseInputVO.getCaseName());
            }
            if (eCaseInputVO.getCaseDesc() != null && !eCaseInputVO.getCaseDesc().equals("")) {
                eCaseEntity.setCaseDesc(eCaseInputVO.getCaseDesc());
            }
            if (eCaseInputVO.getStatus() != null && !eCaseInputVO.getStatus().equals("")) {
                eCaseEntity.setStatus(eCaseInputVO.getStatus());
            }
            if (eCaseInputVO.getType() != null && !eCaseInputVO.getType().equals("")) {
                eCaseEntity.setType(eCaseInputVO.getType());
            }
            if (eCaseInputVO.getModelId() != null && !eCaseInputVO.getModelId().equals("")) {
                eCaseEntity.setModelId(eCaseInputVO.getModelId());
            }
            if (eCaseInputVO.getDataSetId() != null && !eCaseInputVO.getDataSetId().equals("")) {
                eCaseEntity.setDataSetId(eCaseInputVO.getDataSetId());
            }
            eCaseEntity.setUpdateTime(System.currentTimeMillis());

            eCaseRepository.save(eCaseEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        } catch (ServiceException e) {
            log.error("ECase更新异常", e);
            return responseVO;
        } catch (Exception e) {
            log.error("ECase更新异常", e);
            return responseVO;
        }
    }

    /**
     * 查询用例
     * Id方式
     *
     * @return
     */
    public ResponseVO<ECaseOutputVO> getECase(String caseId) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            ECaseEntity eCaseEntity;
            Optional<ECaseEntity> opt = eCaseRepository.findById(caseId);
            if (opt.isPresent() && opt.get().getIsDeleted() == 0) {
                eCaseEntity = opt.get();
            } else {
                throw new ServiceException("未找到该数据");
            }

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(new ECaseOutputVO(eCaseEntity));
            return responseVO;

        } catch (ServiceException e) {
            log.error("EDevice查询异常", e);
            return responseVO;
        } catch (Exception e) {
            log.error("EDevice查询异常", e);
            return responseVO;
        }
    }

    /**
     * 删除用例
     *
     * @return
     */
    public ResponseVO deleteECase(String caseId) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            ECaseEntity eCaseEntity;
            Optional<ECaseEntity> opt = eCaseRepository.findById(caseId);
            if (opt.isPresent() && opt.get().getIsDeleted() == 0) {
                eCaseEntity = opt.get();
            } else {
                throw new ServiceException("未找到该数据");
            }

            eCaseEntity.setIsDeleted(1);

            eCaseRepository.save(eCaseEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        } catch (ServiceException e) {
            log.error("ECase删除异常", e);
            return responseVO;
        } catch (Exception e) {
            log.error("ECase删除异常", e);
            return responseVO;
        }
    }



    //训练/测试 流程所需方法

    /**
     * 1.数据集选择
     *
     * 0-未编辑状态 1-获取数据集列表 2-选定某个数据集 9-查询当前数据集
     *
     * @return
     */
    public ResponseVO<T> chooseDataSet(String caseId, String dataSetId, Integer status) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            switch (status) {
                case 0:
                    responseVO.setCode(ResponseCode.UNEDITALBE.value());
                    responseVO.setMsg(ResponseCode.UNEDITALBE.getDescription());
                    responseVO.setData("数据集选择空状态");
                    break;
                case 1:
                    List<EDataSetEntity> tmp = eDataSetRepository.findAll();
                    List<EDataSetEntity> list = new ArrayList<EDataSetEntity>();
                    for (EDataSetEntity eDataSetEntity : tmp) {
                        if (eDataSetEntity.getIsDeleted() == 0) {
                            list.add(eDataSetEntity);
                        }
                    }

                    List<EDataSetOutputVO> result = new ArrayList<EDataSetOutputVO>();

                    for (EDataSetEntity eDataSetEntity : list) {
                        result.add(new EDataSetOutputVO(eDataSetEntity));
                    }

                    responseVO.setCode(ResponseCode.OK.value());
                    responseVO.setMsg(ResponseCode.OK.getDescription());
                    responseVO.setData(result);
                    break;
                case 2:
                    if (eDataSetRepository.existsById(dataSetId)) {
                        ECaseEntity eCaseEntity;
                        Optional<ECaseEntity> opt = eCaseRepository.findById(caseId);
                        if (opt.isPresent() && opt.get().getIsDeleted() == 0) {
                            eCaseEntity = opt.get();
                        } else {
                            throw new ServiceException("未找到该数据");
                        }
                        eCaseEntity.setDataSetId(dataSetId);

                        eCaseRepository.save(eCaseEntity);

                        responseVO.setCode(ResponseCode.OK.value());
                        responseVO.setMsg(ResponseCode.OK.getDescription());
                        responseVO.setData("OK");
                    } else {
                        throw new ServiceException("该dataSet不存在");
                    }
                    break;
                case 9:
                    /**
                     * null         未选定数据集
                     * {dataSetName}  选定的数据集
                     */
                    ECaseEntity eCaseEntity;
                    Optional<ECaseEntity> optCase = eCaseRepository.findById(caseId);
                    if (optCase.isPresent() && optCase.get().getIsDeleted() == 0) {
                        eCaseEntity = optCase.get();
                    } else {
                        throw new ServiceException("未找到该ECase数据");
                    }

                    EDataSetEntity eDataSetEntity;
                    responseVO.setCode(ResponseCode.OK.value());
                    responseVO.setMsg(ResponseCode.OK.getDescription());
                    if( eCaseEntity.getDataSetId() == null || eCaseEntity.getDataSetId().equals("") ){
                        responseVO.setData(null);
                    }else{
                        Optional<EDataSetEntity> optData = eDataSetRepository.findById(eCaseEntity.getDataSetId());
                        if (optData.isPresent() && optData.get().getIsDeleted() == 0) {
                            eDataSetEntity = optData.get();
                        } else {
                            throw new ServiceException("未找到该EData数据");
                        }
                        responseVO.setData(eDataSetEntity.getDataSetName());
                    }

                    break;
            }

            return responseVO;
        } catch (ServiceException e) {
            log.error("数据集选择操作异常", e);
            return responseVO;
        } catch (Exception e) {
            log.error("数据集选择操作异常", e);
            return responseVO;
        }
    }

    /**
     * 2.环境准备
     *
     * 0-未编辑状态 1-执行环境准备 9-查询执行结果
     *
     * @return
     */
    public ResponseVO prepareEnvironment(String caseId, Integer status) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            ECaseEntity eCaseEntity;
            Optional<ECaseEntity> opt = eCaseRepository.findById(caseId);
            if (opt.isPresent() && opt.get().getIsDeleted() == 0) {
                eCaseEntity = opt.get();
            } else {
                throw new ServiceException("未找到该数据");
            }

            switch (status) {
                case 0:
                    responseVO.setCode(ResponseCode.UNEDITALBE.value());
                    responseVO.setMsg(ResponseCode.UNEDITALBE.getDescription());
                    responseVO.setData("环境准备空状态");
                    break;
                case 1:
                    // 执行环境准备脚本
                    Connection conn = getSSDConnection();
                    Session sess = getSSDSession(conn);

                    sess.execCommand("python front.py prepareEnvironment");
                    printLog(sess);

                    conn.close();
                    sess.close();

                    responseVO.setCode(ResponseCode.OK.value());
                    responseVO.setMsg(ResponseCode.OK.getDescription());
                    responseVO.setData("OK");
                    break;
                case 9:
                    /**
                     * null     文件不存在
                     * running  正在执行
                     * success  执行成功
                     * error    执行出错
                     */
                    // 查询执行结果  查看文件
                    responseVO.setCode(ResponseCode.OK.value());
                    responseVO.setMsg(ResponseCode.OK.getDescription());
                    responseVO.setData("success");
                    break;
            }

            return responseVO;
        } catch (ServiceException e) {
            log.error("环境准备操作异常", e);
            return responseVO;
        } catch (Exception e) {
            log.error("环境准备操作异常", e);
            return responseVO;
        }
    }

    /**
     * 3.开始训练
     *
     * 0-未编辑状态 9-查询训练状态
     * @return
     */
    public ResponseVO startTraining(String caseId, Integer status){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            ECaseEntity eCaseEntity;
            Optional<ECaseEntity> opt = eCaseRepository.findById(caseId);
            if (opt.isPresent() && opt.get().getIsDeleted() == 0) {
                eCaseEntity = opt.get();
            } else {
                throw new ServiceException("未找到该数据");
            }

            switch (status) {
                case 0:
                    responseVO.setCode(ResponseCode.UNEDITALBE.value());
                    responseVO.setMsg(ResponseCode.UNEDITALBE.getDescription());
                    responseVO.setData("开始训练空状态");
                    break;
                case 1:
                    // 执行开始训练脚本
                    Connection conn = getSSDConnection();
                    Session sess = getSSDSession(conn);

                    sess.execCommand("python front.py startTraining");
                    printLog(sess);

                    conn.close();
                    sess.close();

                    responseVO.setCode(ResponseCode.OK.value());
                    responseVO.setMsg(ResponseCode.OK.getDescription());
                    responseVO.setData("OK");
                    break;
                case 9:
                    /**
                     * null     文件不存在 0 
                     * running  正在训练   1
                     * success  训练成功   2
                     * error    训练出错   3
                     */
                    // 查询训练结果  查看文件
                    responseVO.setCode(ResponseCode.OK.value());
                    responseVO.setMsg(ResponseCode.OK.getDescription());
                    responseVO.setData("success");
                    break;
            }

            return responseVO;
        } catch (ServiceException e) {
            log.error("开始训练操作异常", e);
            return responseVO;
        } catch (Exception e) {
            log.error("开始训练操作异常", e);
            return responseVO;
        }
    }

    /**
     * 4.训练情况
     *
     * 0-未编辑状态 9-查询训练情况
     * @return
     */
    public ResponseVO getTrainingCondition(String caseId, Integer status){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            ECaseEntity eCaseEntity;
            Optional<ECaseEntity> opt = eCaseRepository.findById(caseId);
            if (opt.isPresent() && opt.get().getIsDeleted() == 0) {
                eCaseEntity = opt.get();
            } else {
                throw new ServiceException("未找到该数据");
            }

            switch (status) {
                case 0:
                    responseVO.setCode(ResponseCode.UNEDITALBE.value());
                    responseVO.setMsg(ResponseCode.UNEDITALBE.getDescription());
                    responseVO.setData("训练情况空状态");
                    break;
                case 9:
                    // 查询训练情况  查看文件
                    Connection conn = getSSDConnection();
                    Session sess = getSSDSession(conn);

                    sess.execCommand("python front.py getTrainingCondition");
                    TrainingConditionOutputVO trainingConditionOutputVO = getCurCondition(sess);

                    conn.close();
                    sess.close();

                    responseVO.setCode(ResponseCode.OK.value());
                    responseVO.setMsg(ResponseCode.OK.getDescription());
                    responseVO.setData(trainingConditionOutputVO);
                    break;
            }

            return responseVO;
        } catch (ServiceException e) {
            log.error("训练情况操作异常", e);
            return responseVO;
        } catch (Exception e) {
            log.error("训练情况操作异常", e);
            return responseVO;
        }
    }

    /**
     * 5.训练损失
     *
     * 0-未编辑状态 9-返回lossList
     * @return
     */
    public ResponseVO<List<HashMap<String, Object>>> getTrainingLoss(String caseId, Integer status){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            ECaseEntity eCaseEntity;
            Optional<ECaseEntity> opt = eCaseRepository.findById(caseId);
            if (opt.isPresent() && opt.get().getIsDeleted() == 0) {
                eCaseEntity = opt.get();
            } else {
                throw new ServiceException("未找到该数据");
            }

            switch (status) {
                case 0:
                    responseVO.setCode(ResponseCode.UNEDITALBE.value());
                    responseVO.setMsg(ResponseCode.UNEDITALBE.getDescription());
                    responseVO.setData("训练结果空状态");
                    break;
                case 9:
                    // 读取文件  获取实时loss list
                    Connection conn = getSSDConnection();
                    Session sess = getSSDSession(conn);

                    sess.execCommand("python front.py getTrainingLoss");
                    List<HashMap<String, Object>> result = getTrainingLoss(sess);

                    conn.close();
                    sess.close();

                    responseVO.setCode(ResponseCode.OK.value());
                    responseVO.setMsg(ResponseCode.OK.getDescription());
                    responseVO.setData(result);
                    break;
            }

            return responseVO;
        } catch (ServiceException e) {
            log.error("训练结果操作异常", e);
            return responseVO;
        } catch (Exception e) {
            log.error("训练结果操作异常", e);
            return responseVO;
        }
    }

    /**
     * 6.模型测试
     *
     * 0-未编辑状态 1-上传测试图片 9-查看是否已经上传
     * @return
     */
    public R testModel(String caseId, MultipartFile avatar, Integer status){
        Map<String,Object> map = new HashMap<>();

        try {
            ECaseEntity eCaseEntity;
            Optional<ECaseEntity> opt = eCaseRepository.findById(caseId);
            if (opt.isPresent() && opt.get().getIsDeleted() == 0) {
                eCaseEntity = opt.get();
            } else {
                throw new ServiceException("未找到该数据");
            }

            switch (status) {
                case 0:
                    map.put("desc", "模型测试空状态");
                    return R.ok(map).setCode(4040);
                case 1:
                    // 图片上传
                    String type = avatar.getContentType().split("/")[1];

                    if (avatar.isEmpty()) {
                        return R.failed("failed");
                    } else if ( !type.equals("png") && !type.equals("jpg") && !type.equals("jpeg") ){
                        return R.failed("图片类型错误 " + type);
                    } else {
                        String dirPath = constantProperties.getTestRootPath();
                        String filePath = dirPath + "case_" + caseId + "_" + avatar.getOriginalFilename();
                        System.out.println(filePath);
                        File newFile = new File(filePath);
                        try {
                            avatar.transferTo(newFile);
                        } catch (IllegalStateException | IOException e) {
                            e.printStackTrace();
                        }

                        System.out.println("上传测试图片：");
                    }

                    return R.ok(map).setCode(2000);
                case 9:
                    // 查看测试图片是否存在  执行命令
                    /**
                     * success 上传成功
                     * error   上传失败 或 未上传
                     */
                    Boolean flag = true;
                    if( flag ){
                        map.put("status", "success");
                        return R.ok(map).setCode(2000);
                    }else{
                        map.put("status", "error");
                        return R.ok(map).setCode(2000);
                    }
            }
        } catch (Exception e) {
            log.error("模型测试操作异常", e);
            return R.failed("failed");
        }

        return R.ok(map).setCode(2000);
    }

    /**
     * 7.测试结果
     *
     * 0-未编辑状态 1-执行测试 9-查询测试情况
     * @return
     */
    public ResponseVO getTestResult(String caseId, Integer status){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            ECaseEntity eCaseEntity;
            Optional<ECaseEntity> opt = eCaseRepository.findById(caseId);
            if (opt.isPresent() && opt.get().getIsDeleted() == 0) {
                eCaseEntity = opt.get();
            } else {
                throw new ServiceException("未找到该数据");
            }

            switch (status) {
                case 0:
                    responseVO.setCode(ResponseCode.UNEDITALBE.value());
                    responseVO.setMsg(ResponseCode.UNEDITALBE.getDescription());
                    responseVO.setData("训练结果空状态");
                    break;
                case 9:
                    // 查询测试情况
                    /**
                     * null    未测试
                     * {path}  执行成功
                     * error   执行失败
                     */
                    String path = "http://10.112.140.101:9090/result/";
                    String file = "Case" + caseId + ".jpg";
                    String result = path + file;

                    responseVO.setCode(ResponseCode.OK.value());
                    responseVO.setMsg(ResponseCode.OK.getDescription());
                    responseVO.setData(result);
                    break;
            }

            return responseVO;
        } catch (ServiceException e) {
            log.error("测试结果操作异常", e);
            return responseVO;
        } catch (Exception e) {
            log.error("测试结果操作异常", e);
            return responseVO;
        }
    }

    /**
     * 连接服务器相关方法
     */
    public Connection getSSDConnection(){
        Connection conn = null;
        try{
            conn = new Connection(ssdServerHost);
            conn.connect();

            boolean isAuthenticated = conn.authenticateWithPassword(ssdUsername, ssdPassword);
            if (!isAuthenticated){
                throw new IOException("Authentication failed.");
            }
        } catch ( IOException e ){
            e.printStackTrace();
        }
        return conn;
    }

    public Session getSSDSession(Connection conn){
        Session sess = null;
        try{
            sess = conn.openSession();
        }catch ( IOException e){
            e.printStackTrace();
        }

        return sess;
    }

    public void printLog(Session sess){
        InputStream stdout = new StreamGobbler(sess.getStdout());
        BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
        try{
            while (true)
            {
                String line = br.readLine();
                if (line == null)
                    break;
                System.out.println(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public TrainingConditionOutputVO getCurCondition(Session sess){
        TrainingConditionOutputVO trainingConditionOutputVO = new TrainingConditionOutputVO();
        InputStream stdout = new StreamGobbler(sess.getStdout());
        BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
        try{
            String src = null;
            while (true)
            {
                String tmp = br.readLine();
                if (tmp == null)
                    break;
                else
                    src = tmp;
                System.out.println(src);
            }

            String[] list = src.substring(1,src.length()-1).split(", ");

            Integer currentIteratorTimes = Integer.valueOf(list[0]);
            Integer maxIteratorTimes = Integer.valueOf(list[1]);
            Double currentAccuracy = Double.valueOf(list[2]) * 100;
            String status = list[3].substring(2,list[3].length()-1);

            trainingConditionOutputVO.setCurrentIteratorTimes(currentIteratorTimes);
            trainingConditionOutputVO.setMaxIteratorTimes(maxIteratorTimes);
            trainingConditionOutputVO.setCurrentAccuracy(currentAccuracy);
            trainingConditionOutputVO.setStatus(status);

            System.out.println(trainingConditionOutputVO.toString());
        }catch (IOException e){
            e.printStackTrace();
        }

        return trainingConditionOutputVO;
    }

    public List<HashMap<String, Object>> getTrainingLoss(Session sess){
        List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
        InputStream stdout = new StreamGobbler(sess.getStdout());
        BufferedReader br = new BufferedReader(new InputStreamReader(stdout));

        try{
            while (true)
            {
                String line = br.readLine();
                if (line == null)
                    break;
                String[] couple = line.split(";");
                for( int i = 0; i < couple.length; i++ ){
                    Integer iterator = Integer.valueOf( couple[i].split(",")[0] );
                    Double loss = Double.valueOf( couple[i].split(",")[1] );

                    HashMap<String, Object> hashMap = new HashMap<String, Object>();
                    hashMap.put("iterator",iterator);
                    hashMap.put("loss",loss);

                    result.add(hashMap);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return result;
    }
}
