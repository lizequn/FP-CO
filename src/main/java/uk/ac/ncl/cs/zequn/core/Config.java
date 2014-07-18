package uk.ac.ncl.cs.zequn.core;

import java.util.ArrayList;

/**
 * Created by zequnli on 2/06/2014.
 */
public class Config {
    public static int id = -1;
    public static int forward = -1;
    public static int backward = -1;
    public static void init(int id,int f,int b){
        Config.id = id;
        Config.forward = f;
        Config.backward =b;
    }
    public static void updateForward(int f){
        Config.forward = f;
    }
    public static void updateBackWard(int b){
        Config.backward = b;
    }

}
