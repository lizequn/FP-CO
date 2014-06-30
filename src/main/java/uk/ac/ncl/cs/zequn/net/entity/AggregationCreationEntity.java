package uk.ac.ncl.cs.zequn.net.entity;

import java.util.LinkedHashMap;

/**
 * Created by zequnli on 30/06/2014.
 */
public class AggregationCreationEntity {
    private int id;
    private int slice;
    private int range;
    private LinkedHashMap<Integer,Long> index;
    private String aggStrategy;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSlice() {
        return slice;
    }

    public void setSlice(int slice) {
        this.slice = slice;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public LinkedHashMap<Integer, Long> getIndex() {
        return index;
    }

    public void setIndex(LinkedHashMap<Integer, Long> index) {
        this.index = index;
    }

    public String getAggStrategy() {
        return aggStrategy;
    }

    public void setAggStrategy(String aggStrategy) {
        this.aggStrategy = aggStrategy;
    }
}
