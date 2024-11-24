package fr.iutfbleau.dick.siuda.paysages;

import fr.iutfbleau.dick.siuda.paysages.controllers.MenuController;
import fr.iutfbleau.dick.siuda.paysages.views.MenuView;
import fr.iutfbleau.dick.siuda.paysages.models.MenuModel;

/**
 * La classe <code>Main</code> sert de point d'entrée principal pour l'application.
 * <p>
 * Elle initialise les composants principaux du modèle-vue-contrôleur (MVC) :
 * <ul>
 *   <li><b>MenuView</b> : responsable de l'affichage graphique et des interactions utilisateur.</li>
 *   <li><b>MenuModel</b> : représente les données et la logique métier associées au menu.</li>
 *   <li><b>MenuController</b> : coordonne les interactions entre la vue et le modèle.</li>
 * </ul>
 * <p>
 * Ce programme démarre en instanciant la vue, puis en liant la vue et le modèle à l'aide du contrôleur.
 * </p>
 * 
 * @version 1.0
 * @author Siuda Matéo
 * @author Dick Adrien
 */
public class Main {

    /**
     * Méthode principale (point d'entrée) de l'application.
     * 
     * Cette méthode initialise les composants principaux de l'application en suivant le
     * design pattern Modèle-Vue-Contrôleur (MVC). Elle :
     * 
     *   <li>Crée une instance de <code>MenuView</code>, la classe responsable de la vue.</li>
     *   <li>Crée une instance de <code>MenuModel</code>, la classe responsable du modèle, en passant une référence de la vue.</li>
     *   <li>Lie la vue et le modèle via une instance de <code>MenuController</code>, la classe responsable du contrôleur.</li>
     * </ol>
     * </p>
     * 
     * @param args les arguments de ligne de commande (non utilisés dans cette application).
     */
    public static void main(String[] args) {
        MenuView mv = new MenuView();
        new MenuController(mv, new MenuModel(mv));
    }
}
