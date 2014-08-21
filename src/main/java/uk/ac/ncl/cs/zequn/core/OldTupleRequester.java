package uk.ac.ncl.cs.zequn.core;

import uk.ac.ncl.cs.zequn.entity.Result;

/**
 * Created by zequnli on 22/06/2014.
 */
public interface OldTupleRequester {
    void requestNext(int id);
    void pushResult(Result result);
}
