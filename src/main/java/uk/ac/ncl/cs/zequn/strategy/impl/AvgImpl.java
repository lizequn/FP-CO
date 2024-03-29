package uk.ac.ncl.cs.zequn.strategy.impl;

import uk.ac.ncl.cs.zequn.entity.Result;
import uk.ac.ncl.cs.zequn.entity.SingleTuple;
import uk.ac.ncl.cs.zequn.entity.StreamTuple;
import uk.ac.ncl.cs.zequn.entity.Tuple;
import uk.ac.ncl.cs.zequn.net.entity.TupleCollection;
import uk.ac.ncl.cs.zequn.strategy.Aggregation;

import java.util.List;

/**
 * Created by zequnli on 30/06/2014.
 */
public class AvgImpl implements Aggregation {
    private int id;
    public AvgImpl(int id){
        this.id = id;
    }

    @Override
    public void handleStream(Tuple tuple, SingleTuple sTuple) {
        if(this.id == sTuple.getServiceId()){
            tuple.increase(sTuple.getWaitingTime());
        }
    }

    @Override
    public void updateResult(Result result, Tuple oldTuple, Tuple newTuple) {
        result.addNew(newTuple);
        result.deleteOld(oldTuple);
    }

    @Override
    public Tuple getTupleFromCollections(TupleCollection tupleCollection) {return null;
    }
}
