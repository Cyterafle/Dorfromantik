package fr.iutfbleau.dick.siuda.paysages.controllers;

import fr.iutfbleau.dick.siuda.paysages.models.ScoreModel;
import fr.iutfbleau.dick.siuda.paysages.views.Scoreboard;
import java.awt.event.*;

public class ScoreController {
    private Scoreboard view;
    private ScoreModel model;
    public ScoreController(int idSerie){
        model = new ScoreModel();
        view = new Scoreboard(idSerie, model.afficherScoresPourSerie(idSerie));
        view.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose(); 
            }
        });
    }
}
