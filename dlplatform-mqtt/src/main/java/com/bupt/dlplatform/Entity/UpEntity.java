package com.bupt.dlplatform.Entity;

import lombok.*;

/**
 * Created by huhx on 2020/12/7
 */
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpEntity {
    private String deviceId;

    private String message; // videoMessage

    private String data; // modelId

    private Long timestamp;
}

/*
heart_info: {
     "latitude": "3995901",
	 "longitude": "11635179",
	 "time": "1610596249",
	 "productName": "ai_device",
	 "deviceName": "hhx",
	 "deviceKey": "e8e6370ef0c1",
	 "mqttIP": "182.92.6.59",
	 "mqttPort": "1883",
	 "serverIP": "10.1.100.38",
	 "serverPort": "10086",
	 "localIP": "10.166.40.20",
	 "localGateway": "10.166.40.1"
}
 */