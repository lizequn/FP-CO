package uk.ac.ncl.cs.zequn.net.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ncl.cs.zequn.core.Config;
import uk.ac.ncl.cs.zequn.core.aggregation.Index;
import uk.ac.ncl.cs.zequn.net.entity.AggregationCreationEntity;
import uk.ac.ncl.cs.zequn.net.service.CoreService;
import uk.ac.ncl.cs.zequn.strategy.AggStrategy;

import java.util.List;
import java.util.Map;

/**
 * Created by zequnli on 29/06/2014.
 */
@Controller
public class InitController {
    @Autowired
    private CoreService service;
    @RequestMapping(value = "init/{id}/{f}/{b}")
    @ResponseBody
    public int initServer(@PathVariable int id,@PathVariable int f,@PathVariable int b){
        Config.init(id,f,b);
//        AggStrategy.init();
        service.initServer(id,new NextTupleImpl());
        return 1;
    }
    @RequestMapping(value = "urlMapping")
    @ResponseBody
    public int urlMapping(@RequestBody List<String> url){
        Config.updateUrlMap(url);
        if(url.size()-1 == Config.id){
            Config.updateForward(0);
        }
        else {
            Config.updateForward(Config.id+1);
        }
        return 1;
    }
    @RequestMapping(value = "createAggregation")
    @ResponseBody
    public int createAggregation(@RequestBody AggregationCreationEntity entity){
        service.createNewAggregation(entity);
        return 1;
    }
    @RequestMapping(value = "start")
    @ResponseBody
    public int startPassive(){
        service.initStart();
        return 1;
    }



}
