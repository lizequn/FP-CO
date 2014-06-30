package uk.ac.ncl.cs.zequn.net.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import uk.ac.ncl.cs.zequn.core.Config;
import uk.ac.ncl.cs.zequn.net.entity.AggregationCreationEntity;
import uk.ac.ncl.cs.zequn.net.service.CoreService;
import uk.ac.ncl.cs.zequn.strategy.AggStrategy;

/**
 * Created by zequnli on 29/06/2014.
 */
@Controller
public class InitController {
    @Autowired
    private CoreService service;

    public int initServer(int id){
        Config.init(id);
        AggStrategy.init();
        service.initServer(id,new NextTupleImpl());
        return 1;
    }

    public int createAggregation(AggregationCreationEntity entity){
        service.createNewAggregation(entity);
        return 1;
    }

    public int startPassive(){
        service.initStart();
        return 1;
    }



}
