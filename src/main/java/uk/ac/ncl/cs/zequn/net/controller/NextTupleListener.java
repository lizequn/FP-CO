package uk.ac.ncl.cs.zequn.net.controller;

import uk.ac.ncl.cs.zequn.entity.ResultCollection;
import uk.ac.ncl.cs.zequn.entity.Tuple;

import java.util.List;
import java.util.Map;

/**
 * Created by zequnli on 22/06/2014.
 */
public interface NextTupleListener {
    List<Tuple> getResult(int id);
    void pushResult(ResultCollection collection);
}
