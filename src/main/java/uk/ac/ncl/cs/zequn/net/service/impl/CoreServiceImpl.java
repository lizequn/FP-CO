package uk.ac.ncl.cs.zequn.net.service.impl;

import org.springframework.stereotype.Service;
import uk.ac.ncl.cs.zequn.core.Status;
import uk.ac.ncl.cs.zequn.core.aggregation.Index;
import uk.ac.ncl.cs.zequn.entity.StreamTuple;
import uk.ac.ncl.cs.zequn.entity.Tuple;
import uk.ac.ncl.cs.zequn.net.controller.NextTupleListener;
import uk.ac.ncl.cs.zequn.core.CoreController;
import uk.ac.ncl.cs.zequn.net.entity.ActiveEntity;
import uk.ac.ncl.cs.zequn.net.entity.AggregationCreationEntity;
import uk.ac.ncl.cs.zequn.net.service.CoreService;
import uk.ac.ncl.cs.zequn.strategy.AggStrategy;
import uk.ac.ncl.cs.zequn.strategy.Aggregation;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by zequnli on 30/06/2014.
 */
@Service
public class CoreServiceImpl implements CoreService {
    private static CoreController coreController;
    private static Logger logger = Logger.getLogger("service");


    @Override
    public int initServer(int id,NextTupleListener listener) {
        coreController = new CoreController(listener);
        logger.info("init");
        return 1;
    }

    @Override
    public boolean createNewAggregation(AggregationCreationEntity entity) {
        String agg = entity.getAggStrategy();
        Aggregation fun = AggStrategy.getByString(agg,entity.getAggServiceId());
        coreController.createNewController(entity.getId(),fun,entity.getSlice(),entity.getRange());
        return true;
    }

    @Override
    public void initStart() {
        coreController.passive();
    }

    @Override
    public List<Tuple> getOldTuple() {
        if(coreController == null) throw new IllegalStateException();
        if(!coreController.checkStatus(Status.PASSIVE)) throw new IllegalStateException();
        return coreController.getOldTuple();
    }

    @Override
    public void activeServer(ActiveEntity entity) {
        if(coreController == null) throw new IllegalStateException();
        if(!coreController.checkStatus(Status.PASSIVE)) throw new IllegalStateException();
        coreController.active(entity.getIndex(),entity.getResult());
    }

    @Override
    public ActiveEntity passiveServer() {
        if(coreController == null) throw new IllegalStateException();
        if(coreController.checkStatus(Status.PASSIVE)) throw new IllegalStateException();
        return coreController.passive();
    }

    @Override
    public void handleStream(StreamTuple tuple) {
        if(coreController == null) throw new IllegalStateException();
        if(!coreController.checkStatus(Status.ACTIVE)) throw new IllegalStateException();
        coreController.offer(tuple);

    }
}
