/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perceptron;

import java.util.ArrayList;
import java.util.List;
import model.functions;
import model.trainingModel;

/**
 *
 * @author Juan
 */
public class Perceptron {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        functions f;
        List<trainingModel> training = new ArrayList<>();
        trainingModel tr = new trainingModel();
        List<Integer> param = new ArrayList<>();
        param.add(1);
        param.add(1);
        int expected_result = 1;
        tr.setParameter(param);
        tr.setExpected_result(expected_result);
        training.add(tr);
        
        tr = new trainingModel();
        param = new ArrayList<>();
        param.add(1);
        param.add(0);
        expected_result = 0;
        tr.setParameter(param);
        tr.setExpected_result(expected_result);
        training.add(tr);
        
        tr = new trainingModel();
        param = new ArrayList<>();
        param.add(0);
        param.add(1);
        expected_result = 0;
        tr.setParameter(param);
        tr.setExpected_result(expected_result);
        training.add(tr);
        
        tr = new trainingModel();
        param = new ArrayList<>();
        param.add(0);
        param.add(0);
        expected_result = 0;
        tr.setParameter(param);
        tr.setExpected_result(expected_result);
        training.add(tr);
        
        f= new functions(training);
        f.setWeight();
        f.calculate(f.getWeight());
        
        System.out.println("Comprobar");
        f.calculate(f.getWeight());
    }
    
}
