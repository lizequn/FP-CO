package uk.ac.ncl.cs.zequn.entity;

import java.util.List;

/**
 * Created by zequnli on 1/06/2014.
 */
public class StreamTuple {
    private List<SingleTuple> tuples;

    public List<SingleTuple> getTuples() {
        return tuples;
    }

    public void setTuples(List<SingleTuple> tuples) {
        this.tuples = tuples;
    }
}
