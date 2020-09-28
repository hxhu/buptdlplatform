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
          MqttSubsrcibe mqttSubsrcibe = new MqttSubsrcibe();
          mqttSubsrcibe.start();

//          KafKaProducer kafKaProducer = new KafKaProducer();
//          kafKaProducer.createKafKaProducer();

    }
}
