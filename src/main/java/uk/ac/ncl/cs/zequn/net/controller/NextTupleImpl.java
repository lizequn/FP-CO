package uk.ac.ncl.cs.zequn.net.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import uk.ac.ncl.cs.zequn.core.Config;
import uk.ac.ncl.cs.zequn.entity.Tuple;

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
        return restTemplate.getForObject(rUrl, List.class);
    }
}
