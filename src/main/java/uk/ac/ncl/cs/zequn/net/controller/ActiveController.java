package uk.ac.ncl.cs.zequn.net.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ncl.cs.zequn.core.aggregation.Index;
import uk.ac.ncl.cs.zequn.entity.StreamTuple;
import uk.ac.ncl.cs.zequn.entity.TupleCollection;
import uk.ac.ncl.cs.zequn.net.service.CoreService;

import java.util.LinkedList;
import java.util.Map;

/**
 * Created by zequnli on 29/06/2014.
 */
@Controller
public class ActiveController {
    @Autowired
    private CoreService service;
    @RequestMapping(value = "active")
    @ResponseBody
    public int active(@RequestBody LinkedList<Index> index){
        service.activeServer(index);
        return 1;
    }
    @RequestMapping(value = "stopActive")
    @ResponseBody
    public LinkedList<Index> stopActive(){
        return service.passiveServer();
    }
    @RequestMapping(value = "stream")
    @ResponseBody
    public void stream(@RequestBody StreamTuple tuple){
        service.handleStream(tuple);
    }

}
