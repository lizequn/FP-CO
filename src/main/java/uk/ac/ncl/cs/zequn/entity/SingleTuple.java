package uk.ac.ncl.cs.zequn.entity;

/**
 * Created by zequnli on 16/08/2014.
 */
public class SingleTuple {
    private double waitingTime;
    private double serviceTime;
    private int serviceId;

    public double getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(double waitingTime) {
        this.waitingTime = waitingTime;
    }

    public double getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(double serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
}
