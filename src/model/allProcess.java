/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Juan
 */
public class allProcess {
    String patron;
    String []x;
    String []weight;
    String theshold;
    String d;
    String y;
    String fx;
    String learning_coefficent;
    String error;
    String []weight_variation;
    String []new_weight;
    String new_threshold;
    String iteration;
    
    public allProcess(){
        x = new String[2];
        weight_variation = new String[2];
        new_weight = new String[2];
        weight = new String[2];
    }

    public String getPatron() {
        return patron;
    }

    public void setPatron(String patron) {
        this.patron = patron;
    }

    public String getTheshold() {
        return theshold;
    }

    public void setTheshold(String theshold) {
        this.theshold = theshold;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getFx() {
        return fx;
    }

    public void setFx(String fx) {
        this.fx = fx;
    }

    public String getLearning_coefficent() {
        return learning_coefficent;
    }

    public void setLearning_coefficent(String learning_coefficent) {
        this.learning_coefficent = learning_coefficent;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getNew_threshold() {
        return new_threshold;
    }

    public void setNew_threshold(String new_threshold) {
        this.new_threshold = new_threshold;
    }

    public String getIteration() {
        return iteration;
    }

    public void setIteration(String iteration) {
        this.iteration = iteration;
    }
    
}

