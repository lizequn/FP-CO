package uk.ac.ncl.cs.zequn.entity;

import java.io.Serializable;

/**
 * Created by zequnli on 1/06/2014.
 */
public class Tuple implements Serializable {
    private double info;
    private int size;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getInfo() {
        return info;
    }

    public void setInfo(double info) {
        this.info = info;
    }
}
