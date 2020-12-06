package com.bupt.dlplatform.rpc;

import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.exception.ServiceException;
import com.bupt.dlplatform.mapper.EDeviceRepository;
import com.bupt.dlplatform.model.EDeviceEntity;
import com.bupt.dlplatform.vo.EDeviceInputVO;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Created by huhx on 2020/12/6
 */
public class DLPlatformServiceImpl implements DLPlatformService {

    @Override
    public String updateVideoMessage( String deviceId, String videoMessage, String modelId ){
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
}