package uk.ac.ncl.cs.zequn.net.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import uk.ac.ncl.cs.zequn.core.Config;
import uk.ac.ncl.cs.zequn.entity.ResultCollection;
import uk.ac.ncl.cs.zequn.entity.Tuple;
import uk.ac.ncl.cs.zequn.net.entity.TupleCollection;

import java.util.List;
import java.util.Map;

/**
 * Created by zequnli on 30/06/2014.
 */
public class NextTupleImpl implements NextTupleListener {
    private RestTemplate restTemplate;
    public NextTupleImpl(){
        restTemplate = new RestTemplate();
    }
    @Override
    public List<Tuple> getResult(int id) {
        String url = Config.urlMap.get(id);
        String rUrl = url+"/"+Config.PASSIVE+"/"+id;
        TupleCollection collection =  restTemplate.getForObject(rUrl, TupleCollection.class);
        return collection.getList();
    }

    @Override
    public void pushResult(ResultCollection collection) {
        String url = Config.dispatcher+"/result";
        int i = restTemplate.postForObject(url,collection,Integer.class);
        if(i!=0){
            System.out.println("un");
        }
    }
}
