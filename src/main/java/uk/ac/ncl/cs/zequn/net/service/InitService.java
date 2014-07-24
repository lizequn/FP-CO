package uk.ac.ncl.cs.zequn.net.service;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uk.ac.ncl.cs.zequn.core.Config;

import java.util.logging.Logger;

/**
 * Created by zequnli on 23/07/2014.
 */
@Component
public class InitService implements ApplicationListener<ContextRefreshedEvent> {
    private static Logger logger = Logger.getLogger(InitService.class.getName());
    private static boolean f = false;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(f) return;
        f = true;
        RestTemplate restTemplate = new RestTemplate();
        String url = Config.dispatcher;
        //register
        int id = restTemplate.getForObject(url+"/register/127.0.0.1:9001.ig",Integer.class);
        Config.id = id;

    }
}
