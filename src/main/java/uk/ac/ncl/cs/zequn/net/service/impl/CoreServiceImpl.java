package uk.ac.ncl.cs.zequn.net.service.impl;

import org.springframework.stereotype.Service;
import uk.ac.ncl.cs.zequn.core.Status;
import uk.ac.ncl.cs.zequn.core.aggregation.Index;
import uk.ac.ncl.cs.zequn.entity.Tuple;
import uk.ac.ncl.cs.zequn.net.controller.NextTupleListener;
import uk.ac.ncl.cs.zequn.core.CoreController;
import uk.ac.ncl.cs.zequn.net.entity.AggregationCreationEntity;
import uk.ac.ncl.cs.zequn.net.service.CoreService;
import uk.ac.ncl.cs.zequn.strategy.AggStrategy;
import uk.ac.ncl.cs.zequn.strategy.Aggregation;

import java.util.LinkedList;
import java.util.Map;

/**
 * Created by zequnli on 30/06/2014.
 */
@Service
public class CoreServiceImpl implements CoreService {
    private static CoreController coreController;
    @Override
    public int initServer(int id,NextTupleListener listener) {
        coreController = new CoreController(listener);
        return 1;
    }

    @Override
    public boolean createNewAggregation(AggregationCreationEntity entity) {
        String agg = entity.getAggStrategy();
        Aggregation fun = AggStrategy.getByString(agg);
        coreController.createNewController(entity.getId(),entity.getIndex(),fun,entity.getSlice(),entity.getRange());
        return true;
    }

    @Override
    public void initStart() {
        coreController.passive();
    }

    @Override
    public Map<Integer, Tuple> getOldTuple() {
        if(coreController == null) throw new IllegalStateException();
        if(!coreController.checkStatus(Status.PASSIVE)) throw new IllegalStateException();
        return coreController.getOldTuple();
    }

    @Override
    public void activeServer(LinkedList<Index> info) {
        if(coreController == null) throw new IllegalStateException();
        if(!coreController.checkStatus(Status.PASSIVE)) throw new IllegalStateException();
        coreController.active(info);
    }
}
