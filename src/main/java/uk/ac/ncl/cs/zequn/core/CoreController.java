package uk.ac.ncl.cs.zequn.core;

import uk.ac.ncl.cs.zequn.net.controller.NextTupleListener;
import uk.ac.ncl.cs.zequn.core.aggregation.AggregateController;
import uk.ac.ncl.cs.zequn.core.aggregation.Index;
import uk.ac.ncl.cs.zequn.entity.StreamTuple;
import uk.ac.ncl.cs.zequn.entity.Tuple;
import uk.ac.ncl.cs.zequn.strategy.Aggregation;

import java.util.*;

/**
 * Created by zequnli on 1/06/2014.
 */
public class CoreController implements OldTupleRequester{
    private Map<Integer,AggregateController> map;
    private Status status = Status.WAITING;
    private int count;
    private final NextTupleListener listener;
    /*
    init add listener used to get tuple from other coordinator
     */
    public CoreController(NextTupleListener listener){
        map = new HashMap<Integer, AggregateController>();
        this.listener = listener;
    }

    /**
     * Create new Aggregate, should be same with other coordinator
     * @param id
     * @param index
     * @param aggregation
     * @param slice
     * @param range
     * @return
     */
    public boolean createNewController(int id,LinkedHashMap<Integer,Long> index,Aggregation aggregation,int slice,int range){
        AggregateController aggregateController = new AggregateController(id,aggregation,slice,range,this);
        map.put(id, aggregateController);
        return true;
    }

    /**
     * Working in passive mode
     * @return
     */
    public Map<Integer,LinkedList<Index>> passive(){
        if(map.isEmpty()) throw new IllegalStateException();
        this.status = Status.PASSIVE;
        Map<Integer,LinkedList<Index>> result = new HashMap<Integer, LinkedList<Index>>();
        for(AggregateController controller:map.values()){
            result.put(controller.getAggregateID(),controller.passive());
        }
        return result;
    }

    /**
     * Working in active mode
     * @param info
     * @return
     */
    public boolean active(Map<Integer,LinkedList<Index>> info){
        if(map.isEmpty()) return false;
        this.status = Status.ACTIVE;
        for(int i: info.keySet()){
            map.get(i).active(info.get(i));
        }
        return true;
    }

    /**
     * offer new tuple
     * Only used in active
     * @param tuple
     */
    public void offer(StreamTuple tuple){
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


    @Override
    public synchronized void requestNext(int id) {
        count++;
        if(count == map.size()){
            Map<Integer,Tuple> re =  listener.getResult(id);
            for(int i:re.keySet()){
                map.get(i).setOldTuple(re.get(i));
            }
            count = 0;
        }
    }
}
