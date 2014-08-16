package uk.ac.ncl.cs.zequn.net.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uk.ac.ncl.cs.zequn.core.Config;
import uk.ac.ncl.cs.zequn.net.service.MonitorService;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zequnli on 16/08/2014.
 */
@Service
public class MonitorServiceImpl implements MonitorService {
    private static boolean freeF = false;
    RestTemplate restTemplate = new RestTemplate();
    class task extends TimerTask {

        @Override
        public void run() {
            double free = Runtime.getRuntime().freeMemory()/1024/1024;
            if(free<50){
                freeF = false;
                restTemplate.getForObject(Config.dispatcher+"/updateStatus/"+Config.id+"/"+free,Integer.class);
            }else if(free >50){
                freeF = true;
                restTemplate.getForObject(Config.dispatcher+"/updateStatus/"+Config.id+"/"+free,Integer.class);
            }
        }
    }
    @Override
    public void start() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new task(),0,10000);
    }
    public static void main(String [] args){
        System.out.println(Runtime.getRuntime().freeMemory()/1024/1024);
    }
}
