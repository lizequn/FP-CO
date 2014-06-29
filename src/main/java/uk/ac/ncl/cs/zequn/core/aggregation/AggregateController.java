package uk.ac.ncl.cs.zequn.core.aggregation;

import uk.ac.ncl.cs.zequn.core.Config;
import uk.ac.ncl.cs.zequn.core.OldTupleRequester;
import uk.ac.ncl.cs.zequn.entity.Result;
import uk.ac.ncl.cs.zequn.entity.StreamTuple;
import uk.ac.ncl.cs.zequn.entity.Tuple;
import uk.ac.ncl.cs.zequn.strategy.Aggregation;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by zequnli on 1/06/2014.
 */
public class AggregateController {

    private final int aggregateID;
    private final TupleFactory factory;
    private final InMemoryQueue inMemoryQueue;
    private final int sliceTime;
    private final int rangeTime;
    private Timer timer;
    private final TimerTask timerTask;
    private final Result result;
    private final AtomicReference<Tuple> remoteOldTuple = new AtomicReference<Tuple>();
    private LinkedList<Index> index;
    private long totalCount = 0;
    public AggregateController(final int aggregateID,final Aggregation aggregation, final int sliceTime, final int rangeTime,final OldTupleRequester requester){
        this.aggregateID = aggregateID;
        this.inMemoryQueue = new InMemoryQueue();
        this.factory = new TupleFactory(aggregation);
        this.sliceTime = sliceTime;
        this.rangeTime = rangeTime;
        this.result = new Result();
        this.timer = new Timer();
        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                //get new tuple
                Tuple newTuple = factory.getTuple();
                if(newTuple == null) newTuple = new Tuple();
                inMemoryQueue.offer(newTuple);
                if(index.getLast().id == Config.id){
                    index.getLast().count++;
                }else {
                    Index index1 = new Index();
                    index1.id = Config.id;
                    index1.count = 1;
                    index.offer(index1);
                }
                Tuple oldTuple = null;
//                if(inMemoryQueue.size()>= sliceTime/rangeTime){
//                    oldTuple = inMemoryQueue.get();//
//                }
                //need moving window
                if(totalCount>=sliceTime/rangeTime){
                    if(index.getFirst().id == Config.id){
                        oldTuple = inMemoryQueue.get();
                        index.getFirst().count--;
                    }else {
                        index.getFirst().count--;
                        //remote get
                        oldTuple = remoteOldTuple.get();
                        requester.requestNext(Config.id);
                    }
                }

                aggregation.updateResult(result,oldTuple,newTuple);
            }
        };
    }
    public Tuple get(){
        return inMemoryQueue.get();
    }
    public void offer(StreamTuple streamTuple){
        factory.offer(streamTuple);
    }
    public Result getResult(){
        return result;
    }
    public void setIndex(LinkedList<Index> index){
        this.index = index;
        this.totalCount = 0;
        for(Index index1:index){
            this.totalCount+=index1.count;
        }
    }
    public void setOldTuple(Tuple tuple){
        if(remoteOldTuple.get() ==null){
            remoteOldTuple.set(tuple);
        }else {
            throw new IllegalStateException();
        }
    }
    public int getAggregateID() {
        return aggregateID;
    }
    public LinkedList<Index> passive(){
        timer.cancel();
        return index;
    }
    public void active(LinkedList<Index> in){
        timer = new Timer();
        this.setIndex(in);
        timer.scheduleAtFixedRate(timerTask,0,sliceTime);
    }

}
