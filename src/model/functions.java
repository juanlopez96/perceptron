package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class functions {

    private List<trainingModel> training_model;
    private List<Double> weight;
    private double threshold;
    private double error;
    private double learning_coefficent;
    private Random random;
    /*si se desea disminuir los intervalos de los pesos y el umbral el min usado se comenta y el otro se descomenta*/
    private final int max = 1;
    private final int min = -1;
    /*private final double min=0.5;*/
    private int weightCont = 0;
    private double evaluate_function = 0;
    String aux;
    public ArrayList<allProcess> allprocess = new ArrayList<allProcess>();
    public allProcess process;
    private Object[][] data;
    private int entryNumber;
    boolean tol = false;
    public functions(List<trainingModel> training_model, int entryNumber) {
        this.training_model = training_model;
        this.random = new Random();
        this.entryNumber = entryNumber;
        this.process = new allProcess(this.entryNumber);
        //Valor del coeficiente de aprendizaje
        aux = String.format("%.2f", this.random.nextDouble());
        aux = aux.replaceAll(",", ".");
        this.learning_coefficent = Double.parseDouble(aux);
        //Valor del umbral
        /*si se desea que el umbral (threshold) sea de 0 a 1 se comentan las siguientes dos lineas de aux*/
        aux = String.format("%.2f", (Math.random() * (max - min) + min));
        aux = aux.replaceAll(",", ".");
        this.threshold = Double.parseDouble(aux);
        this.weight = new ArrayList<>();
        //Valor de los n pesos con respecto a los n parámetros
        this.setWeight();
        //System.out.println(this.learning_coefficent + " -- " + this.threshold);
    }

    public void setWeight() {
        //Para cada parametro se genera su respectivo peso
        this.training_model.get(0).getParameter().forEach(x -> {
            aux = String.format("%.2f", (Math.random() * (max - min) + min));
            aux = aux.replaceAll(",", ".");
            this.weight.add(Double.parseDouble(aux));
        });

    }

    public void setWeight(List<Double> weight) {
        this.weight = weight;
    }

    public void setLearningCoefficent(double learning_coefficent) {
        this.learning_coefficent = learning_coefficent;
    }

    public void setThreshold(double threshold) {
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

    public int evaluate(int subcont, int entryNumber) {
        process = new allProcess(entryNumber);
        process.setPatron("" + (subcont + 1));
        this.weightCont = 0;
        //Para cada parametro se evalua y se suma al total de la funcion (Funcion de activacion)
        this.training_model.get(subcont).getParameter().forEach(x -> {
            //System.out.println(x);
            process.x[this.weightCont] = x.toString();
            process.weight[this.weightCont] = this.weight.get(this.weightCont).toString();

            this.evaluate_function += (x * this.weight.get(this.weightCont));

            this.weightCont += 1;
        });
        process.setTheshold("" + this.threshold);
        process.setD("" + this.training_model.get(subcont).getExpected_Result());
        //A la sumatoria del parametro xi * el peso pi se le resta el umbral
        this.evaluate_function -= this.threshold;
        this.evaluate_function = Double.parseDouble((String.format("%.2f", this.evaluate_function)).replaceAll(",", "."));
        process.setY("" + this.evaluate_function);
        process.setLearning_coefficent("" + this.learning_coefficent);
        //Si el valor de la funcion evaluada es mayor a 0, devuelve un 1, de lo contrato devuelve 0
        int fx = this.evaluate_function > 0 ? 1 : 0;

        return fx;
    }

    public void calculate(List<Double> weight) {
        this.weight = weight;
        int cont = this.training_model.size();
        int iteration = 0;
        int subcont = 0;
        tol = false;
        while (cont > 0) {
            this.evaluate_function = 0;
            do {
                iteration++;
                if (iteration > 5000) {
                    tol = true;
                    break;
                }
                //Se obtiene el valor de la funcion evaluada para el patron subcont
                int fx = evaluate(subcont, this.entryNumber);
                process.setIteration("" + iteration);
                process.setFx("" + fx);
                //Se calcula el error existente
                this.error = this.training_model.get(subcont).getExpected_Result() - fx;
                process.setError("" + this.error);
                this.weightCont = 0;
                //Si el valor del error es diferente de 0, se debe recalcular los pesos y el umbral
                if (this.error != 0) {
                    //Se empieza a recorrer cada uno de los patrones
                    this.training_model.get(subcont).getParameter().forEach(x -> {
                        //Se calcula la variacion de los pesos
                        aux = String.format("%.2f", (this.learning_coefficent * this.error * x));
                        aux = aux.replaceAll(",", ".");
                        double weight_variation = Double.parseDouble(aux);
                        process.weight_variation[weightCont] = "" + weight_variation;
                        //Se define el nuevo valor de los pesos
                        aux = String.format("%.2f", this.weight.get(this.weightCont) + weight_variation);
                        aux = aux.replaceAll(",", ".");
                        double newWeight = Double.parseDouble(aux);
                        process.new_weight[weightCont] = "" + newWeight;
                        this.weight.set(this.weightCont, newWeight);
                        this.weightCont += 1;
                    });
                    //Se calcula el nuevo umbral
                    aux = String.format("%.2f", this.threshold - this.learning_coefficent * this.error);
                    aux = aux.replaceAll(",", ".");
                    this.threshold = Double.parseDouble(aux);
                    process.setNew_threshold("" + this.threshold);
                    cont = this.training_model.size();
                } else {
                    //Si el error es 0, permanece constante el umbral y los pesos
                    this.weightCont = 0;
                    this.training_model.get(subcont).getParameter().forEach(x -> {
                        process.weight_variation[weightCont] = "0";
                        process.new_weight[weightCont] = "" + this.weight.get(weightCont);
                        process.setNew_threshold("" + this.threshold);
                        this.weightCont++;
                    });
                }
                System.out.println("Patron: " + (subcont + 1) + "\nIteracion: " + (iteration) + "\nError: "
                        + (this.error) + "\nFuncion: " + this.evaluate_function + "\nFuncion evaluada: " + fx + "\n-----------");
                allprocess.add(process);
            } while (this.error != 0);
            subcont += 1;
            if (subcont > this.training_model.size() - 1) {
                subcont = 0;
            }
            cont--;
        }
        if (tol == false) {
            cont = 0;
            data = new Object[allprocess.size()][9 + (entryNumber * 4)];
            for (int i = 0; i < allprocess.size(); i++) {
                cont = 0;
                while (cont < (9 + (entryNumber * 4))) {
                    if (cont == 0) {
                        data[i][cont] = allprocess.get(i).getPatron();
                        cont++;
                    } else if (cont <= entryNumber * 2) {
                        for (int j = 0; j < allprocess.get(i).x.length; j++) {
                            data[i][cont] = allprocess.get(i).x[j];
                            cont++;
                        }
                        for (int j = 0; j < allprocess.get(i).weight.length; j++) {
                            data[i][cont] = allprocess.get(i).weight[j];
                            cont++;
                        }
                    } else if (cont >= (entryNumber * 2) + 1 && cont < (entryNumber * 2) + 7) {
                        data[i][cont] = allprocess.get(i).getTheshold();
                        cont++;
                        data[i][cont] = allprocess.get(i).getD();
                        cont++;
                        data[i][cont] = allprocess.get(i).getY();
                        cont++;
                        data[i][cont] = allprocess.get(i).getFx();
                        cont++;
                        data[i][cont] = allprocess.get(i).getLearning_coefficent();
                        cont++;
                        data[i][cont] = allprocess.get(i).getError();
                        cont++;
                    } else if (cont >= (entryNumber * 2) + 7 && cont < (entryNumber * 4) + 7) {
                        for (int j = 0; j < allprocess.get(i).weight_variation.length; j++) {
                            data[i][cont] = allprocess.get(i).weight_variation[j];
                            cont++;
                        }
                        for (int j = 0; j < allprocess.get(i).new_weight.length; j++) {
                            data[i][cont] = allprocess.get(i).new_weight[j];
                            cont++;
                        }
                    } else if (cont >= (9 + (entryNumber * 4)) -2 && cont < (9 + (entryNumber * 4)) - 1) {
                        data[i][cont] = allprocess.get(i).getNew_threshold();
                        cont++;
                    } else {
                        data[i][cont] = allprocess.get(i).getIteration();
                        cont++;
                    }
                }
            }
        }
    }

    public Object[][] getData() {
        return data;
    }

    public void resetData() {
        allprocess = new ArrayList<>();
    }

    public void setTrainingModel(List<trainingModel> training_model) {
        this.training_model = training_model;
    }
    public boolean getTol(){
        return tol;
    }
}
