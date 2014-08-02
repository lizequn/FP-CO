package uk.ac.ncl.cs.zequn.net.entity;

import uk.ac.ncl.cs.zequn.core.aggregation.Index;
import uk.ac.ncl.cs.zequn.entity.Result;

import java.util.LinkedList;

/**
 * Created by zequnli on 2/08/2014.
 */
public class ActiveEntity {
    private LinkedList<Index> index;
    private LinkedList<Result> result;

    public LinkedList<Index> getIndex() {
        return index;
    }

    public void setIndex(LinkedList<Index> index) {
        this.index = index;
    }

    public LinkedList<Result> getResult() {
        return result;
    }

    public void setResult(LinkedList<Result> result) {
        this.result = result;
    }
}
