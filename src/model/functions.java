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
    private double evaluate_function = 0;
    String aux;
    public ArrayList<allProcess> allprocess = new ArrayList<allProcess>();
    public allProcess process = new allProcess();
    private Object[][] data;
    public int funcres;
    public functions(List<trainingModel> training_model) {
        this.training_model = training_model;
        this.random = new Random();
        aux = String.format("%.2f", this.random.nextDouble());
        aux = aux.replaceAll(",", ".");
        this.learning_coefficent = Double.parseDouble(aux);
        aux = String.format("%.2f", (Math.random() * (max - min) + min));
        aux = aux.replaceAll(",", ".");
        this.threshold = Double.parseDouble(aux);
        this.weight = new ArrayList<>();
        this.setWeight();
        //System.out.println(this.learning_coefficent + " -- " + this.threshold);
    }

    public void setWeight() {

        this.training_model.get(0).getParameter().forEach(x -> {
            aux = String.format("%.2f", (Math.random() * (max - min) + min));
            aux = aux.replaceAll(",", ".");
            this.weight.add(Double.parseDouble(aux));
        });

    }
    public void setWeight(List<Double> weight){
        this.weight = weight;
    }
    public void setLearningCoefficent(double learning_coefficent){
        this.learning_coefficent = learning_coefficent;
    }
    public void setThreshold(double threshold){
        this.threshold = threshold;
    }
    public List<Double> getWeight() {
        return this.weight;
    }

    public double getLearningCoefficent() {
        return this.learning_coefficent;
    }

    public double getThreshold() {
        return this.threshold;
    }

    public int evaluate(int subcont) {
        process = new allProcess();
        process.setPatron(""+(subcont+1));
        this.weightCont = 0;
        this.training_model.get(subcont).getParameter().forEach(x -> {
            //System.out.println(x);
            process.x[this.weightCont] = x.toString();
            process.weight[this.weightCont] = this.weight.get(this.weightCont).toString();

            this.evaluate_function += (x * this.weight.get(this.weightCont));

            this.weightCont += 1;
        });
        process.setTheshold("" + this.threshold);
        process.setD("" + this.training_model.get(subcont).getExpected_Result());
        this.evaluate_function -= this.threshold;
        this.evaluate_function = Double.parseDouble((String.format("%.2f", this.evaluate_function)).replaceAll(",", "."));
        process.setY("" + this.evaluate_function);
        process.setLearning_coefficent("" + this.learning_coefficent);
        int fx = this.evaluate_function > 0 ? 1 : 0;
        
        return fx;
    }

    public void calculate(List<Double> weight) {
        

        this.weight = weight;
        int cont = this.training_model.size();
        int iteration = 0;
        int subcont = 0;

        while (cont > 0) {
            this.evaluate_function = 0;
            
            do {
                

                iteration++;
                int fx = evaluate(subcont);
                
                process.setIteration("" + iteration);
                process.setFx("" + fx);
                this.bias = this.training_model.get(subcont).getExpected_Result() - fx;
                process.setBias("" + this.bias);
                this.weightCont = 0;
                if (this.bias != 0) {
                    this.training_model.get(subcont).getParameter().forEach(x -> {
                        aux = String.format("%.2f", (this.learning_coefficent * this.bias * x));
                        aux = aux.replaceAll(",", ".");
                        double weight_variation = Double.parseDouble(aux);
                        process.weight_variation[weightCont] = "" + weight_variation;
                        aux = String.format("%.2f", this.weight.get(this.weightCont) + weight_variation);
                        aux = aux.replaceAll(",", ".");
                        double newWeight = Double.parseDouble(aux);
                        process.new_weight[weightCont] = "" + newWeight;
                        this.weight.set(this.weightCont, newWeight);
                        this.weightCont += 1;
                    });
                    aux = String.format("%.2f", this.threshold - this.learning_coefficent * this.bias);
                    aux = aux.replaceAll(",", ".");
                    this.threshold = Double.parseDouble(aux);
                    process.setNew_threshold("" + this.threshold);
                    cont = this.training_model.size();
                }
                else{
                    this.weightCont = 0;
                    this.training_model.get(subcont).getParameter().forEach(x -> {
                        process.weight_variation[weightCont] = "0";
                        process.new_weight[weightCont] = ""+this.weight.get(weightCont);
                        process.setNew_threshold(""+this.threshold);
                        this.weightCont++;
                    });
                }
                System.out.println("Patron: " + (subcont + 1) + "\nIteracion: " + (iteration) + "\nError: "
                        + (this.bias) + "\nFuncion: " + this.evaluate_function + "\nFuncion evaluada: " + fx + "\n-----------");
                allprocess.add(process);
            } while (this.bias != 0);
            subcont += 1;
            if (subcont > this.training_model.size() - 1) {
                subcont = 0;
            }
            cont--;
        }
        data = new Object[allprocess.size()][17];
        for (int i = 0; i < allprocess.size(); i++) {
            data[i][0] = allprocess.get(i).getPatron();
            data[i][1] = allprocess.get(i).x[0];
            data[i][2] = allprocess.get(i).x[1];
            data[i][3] = allprocess.get(i).weight[0];
            data[i][4] = allprocess.get(i).weight[1];
            data[i][5] = allprocess.get(i).getTheshold();
            data[i][6] = allprocess.get(i).getD();
            data[i][7] = allprocess.get(i).getY();
            data[i][8] = allprocess.get(i).getFx();
            data[i][9] = allprocess.get(i).getLearning_coefficent();
            data[i][10] = allprocess.get(i).getBias();
            data[i][11] = allprocess.get(i).weight_variation[0];
            data[i][12] = allprocess.get(i).weight_variation[1];
            data[i][13] = allprocess.get(i).new_weight[0];
            data[i][14] = allprocess.get(i).new_weight[1];
            data[i][15] = allprocess.get(i).getNew_threshold();
            data[i][16] = allprocess.get(i).getIteration();
        }
    }
    public Object[][] getData(){
        return data;
    }
    public void resetData (){
        allprocess = new ArrayList<>();
    }
    public void setTrainingModel(List<trainingModel> training_model){
        this.training_model = training_model;
    }
}
