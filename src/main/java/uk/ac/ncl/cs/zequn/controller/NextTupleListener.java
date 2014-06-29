package uk.ac.ncl.cs.zequn.controller;

import uk.ac.ncl.cs.zequn.entity.Tuple;

import java.util.Map;

/**
 * Created by zequnli on 22/06/2014.
 */
public interface NextTupleListener {
    Map<Integer,Tuple> getResult(int id);
}
