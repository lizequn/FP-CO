package uk.ac.ncl.cs.zequn.strategy;

import uk.ac.ncl.cs.zequn.entity.Result;
import uk.ac.ncl.cs.zequn.entity.StreamTuple;
import uk.ac.ncl.cs.zequn.entity.Tuple;
import uk.ac.ncl.cs.zequn.net.entity.TupleCollection;

/**
 * Created by zequnli on 1/06/2014.
 */
public interface Aggregation {
    void handleStream(Tuple tuple,StreamTuple streamTuple);
    void updateResult(Result result,Tuple oldTuple,Tuple newTuple);
    Tuple getTupleFromCollections(TupleCollection tupleCollection);
}
