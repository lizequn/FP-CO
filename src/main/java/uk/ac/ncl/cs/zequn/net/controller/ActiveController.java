package uk.ac.ncl.cs.zequn.net.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ncl.cs.zequn.core.aggregation.Index;
import uk.ac.ncl.cs.zequn.entity.StreamTuple;
import uk.ac.ncl.cs.zequn.net.entity.ActiveEntity;
import uk.ac.ncl.cs.zequn.net.service.CoreService;

import java.util.LinkedList;

/**
 * Created by zequnli on 29/06/2014.
 */
@Controller
public class ActiveController {
    @Autowired
    private CoreService service;
    @RequestMapping(value = "active")
    @ResponseBody
    public int active(@RequestBody ActiveEntity entity){
        service.activeServer(entity);
        return 1;
    }
    @RequestMapping(value = "stopActive")
    @ResponseBody
    public ActiveEntity stopActive(){
        return service.passiveServer();
    }
    @RequestMapping(value = "stream")
    @ResponseBody
    public void stream(@RequestBody StreamTuple tuple){
        try{
            service.handleStream(tuple);

        }catch (Exception e){

        }
    }

}
