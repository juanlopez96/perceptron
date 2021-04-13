/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Juan
 */
public class functions {

    private List<trainingModel> training_model;
    private List<Double> weight;
    private double threshold;
    private double bias;
    private double learning_coefficent;
    private Random random;
    private final int max = 1;
    private final int min = -1;
    private int weightCont = 0;
    private double evaluate = 0;

    public functions(List<trainingModel> training_model) {
        this.training_model = training_model;
        this.random = new Random();
        this.learning_coefficent = this.random.nextDouble();
        this.threshold = this.random.nextDouble();
        this.weight = new ArrayList<>();
        System.out.println(this.learning_coefficent  +" -- " + this.threshold);
    }

    public void setWeight() {
        //Arreglar
        this.training_model.get(0).getParameter().forEach(x -> {
            this.weight.add((Math.random() * (max-min)+min));
        });
       
    }

    public void calculate() {

        int cont = this.training_model.size();
        int iteration = 0;
        int subcont = 0;
        while (cont > 0) {
            do {
                iteration ++;
                this.evaluate = 0;
                this.weightCont = 0;
                this.training_model.get(subcont).getParameter().forEach(x -> {
                    //System.out.println(x);
                    this.evaluate += (x * this.weight.get(this.weightCont));
                    this.weightCont += 1;
                });
                this.weightCont = 0;
                this.evaluate -= this.threshold;
                int fx = this.evaluate > 0 ? 1 : 0;
                this.bias = this.training_model.get(subcont).getExpected_Result() - fx;
                if (this.bias != 0) {
                    this.training_model.get(subcont).getParameter().forEach(x -> {
                        this.weight.set(this.weightCont, (this.weight.get(this.weightCont) + (this.learning_coefficent * this.bias * x)));
                        this.weightCont += 1;
                    });
                    this.threshold = this.threshold - this.learning_coefficent*this.bias;
                    cont = this.training_model.size();
                }
                System.out.println("Patron: " + (subcont+1) +"\nIteracion: " + (iteration) +"\nError: " 
                        + (this.bias) +"\nFuncion: " + this.evaluate +"\nFuncion evaluada: " + fx +"\n-----------");
            } while (this.bias != 0);
            subcont += 1;
            if(subcont > this.training_model.size()-1){
                subcont = 0;
            }
            cont --;
        }

    }
}
