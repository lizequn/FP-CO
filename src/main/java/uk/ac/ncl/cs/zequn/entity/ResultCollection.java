package uk.ac.ncl.cs.zequn.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zequnli on 21/08/2014.
 */
public class ResultCollection {
    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }
    public void addResult(Result result){
        if(results == null){
            results= new ArrayList<Result>();

        }
        results.add(result);
    }
    public void clean(){
        results =null;
    }

    private ArrayList<Result> results;

}
