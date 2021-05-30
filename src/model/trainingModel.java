package model;

import java.util.ArrayList;
import java.util.List;

public class trainingModel {

    private List<Integer> parameter;
    private int expected_result;
    
    public trainingModel(){
        this.parameter = new ArrayList<>();
        expected_result = 0;
    }

    public void setParameter(List<Integer> parameter) {
        this.parameter = parameter;
    }

    public void setExpected_result(int expected_result) {
        this.expected_result = expected_result;
    }
    
    public List<Integer> getParameter() { return this.parameter; }
    public int getExpected_Result(){ return this.expected_result;}

}
