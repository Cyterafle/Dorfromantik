package fr.iutfbleau.dick.siuda.paysages;

import fr.iutfbleau.dick.siuda.paysages.controllers.MenuController;
import fr.iutfbleau.dick.siuda.paysages.views.MenuView;
import fr.iutfbleau.dick.siuda.paysages.models.MenuModel;

public class Main {

    public static void main(String[] args) {
        MenuView mv = new MenuView();
        new MenuController(mv, new MenuModel(mv));
    }
}