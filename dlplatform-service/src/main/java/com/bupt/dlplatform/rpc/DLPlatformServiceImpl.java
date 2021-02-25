package com.bupt.dlplatform.rpc;

import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.exception.ServiceException;
import com.bupt.dlplatform.mapper.EDeviceRepository;
import com.bupt.dlplatform.mapper.EFileRepository;
import com.bupt.dlplatform.model.EDeviceEntity;
import com.bupt.dlplatform.model.EFileEntity;
import com.bupt.dlplatform.model.EModelEntity;
import com.bupt.dlplatform.vo.EDeviceInputVO;
import com.bupt.dlplatform.vo.EModelInputVO;
import com.bupt.dlplatform.vo.ERHeartbeatInputVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Created by huhx on 2020/12/6
 */
@Service
@Slf4j
public class DLPlatformServiceImpl implements DLPlatformService {

    @Override
    public String updateVideoMessage( String deviceId, String videoMessage, String modelId, Long timestamp ){
        try{
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            String url = "http://localhost:18800/dlplatform/EDevice/update";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            EDeviceInputVO eDeviceInputVO = new EDeviceInputVO();
            eDeviceInputVO.setId(deviceId);
            eDeviceInputVO.setVideoMessage(videoMessage);
            eDeviceInputVO.setCurrentModelId(modelId);

            JSONObject body = JSONObject.fromObject(eDeviceInputVO);
            HttpEntity<String> formEntity = new HttpEntity<String>(body.toString(), headers);
            JSONObject json = restTemplate.postForEntity(url, formEntity, JSONObject.class).getBody();

            if( !json.get("code").equals("2000") ){
                new ServiceException("上传修改失败");
            }

        }catch ( Exception e ){
            e.printStackTrace();
            return "ERROR";
        }

        return "OK";
    }

    @Override
    public String updateERHeartbeat( String deviceId, String type, String status, Long timestamp, String targets ){
        try{
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            String url = "http://localhost:18800/dlplatform/ERHeartbeat/update";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            ERHeartbeatInputVO erHeartbeatInputVO = new ERHeartbeatInputVO();
            erHeartbeatInputVO.setDeviceId(deviceId);
            erHeartbeatInputVO.setType(type);
            erHeartbeatInputVO.setStatus(status);
            erHeartbeatInputVO.setTimestamp(timestamp);
            erHeartbeatInputVO.setTargets(targets);

            JSONObject body = JSONObject.fromObject(erHeartbeatInputVO);
            HttpEntity<String> formEntity = new HttpEntity<String>(body.toString(), headers);
            JSONObject json = restTemplate.postForEntity(url, formEntity, JSONObject.class).getBody();

            if( !json.get("code").equals("2000") ){
                new ServiceException("上传修改失败");
            }

        }catch ( Exception e ){
            e.printStackTrace();
            return "ERROR";
        }

        return "OK";
    }

    @Override
    public String updateModelMessage( String deviceId, String type, String modelId, Long timestamp){
        try{
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            String url = "http://localhost:18800/dlplatform/EDevice/update";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            EDeviceInputVO eDeviceInputVO = new EDeviceInputVO();
            eDeviceInputVO.setId(deviceId);
            eDeviceInputVO.setDeviceName("");
            eDeviceInputVO.setDeviceDesc("");
            eDeviceInputVO.setVideoRtsp("");
            eDeviceInputVO.setVideoMessage("");
            eDeviceInputVO.setCurrentModelId(modelId);

            JSONObject body = JSONObject.fromObject(eDeviceInputVO);
            HttpEntity<String> formEntity = new HttpEntity<String>(body.toString(), headers);
            JSONObject json = restTemplate.postForEntity(url, formEntity, JSONObject.class).getBody();

            if( !json.get("code").equals("2000") ){
                new ServiceException("上传修改失败");
            }

        }catch ( Exception e ){
            e.printStackTrace();
            return "ERROR";
        }

        return "OK";
    }

    @Override
    public String updateFileMessage( String deviceId, String type, String fileId, Long timestamp){
        try{
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            String url = "http://localhost:18800/dlplatform/EDevice/updateFile?" + "deviceId=" + deviceId + "&fileId=" + fileId;
            JSONObject json = restTemplate.getForObject(url, JSONObject.class);

            if( !json.get("code").equals("2000") ){
                new ServiceException("更新文件失败");
            }
        }catch ( Exception e ){
            e.printStackTrace();
            return "ERROR";
        }

        return "OK";
    }
}