package uk.ac.ncl.cs.zequn.entity;

/**
 * Created by zequnli on 1/06/2014.
 */
public class Result {
    private double re;
    private int size;

    public long getExceptedTime() {
        return exceptedTime;
    }

    public void setExceptedTime(long exceptedTime) {
        this.exceptedTime = exceptedTime;
    }

    public long getRealTimeWithoutNet() {
        return realTimeWithoutNet;
    }

    public void setRealTimeWithoutNet(long realTimeWithoutNet) {
        this.realTimeWithoutNet = realTimeWithoutNet;
    }

    private long exceptedTime;
    private long realTimeWithoutNet;

    public void addNew(Tuple tuple){
        re+=tuple.getInfo();
        size+=tuple.getSize();
    }
    public void deleteOld(Tuple tuple){
        if(tuple == null) return;
        re-=tuple.getInfo();
        size-=tuple.getSize();
    }
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getRe() {
        return re;
    }

    public void setRe(double re) {
        this.re = re;
    }
}
