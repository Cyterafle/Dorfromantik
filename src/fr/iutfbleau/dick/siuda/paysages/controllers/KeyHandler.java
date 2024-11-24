package fr.iutfbleau.dick.siuda.paysages.controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import fr.iutfbleau.dick.siuda.paysages.models.PlateauModel;

public class KeyHandler implements KeyListener {

    private PlateauModel model;
    private PlateauController controller;
    public KeyHandler(PlateauModel model, PlateauController controller){
        this.model = model;
        this.controller = controller;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_KP_LEFT:
            case KeyEvent.VK_LEFT:
                model.getNexTuile().setOrientation('-');
                break;
                case KeyEvent.VK_KP_RIGHT:
            case KeyEvent.VK_RIGHT:
            model.getNexTuile().setOrientation('+');
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        controller.updateTuile();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //Rien à mettre par ici
    }
    
}
