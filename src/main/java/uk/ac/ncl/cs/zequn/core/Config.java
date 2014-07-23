package uk.ac.ncl.cs.zequn.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by zequnli on 2/06/2014.
 */
public class Config {
    public static String dispatcher = "http://127.0.0.1:8080";
    private static Logger logger = Logger.getLogger(Config.class.getName());
    public static int id = -1;
    public static int forward = -1;
    public static int backward = -1;
//    public static String url = null;
    public static List<String> urlMap =null;
    public static String PASSIVE = "getOldTuple";
    public static void init(int id,int f,int b){
        Config.id = id;
        Config.forward = f;
        Config.backward =b;
        logger.info("init"+id+" ,"+f+" ,"+b);
//        Config.url = url;
    }
    public static void updateUrlMap(List<String> map){
        logger.info("mapping");
       // logger.info(map.get(1));
        Config.urlMap = map;
    }
    public static void updateForward(int f){
        Config.forward = f;
    }
    public static void updateBackWard(int b){
        Config.backward = b;
    }

}
