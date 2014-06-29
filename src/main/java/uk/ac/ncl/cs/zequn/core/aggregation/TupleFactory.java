package uk.ac.ncl.cs.zequn.core.aggregation;

import uk.ac.ncl.cs.zequn.entity.StreamTuple;
import uk.ac.ncl.cs.zequn.entity.Tuple;
import uk.ac.ncl.cs.zequn.strategy.Aggregation;

/**
 * Created by zequnli on 1/06/2014.
 */
public class TupleFactory {
    private final Aggregation aggregation;
    private Tuple tuple;
    private Object lock = new Object();
    public TupleFactory(Aggregation aggregation){
        this.aggregation = aggregation;

    }

    public void offer(StreamTuple streamTuple){
        synchronized (lock){
            if(tuple == null) tuple = new Tuple();
            aggregation.handleStream(tuple,streamTuple);
        }
    }

    public Tuple getTuple(){
        synchronized (lock){
            if(null == tuple) return null;
            Tuple re = tuple;
            tuple = null;
            return re;
        }
    }
}
