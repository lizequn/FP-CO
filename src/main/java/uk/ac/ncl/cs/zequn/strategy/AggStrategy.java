package uk.ac.ncl.cs.zequn.strategy;

import uk.ac.ncl.cs.zequn.strategy.impl.AvgImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zequnli on 30/06/2014.
 */
public enum AggStrategy {
    AVG;
    private static Map<AggStrategy,Aggregation> repo = new HashMap<AggStrategy, Aggregation>();
    public static void init(){
        repo.put(AVG,new AvgImpl());
    }
    public static Aggregation getByString(String arg){
        AggStrategy agg = AggStrategy.valueOf(arg);
        return repo.get(agg);
    }
}
