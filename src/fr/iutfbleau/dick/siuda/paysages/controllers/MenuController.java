package fr.iutfbleau.dick.siuda.paysages.controllers;

import fr.iutfbleau.dick.siuda.paysages.views.MenuView;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.util.Map;

import fr.iutfbleau.dick.siuda.paysages.models.MenuModel;

public class MenuController {
    private MenuView view;
    private MenuModel model;
    public MenuController(MenuView view, MenuModel model){
        this.view = view;
        this.model = model;
        view.getJouerPlateauButton().addActionListener(e -> {Map<Integer, JButton> seriesButton = view.ouvrirFenetreSeries(model.listeSeries());
                                                             seriesListener(seriesButton);});
        view.getReglesRetourButton().addActionListener(e -> view.getCardLayout().show(view.getCardPanel(), "Menu"));
        view.getCommandesRetourButton().addActionListener(e -> view.getCardLayout().show(view.getCardPanel(), "Menu"));
    }

    private void seriesListener(Map<Integer, JButton> itemList){
        for (Map.Entry<Integer, JButton> itemSet : itemList.entrySet()){
            JButton item = itemSet.getValue();
            item.addActionListener(e -> {
                    JOptionPane.showMessageDialog(view.getSeriesFrame(), "Vous avez sélectionné la série : " + item.getText());
                    view.getSeriesFrame().dispose(); // Fermer la fenêtre parent
                    new PlateauController();
                });
        }
    }
}
