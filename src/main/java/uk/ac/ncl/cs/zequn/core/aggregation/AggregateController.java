package uk.ac.ncl.cs.zequn.core.aggregation;

import uk.ac.ncl.cs.zequn.core.Config;
import uk.ac.ncl.cs.zequn.core.OldTupleRequester;
import uk.ac.ncl.cs.zequn.entity.Result;
import uk.ac.ncl.cs.zequn.entity.SingleTuple;
import uk.ac.ncl.cs.zequn.entity.StreamTuple;
import uk.ac.ncl.cs.zequn.entity.Tuple;
import uk.ac.ncl.cs.zequn.strategy.Aggregation;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

/**
 * Created by zequnli on 1/06/2014.
 */
public class AggregateController {
    private final static Logger logger = Logger.getLogger(AggregateController.class.getName());
    private final int aggregateID;
    private final TupleFactory factory;
    private final InMemoryQueue inMemoryQueue;
    private final int sliceTime;
    private final int rangeTime;
    private Timer timer;
    private boolean findOld = false;
    private static int finished = 0;
    public void setResult(Result result) {
        this.result = result;
    }

    //    private TimerTask timerTask;
    private Result result;
    private Tuple remoteOldTuple = null;
    private LinkedList<Index> index;
    private long totalCount = 0;
    private OldTupleRequester requester;
    private Aggregation aggregation;
    public AggregateController(final int aggregateID,final Aggregation aggregation, final int sliceTime, final int rangeTime,final OldTupleRequester requester){
        logger.setUseParentHandlers(false);
        this.aggregateID = aggregateID;
        this.aggregation = aggregation;
        this.inMemoryQueue = new InMemoryQueue();
        this.factory = new TupleFactory(aggregation);
        this.sliceTime = sliceTime;
        this.rangeTime = rangeTime;
        this.result = new Result();
        this.timer = new Timer();
        this.requester = requester;
//        this.timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                //get new tuple
//                Tuple newTuple = factory.getTuple();
//                if(newTuple == null) newTuple = new Tuple();
//                inMemoryQueue.offer(newTuple);
//                totalCount++;
//                if(index.getLast().id == Config.id){
//                    index.getLast().count++;
//                }else {
//                    Index index1 = new Index();
//                    index1.id = Config.id;
//                    index1.count = 1;
//                    index.offer(index1);
//                }
//                Tuple oldTuple = null;
////                if(inMemoryQueue.size()>= sliceTime/rangeTime){
////                    oldTuple = inMemoryQueue.get();//
////                }
//                //need moving window
//
//                if(totalCount>=rangeTime/sliceTime){
//                    if(index.getFirst().id == Config.id){
//                        oldTuple = inMemoryQueue.get();
//                        index.getFirst().count--;
//                    }else {
//                        index.getFirst().count--;
//                        //remote get
//                        //todo
//                        oldTuple = remoteOldTuple.get();
//                        requester.requestNext(Config.id);
//                    }
//                    totalCount--;
//                }
//                aggregation.updateResult(result,oldTuple,newTuple);
//                logger.info(result.getRe()+"");
//                logger.info(totalCount+"");
//            }
//        };
    }
    class myTimerTask extends TimerTask{

        @Override
        public void run() {
            result.setExceptedTime(System.currentTimeMillis());
            //get new tuple
            Tuple newTuple = factory.getTuple();
            if(newTuple == null) newTuple = new Tuple();
            inMemoryQueue.offer(newTuple);
            totalCount++;
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

            logger.info(finished+++""+ totalCount+":"+rangeTime/sliceTime);
            if(totalCount>=rangeTime/sliceTime){
                if(index.getFirst().id == Config.id){
                    oldTuple = inMemoryQueue.get();
                    index.getFirst().count--;
                    if(index.getFirst().count ==0){
                        index.pollFirst();
                    }
                }else {
                    boolean f = false;
                    while(!findOld){
                        if(!f){
                            requester.requestNext(index.getFirst().id);
                            f=true;
                        }
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    index.getFirst().count--;
                    if(index.getFirst().count ==0){
                        index.pollFirst();
                    }

                    oldTuple = remoteOldTuple;
                    remoteOldTuple = null;
                    findOld = false;
                }
                totalCount--;
            }

            aggregation.updateResult(result,oldTuple,newTuple);
            result.setRealTimeWithoutNet(System.currentTimeMillis());
            requester.pushResult(result);
//            logger.info("result"+result.getRe()/result.getSize()+"");
//            logger.info("total count"+totalCount+"");
        }
    }
    public Tuple get(){
        //logger.info("get Tuple");
        return inMemoryQueue.get();
    }
    public void offer(StreamTuple streamTuple){
        for(SingleTuple tuple:streamTuple.getTuples()){
            factory.offer(tuple);
        }

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
        //logger.info("totalCount:"+totalCount);
        //init
        if(this.totalCount == 0){
            index.clear();
            Index i = new Index();
            i.setId(Config.id);
            i.setCount(0);
            index.add(i);
        }


    }
    public void setOldTuple(Tuple tuple){
        if(remoteOldTuple ==null){
            findOld = true;
            remoteOldTuple =tuple;
        }else {
            // throw new IllegalStateException();
            logger.info("reSet:"+System.currentTimeMillis());

        }
    }
    public int getAggregateID() {
        return aggregateID;
    }
    public LinkedList<Index> passive(){
        timer.cancel();
        // logger.info("passive");
        return index;
    }
    public void active(LinkedList<Index> in){
        //logger.info("active");
        timer = new Timer();
        this.setIndex(in);
        //logger.info("index:" +in.getFirst());
        timer.schedule(new myTimerTask(), 0, sliceTime);
    }
}
