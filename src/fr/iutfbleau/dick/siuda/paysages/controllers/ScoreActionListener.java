package fr.iutfbleau.dick.siuda.paysages.controllers;
import java.awt.event.*;

import fr.iutfbleau.dick.siuda.paysages.views.Scoreboard;

public class ScoreActionListener implements ActionListener {
    private Scoreboard view;
    public ScoreActionListener(Scoreboard view){
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        view.dispose(); // Ferme la fenêtre des scores
    }
}
