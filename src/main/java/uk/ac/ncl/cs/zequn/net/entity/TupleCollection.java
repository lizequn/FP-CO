package uk.ac.ncl.cs.zequn.net.entity;

import uk.ac.ncl.cs.zequn.entity.Tuple;

import java.util.List;

/**
 * Created by zequnli on 23/07/2014.
 */
public class TupleCollection {

    private int state;
    private List<Tuple> list;

    public List<Tuple> getList() {
        return list;
    }

    public void setList(List<Tuple> list) {
        this.list = list;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
