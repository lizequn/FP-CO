package uk.ac.ncl.cs.zequn.net.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ncl.cs.zequn.core.Config;
import uk.ac.ncl.cs.zequn.entity.Tuple;
import uk.ac.ncl.cs.zequn.net.service.CoreService;

import java.util.List;
import java.util.Map;

/**
 * Created by zequnli on 29/06/2014.
 * version 1 on 18/07
 */
@Controller
public class PassiveController {
    @Autowired
    private CoreService service;
    @RequestMapping(value = "getOldTuple/{id}")
    @ResponseBody
    public List<Tuple> getOldTuple(@PathVariable int id){
        if(Config.id != id){
            throw new IllegalStateException();
        }
        return  service.getOldTuple();
    }
}
