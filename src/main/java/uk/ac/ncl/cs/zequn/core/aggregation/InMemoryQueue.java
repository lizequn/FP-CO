package uk.ac.ncl.cs.zequn.core.aggregation;

import uk.ac.ncl.cs.zequn.entity.Tuple;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by zequnli on 1/06/2014.
 */
public class InMemoryQueue {
    private final BlockingDeque<Tuple> queue;
    public InMemoryQueue(){
        queue = new LinkedBlockingDeque<Tuple>();
    }
    public void offer(Tuple tuple){
        queue.offer(tuple);
    }
    public Tuple get(){
        return queue.poll();
    }
    public int size(){
        return queue.size();
    }

}
