package uk.ac.ncl.cs.zequn.strategy;

import uk.ac.ncl.cs.zequn.strategy.impl.AvgImpl;
import uk.ac.ncl.cs.zequn.strategy.impl.AvgServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zequnli on 30/06/2014.
 */
public enum AggStrategy {
    AVG1,AVG2;
//    private static Map<AggStrategy,Aggregation> repo = new HashMap<AggStrategy, Aggregation>();
//    public static void init(){
//        repo.put(AVG,new AvgImpl());
//    }
    public static Aggregation getByString(String arg,int id){
        AggStrategy agg = AggStrategy.valueOf(arg);
        if(agg == AVG1){
            return new AvgImpl(id);
        }else if(agg == AVG2){
            return new AvgServiceImpl(id);
        }else
        {
            throw new IllegalArgumentException("no such aggregation");
        }
    }
}
