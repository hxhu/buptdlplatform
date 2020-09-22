package com.bupt.dlplatform;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
//        String[][] configs = {
//                {"edge/list", "CenterList"},
//                {"edge/figure", "CenterFigure"},
//                {"edge/picture", "CenterPicture"},
//                {"edge/video", "CenterVideo"},
//                {"edge/map", "CenterMap"},
//                {"edge/heartbeat", "CenterHeartbeat"},
//        };
          MqttSubsrcibe mqttSubsrcibe = new MqttSubsrcibe();
          mqttSubsrcibe.start();
//        ExecutorService executorService = Executors.newFixedThreadPool(6);
//        for( int i = 0; i < 2; i++ ){
//            executorService.execute( new MqttSubsrcibe(configs[i][0], configs[i][1]) );
//        }

    }
}
