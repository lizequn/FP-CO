package uk.ac.ncl.cs.zequn.net.service;

import uk.ac.ncl.cs.zequn.core.aggregation.Index;
import uk.ac.ncl.cs.zequn.entity.StreamTuple;
import uk.ac.ncl.cs.zequn.entity.Tuple;
import uk.ac.ncl.cs.zequn.net.controller.NextTupleListener;
import uk.ac.ncl.cs.zequn.net.entity.AggregationCreationEntity;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by zequnli on 30/06/2014.
 */
public interface CoreService {
    int initServer(int id,NextTupleListener listener);
    boolean createNewAggregation(AggregationCreationEntity entity);
    void initStart();
    List<Tuple> getOldTuple();
    void activeServer(LinkedList<Index> info);
    LinkedList<Index> passiveServer();
    void handleStream(StreamTuple tuple);
}
