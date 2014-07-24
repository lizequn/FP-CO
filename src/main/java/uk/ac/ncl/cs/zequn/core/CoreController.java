package uk.ac.ncl.cs.zequn.core;

import uk.ac.ncl.cs.zequn.net.controller.ActiveRequester;
import uk.ac.ncl.cs.zequn.net.controller.NextTupleListener;
import uk.ac.ncl.cs.zequn.core.aggregation.AggregateController;
import uk.ac.ncl.cs.zequn.core.aggregation.Index;
import uk.ac.ncl.cs.zequn.entity.StreamTuple;
import uk.ac.ncl.cs.zequn.entity.Tuple;
import uk.ac.ncl.cs.zequn.strategy.Aggregation;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by zequnli on 1/06/2014.
 */
public class CoreController implements OldTupleRequester{
    private static Logger logger = Logger.getLogger(CoreController.class.getName());
    private Map<Integer,AggregateController> map;
    private Status status = Status.WAITING;
    private int count;
    private final NextTupleListener listener;
    private final TimerTask timeCounter;
    private Timer timer;

    private int workingTime =20;
    /*
    init add listener used to get tuple from other coordinator
     */
    public CoreController(NextTupleListener listener){
        map = new HashMap<Integer, AggregateController>();
        this.listener = listener;
        logger.info("init");
        timeCounter = new TimerTask() {
            @Override
            public void run() {
                workingTime--;
                logger.info("time left: "+workingTime);
                if(workingTime == 0){
                    workingTime =20;
                    changeActive();
                }
            }
        };
    }
    public boolean checkStatus(Status s){
        return this.status == s;
    }
    /**
     * Create new Aggregate, should be same with other coordinator
     * @param id
     * @param aggregation
     * @param slice
     * @param range
     * @return
     */
    public boolean createNewController(int id,Aggregation aggregation,int slice,int range){
        AggregateController aggregateController = new AggregateController(id,aggregation,slice,range,this);
        map.put(id, aggregateController);
        logger.info("createNewController"+id);
        return true;
    }

    /**
     * Working in passive mode
     * @return new global index
     */
    public LinkedList<Index> passive(){

        if(map.isEmpty()) throw new IllegalStateException();
        this.status = Status.PASSIVE;
        LinkedList<Index> result = new LinkedList<Index>();
        for(AggregateController controller:map.values()){
            result = controller.passive();
        }
        logger.info("passive");
        return result;
    }

    /**
     * Working in active mode
     * @param info
     * @return
     */
    public boolean active(LinkedList<Index> info){
        if(map.isEmpty()) return false;
        this.status = Status.ACTIVE;
        for(int i: map.keySet()){
            LinkedList<Index> index = new LinkedList<Index>();
            for(Index in:info){
                index.add(in);
            }
            map.get(i).active(index);
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(timeCounter,0,1000);
        logger.info("active");
        return true;
    }

    /**
     * offer new tuple
     * Only used in active
     * @param tuple
     */
    public void offer(StreamTuple tuple){
        //logger.info("Stream Tuple");
        if(status == Status.ACTIVE){
            for(AggregateController aggregateController :map.values()){
                aggregateController.offer(tuple);
            }
        }else {
            throw new IllegalStateException();
        }
    }

    /**
     * get old tuple
     * Only used in passive
     * @return
     */
    public Map<Integer,Tuple> getOldTuple(){
        logger.info("getOldTuple");
        if(status == Status.PASSIVE){
        Map<Integer,Tuple> list = new HashMap<Integer, Tuple>();
        for(AggregateController aggregateController :map.values()){
            list.put(aggregateController.getAggregateID(), aggregateController.get());
        }
        return list;
        }
        else {
            throw new IllegalStateException();
        }
    }

    private void changeActive(){
        ActiveRequester requester = new ActiveRequester();
        timer.cancel();
        workingTime =20;
        requester.changeActive(this.passive());
    }

    public int getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(int workingTime) {
        this.workingTime = workingTime;
    }


    @Override
    public synchronized void requestNext(int id) {
        count++;
        if(count == map.size()){
            logger.info("Request Next");
            Map<Integer,Tuple> re =  listener.getResult(id);
            for(int i:re.keySet()){
                map.get(i).setOldTuple(re.get(i));
            }
            count = 0;
        }
    }
}
