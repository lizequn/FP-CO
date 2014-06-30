package uk.ac.ncl.cs.zequn.strategy.impl;

import uk.ac.ncl.cs.zequn.entity.Result;
import uk.ac.ncl.cs.zequn.entity.StreamTuple;
import uk.ac.ncl.cs.zequn.entity.Tuple;
import uk.ac.ncl.cs.zequn.strategy.Aggregation;

/**
 * Created by zequnli on 30/06/2014.
 */
public class AvgImpl implements Aggregation {

    @Override
    public void handleStream(Tuple tuple, StreamTuple streamTuple) {
        tuple.increase(streamTuple.getInfo());
    }

    @Override
    public void updateResult(Result result, Tuple oldTuple, Tuple newTuple) {

    }
}
