/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import model.functions;
import model.trainingModel;
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

    public perceptronController() {
        this.view = new perceptronView();
        this.view.setResizable(false);
        this.view.setVisible(true);
        try {
            img = new ImageIcon((this.getClass().getResource("/Images/ImagenPerceptron.jpg"))).getImage();
            Image dim = img.getScaledInstance(this.view.background.getWidth(), this.view.background.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(dim);
            this.view.background.setIcon(imageIcon);

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        setTrainningModel();
        this.view.setVarButton.addActionListener(this);
        this.view.evaluateButton.addActionListener(this);
        this.view.evaluateButton.setEnabled(false);
        this.view.validateButton.addActionListener(this);
        this.view.validateButton.setEnabled(false);
    }

    private void setTrainningModel() {
        training = new ArrayList<>();
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.view.setVarButton) {
            this.func = new functions(this.training);
            this.view.firstWeight.setText(this.func.getWeight().get(0).toString());
            this.view.secondWeight.setText(this.func.getWeight().get(1).toString());
            this.view.learningCoefficent.setText("" + this.func.getLearningCoefficent());
            this.view.threshold.setText("" + this.func.getThreshold());
            this.view.evaluateButton.setEnabled(true);
            this.view.validateButton.setEnabled(false);
        } else if (e.getSource() == this.view.evaluateButton) {
            paintTable();
            this.view.firstWeight.setText(this.func.getWeight().get(0).toString());
            this.view.secondWeight.setText(this.func.getWeight().get(1).toString());
            this.view.learningCoefficent.setText("" + this.func.getLearningCoefficent());
            this.view.threshold.setText("" + this.func.getThreshold());
            this.view.evaluateButton.setEnabled(false);
            this.view.validateButton.setEnabled(true);
        } else if(e.getSource() == this.view.validateButton){
            paintTable();
            this.view.firstWeight.setText(this.func.getWeight().get(0).toString());
            this.view.secondWeight.setText(this.func.getWeight().get(1).toString());
            this.view.learningCoefficent.setText("" + this.func.getLearningCoefficent());
            this.view.threshold.setText("" + this.func.getThreshold());
        }
    }
    private void paintTable(){
        this.view.jPanel1.setPreferredSize(new Dimension(550, 200));
            this.func.calculate(this.func.getWeight());
            System.out.println("TAMAÑO " + this.func.allprocess.size());
            Object[] header = {"Patrón", "X1", "X2", "P1", "P2", "Ø", "D", "Y", "F(x)", "η", "δ", "Δ1", "Δ2", "P1+", "P2+", "Øi", "Iteración"};
            table = new JTable(this.func.getData(), header);
            table.setPreferredScrollableViewportSize(new Dimension(500, 200));
            addTable = new JScrollPane(table);
            addTable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            addTable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

            this.view.jPanel1.setLayout(new BorderLayout());
            this.view.jPanel1.removeAll();
            this.view.jPanel1.add(addTable, BorderLayout.CENTER);
            this.view.jPanel1.revalidate();
            this.view.jPanel1.repaint();
            this.view.repaint();
            this.func.resetData();
    }

}
