/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import view.indexView;
import view.perceptronView;

/**
 *
 * @author Juan
 */
public class indexController implements ActionListener {

    private indexView index;
    private Image img = null;

    public indexController() {
        this.index = new indexView();
        this.index.setVisible(true);
        this.index.andButton.addActionListener(this);
        this.index.andOption.addActionListener(this);
        try {
            img = new ImageIcon((this.getClass().getResource("/Images/ImagenPerceptron.jpg"))).getImage();
            Image dim = img.getScaledInstance(this.index.background.getWidth(), this.index.background.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(dim);
            this.index.background.setIcon(imageIcon);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.index.andButton || e.getSource() == this.index.andOption) {
            perceptronController controller = new perceptronController();
            this.index.dispose();
        }
        
    }
}