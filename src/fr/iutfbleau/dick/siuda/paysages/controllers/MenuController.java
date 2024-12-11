package fr.iutfbleau.dick.siuda.paysages.controllers;

import fr.iutfbleau.dick.siuda.paysages.views.MenuView;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.util.Map;

import fr.iutfbleau.dick.siuda.paysages.models.MenuModel;

/**
 * La classe <code>MenuController</code> représente le contrôleur dans l'architecture MVC.
 * <p>
 * Elle gère les interactions entre la vue (<code>MenuView</code>) et le modèle (<code>MenuModel</code>).
 * Cette classe est responsable de la gestion des événements déclenchés par les boutons et des actions associées.
 * </p>
 * 
 * @version 1.0
 * @author Siuda Matéo
 * @author Dick Adrien
 */
public class MenuController {

    /**
     * La vue associée au menu.
     */
    private MenuView view;

    /**
     * Constructeur de la classe <code>MenuController</code>.
     * <p>
     * Initialise le contrôleur en liant la vue et le modèle, et configure les gestionnaires d'événements
     * pour les différents boutons de l'interface utilisateur.
     * </p>
     * 
     * @param view une instance de <code>MenuView</code>, la vue du menu.
     * @param model une instance de <code>MenuModel</code>, le modèle du menu.
     */
    public MenuController(MenuView view, MenuModel model) {
        this.view = view;

        // Configuration des actions pour les boutons de la vue
        view.getJouerPlateauButton().addActionListener(e -> {
            Map<Integer, JButton> seriesButton = view.ouvrirFenetreSeries(model.listeSeries());
            seriesListener(seriesButton);
        });

        view.getReglesRetourButton().addActionListener(e -> 
            view.getCardLayout().show(view.getCardPanel(), "Menu")
        );

        view.getCommandesRetourButton().addActionListener(e -> 
            view.getCardLayout().show(view.getCardPanel(), "Menu")
        );
    }

    /**
     * Ajoute des gestionnaires d'événements aux boutons représentant les séries.
     * <p>
     * Cette méthode crée une action spécifique pour chaque bouton de série généré dynamiquement.
     * Lorsqu'un bouton est cliqué, une boîte de dialogue s'affiche pour confirmer la sélection,
     * la fenêtre des séries est fermée, et un nouveau contrôleur de plateau est initialisé
     * pour gérer la série sélectionnée.
     * </p>
     * 
     * @param itemList une <code>Map</code> contenant les identifiants des séries en tant que clés 
     *                 et les boutons correspondants en tant que valeurs.
     */
    private void seriesListener(Map<Integer, JButton> itemList) {
        for (Map.Entry<Integer, JButton> itemSet : itemList.entrySet()) {
            JButton item = itemSet.getValue();
            item.addActionListener(e -> {
                JOptionPane.showMessageDialog(view.getSeriesFrame(),
                        "Vous avez sélectionné la série : " + item.getText());
                view.getSeriesFrame().dispose(); // Fermer la fenêtre des séries
                new PlateauController(itemSet.getKey());
            });
        }
    }
}
