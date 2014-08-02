package uk.ac.ncl.cs.zequn.net.controller;

import org.springframework.web.client.RestTemplate;
import uk.ac.ncl.cs.zequn.core.Config;
import uk.ac.ncl.cs.zequn.core.aggregation.Index;

import java.util.LinkedList;

/**
 * Created by zequnli on 29/06/2014.
 */
public class ActiveRequester {
//    private RestTemplate restTemplate;
//    public ActiveRequester(){
//        restTemplate = new RestTemplate();
//    }
//    public void changeActive(LinkedList<Index> index){
//        int nextId = Config.forward;
//        String address = Config.urlMap.get(nextId);
//        //told dispatcher and next active worker
//        //active next Worker
////        int f1 = restTemplate.postForObject(address+"/active",index,Integer.class);
//        //notify dispatcher
//        int f2 = restTemplate.getForObject(Config.dispatcher+"/changeActive/"+Config.id+"/"+nextId,Integer.class);
//    }
}
