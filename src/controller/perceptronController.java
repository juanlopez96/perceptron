/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import model.functions;
import model.trainingModel;
import view.indexView;
import view.perceptronView;

/**
 *
 * @author Juan
 */
public class perceptronController implements ActionListener {

    private perceptronView view;
    private Image img = null;
    functions func;
    private List<trainingModel> training;
    JTable table;
    JScrollPane addTable;
    double learning_coefficent;
    double threshold;
    List<Double> finalWeight;
    indexView index;
    int entryNumber = 0;

    public perceptronController() {
        this.view = new perceptronView();
        this.view.setResizable(false);
        this.view.setSize(1100,650);
        this.view.setVisible(true);
        try {
            img = new ImageIcon((this.getClass().getResource("/Images/ImagenPerceptron.jpg"))).getImage();
            Image dim = img.getScaledInstance(this.view.background.getWidth(), this.view.background.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(dim);
            this.view.background.setIcon(imageIcon);

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        this.finalWeight = new ArrayList<>();
        setTrainningModel();
        this.view.setVarButton.addActionListener(this);
        this.view.evaluateButton.addActionListener(this);
        this.view.evaluateButton.setEnabled(false);
        this.view.validateButton.addActionListener(this);
        this.view.validateButton.setEnabled(false);
        this.view.test.addActionListener(this);
        this.view.backButton.addActionListener(this);
        this.view.x1Label.setVisible(false);
        this.view.x2Label.setVisible(false);
        this.view.testFirstInput.setVisible(false);
        this.view.testSecondInput.setVisible(false);
        this.view.testResult.setVisible(false);
        this.view.test.setVisible(false);
    }

    //Se definen los parametros y el valor esperado para cada patron
    private void setTrainningModel() {
        training = new ArrayList<>();

        int patron = 0;
        while (entryNumber < 2) {
            try {
                entryNumber = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de parámetros"));
                patron = (int) Math.pow(2, entryNumber);
                System.out.println(patron);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un número mayor a 1", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        for (int i = 0; i < patron; i++) {
            trainingModel tr = new trainingModel();
            List<Integer> params = new ArrayList<>();
            int expected_result = -1;
            for (int j = 0; j < entryNumber; j++) {
                int param = -1;
                while (param < 0 || param > 1) {
                    try {
                        param = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el parametro " + (j + 1) + " del patron " + (i + 1)));
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "El valor debe ser 0 o 1", "Error", JOptionPane.ERROR_MESSAGE);
                        param = -1;
                    }
                    params.add(param);
                }
            }
            while (expected_result < 0 || expected_result > 1) {
                try {
                    expected_result = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el resultado esperado"));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "El valor debe ser 0 o 1", "Error", JOptionPane.ERROR_MESSAGE);
                    expected_result = -1;
                }
            }
            tr.setParameter(params);
            tr.setExpected_result(expected_result);
            training.add(tr);
        }

        /*training = new ArrayList<>();
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
        training.add(tr);*/
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.view.setVarButton) {
            this.func = new functions(this.training, entryNumber);
            this.view.firstWeight.setText(this.func.getWeight().get(0).toString());
            this.view.secondWeight.setText(this.func.getWeight().get(1).toString());
            this.view.learningCoefficent.setText("" + this.func.getLearningCoefficent());
            this.view.threshold.setText("" + this.func.getThreshold());
            this.view.x1Label.setVisible(false);
            this.view.x2Label.setVisible(false);
            this.view.evaluateButton.setEnabled(true);
            this.view.validateButton.setEnabled(false);
            this.view.testFirstInput.setVisible(false);
            this.view.testSecondInput.setVisible(false);
            this.view.testResult.setVisible(false);
            this.view.test.setVisible(false);
        } else if (e.getSource() == this.view.evaluateButton) {
            paintTable();
            this.view.firstWeight.setText(this.func.getWeight().get(0).toString());
            this.view.secondWeight.setText(this.func.getWeight().get(1).toString());
            this.view.learningCoefficent.setText("" + this.func.getLearningCoefficent());
            this.view.threshold.setText("" + this.func.getThreshold());
            this.view.x1Label.setVisible(false);
            this.view.x2Label.setVisible(false);
            this.view.evaluateButton.setEnabled(false);
            this.view.validateButton.setEnabled(true);
            this.view.testFirstInput.setVisible(false);
            this.view.testSecondInput.setVisible(false);
            this.view.testResult.setVisible(false);
            this.view.test.setVisible(false);
            this.threshold = this.func.getThreshold();
            this.learning_coefficent = this.func.getLearningCoefficent();
            this.finalWeight = this.func.getWeight();
        } else if (e.getSource() == this.view.validateButton) {
            paintTable();
            this.view.firstWeight.setText(this.func.getWeight().get(0).toString());
            this.view.secondWeight.setText(this.func.getWeight().get(1).toString());
            this.view.learningCoefficent.setText("" + this.func.getLearningCoefficent());
            this.view.threshold.setText("" + this.func.getThreshold());
            this.view.x1Label.setVisible(true);
            this.view.x2Label.setVisible(true);
            this.view.testFirstInput.setVisible(true);
            this.view.testSecondInput.setVisible(true);
            this.view.testResult.setVisible(true);
            this.view.test.setVisible(true);
        } else if (e.getSource() == this.view.test) {
            List<Integer> inputs = new ArrayList<>();
            for (int i = 0; i < entryNumber; i++) {
                int aux = -1;
                while (aux < 0 || aux > 1) {
                    try {
                        aux = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el valor del parametro " + (i + 1)));
                        inputs.add(aux);
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                }
            }
            ArrayList<trainingModel> testmodel = new ArrayList<>();
            trainingModel testTraining = new trainingModel();
            testTraining.setParameter(inputs);
            testmodel.add(testTraining);
            functions testM = new functions(testmodel, entryNumber);
            //testM.setWeight(finalWeight);
            testM.setLearningCoefficent(learning_coefficent);
            testM.setThreshold(threshold);
            testM.setTrainingModel(testmodel);
            testM.setWeight(finalWeight);
            JOptionPane.showMessageDialog(null, "" + testM.evaluate(0, this.entryNumber));
            /*System.out.println("Here");
            ArrayList<Integer> inputs = new ArrayList<>();
            try {
                if ((Integer.parseInt(this.view.testFirstInput.getText()) == 0 || Integer.parseInt(this.view.testFirstInput.getText()) == 1)
                        && (Integer.parseInt(this.view.testSecondInput.getText()) == 0 || Integer.parseInt(this.view.testSecondInput.getText()) == 1)) {
                    inputs.add(Integer.parseInt(this.view.testFirstInput.getText()));
                    inputs.add(Integer.parseInt(this.view.testSecondInput.getText()));
                    ArrayList<trainingModel> testmodel = new ArrayList<>();
                    trainingModel testAux = new trainingModel();
                    testAux.setParameter(inputs);
                    testmodel.add(testAux);

                    functions testM = new functions(testmodel);
                    //testM.setWeight(finalWeight);
                    testM.setLearningCoefficent(learning_coefficent);
                    testM.setThreshold(threshold);
                    testM.setTrainingModel(testmodel);
                    testM.setWeight(finalWeight);
                    this.view.testResult.setText("" + testM.evaluate(0));
                } else {
                    JOptionPane.showMessageDialog(this.view, "Las variables de las entradas deben ser 0 o 1", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this.view, "Tipo de dato erroneo", "Error", JOptionPane.ERROR_MESSAGE);
            }*/ {

            }
        } else if (e.getSource() == this.view.backButton) {
            this.view.dispose();
            index.setVisible(true);
        }
    }

    private void paintTable() {
        this.view.jPanel1.setPreferredSize(new Dimension(550, 200));
        this.func.calculate(this.func.getWeight());
        System.out.println("TAMAÑO " + this.func.allprocess.size());
        int cont = 0;
        String symbols[] = {"Ø", "D", "Y", "F(x)", "η", "δ"};
        String symbols2[] = {"Øi", "Interacion"};
        //Object[] header = {"Patrón", "X1", "X2", "P1", "P2", "Ø", "D", "Y", "F(x)", "η", "δ", "Δ1", "Δ2", "P1+", "P2+", "Øi", "Iteración"};
        Object[] header = new Object[9 + (entryNumber * 4)];
        int subcont = 1;
        while (cont < header.length) {
            if (cont == 0) {
                header[cont] = "Patron";
                cont++;
            } else if (cont <= entryNumber * 2) {
                for (int i = 0; i < entryNumber * 2; i++) {
                    header[cont] = (i < (entryNumber)) ? "X" + subcont : "P" + subcont;
                    subcont++;
                    subcont = (subcont > (entryNumber)) ? 1 : subcont;
                    cont++;
                }

            } else if (cont >= (entryNumber * 2) + 1 && cont < (entryNumber * 2) + 1 + symbols.length) {
                subcont = 1;
                for (String symbol : symbols) {
                    header[cont] = symbol;
                    cont++;
                }
            } else if (cont >= (entryNumber * 2) + 1 + symbols.length && cont < (entryNumber * 4) + 1 + symbols.length) {
                for (int i = 0; i < entryNumber * 2; i++) {
                    header[cont] = (i < (entryNumber)) ? "Δ" + subcont : "P" + subcont + "+";
                    subcont++;
                    subcont = (subcont > (entryNumber)) ? 1 : subcont;
                    cont++;
                }
            } else if (cont >= (entryNumber * 4) + 1 + symbols.length) {
                for (String symbol : symbols2) {
                    header[cont] = symbol;
                    cont++;
                }
            }

        }
        table = new JTable(this.func.getData(), header);
        addTable = new JScrollPane(table);
        //table.setSize(1100, 400);
        addTable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        addTable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.view.jPanel1.setLayout(new GridLayout());
        //this.view.jPanel1.setSize(1100,400);
        this.view.jPanel1.removeAll();
        this.view.jPanel1.add(addTable, BorderLayout.CENTER);
        this.view.jPanel1.revalidate();
        this.view.jPanel1.repaint();
        this.view.repaint();
        this.func.resetData();
    }

    public void setIndexView(indexView index) {
        this.index = index;
    }
}
