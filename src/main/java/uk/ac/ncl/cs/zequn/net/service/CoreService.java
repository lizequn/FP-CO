package uk.ac.ncl.cs.zequn.net.service;

import uk.ac.ncl.cs.zequn.net.controller.NextTupleListener;
import uk.ac.ncl.cs.zequn.net.entity.AggregationCreationEntity;

/**
 * Created by zequnli on 30/06/2014.
 */
public interface CoreService {
    int initServer(int id,NextTupleListener listener);
    boolean createNewAggregation(AggregationCreationEntity entity);
    void initStart();
}
