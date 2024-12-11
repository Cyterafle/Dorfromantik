package fr.iutfbleau.dick.siuda.paysages.views;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import fr.iutfbleau.dick.siuda.paysages.models.ScoreModel;

import java.util.List;

/**
 * La classe <code>Scoreboard</code> représente une fenêtre affichant les 10 meilleurs scores pour une série donnée.
 * <p>
 * Cette classe utilise un tableau pour afficher les scores, avec un rendu personnalisé pour les rangs, 
 * et un bouton permettant de retourner à l'écran précédent.
 * </p>
 *
 * @version 1.0
 * @author Siuda Matéo
 * @author Dick Adrien
 */
public class Scoreboard extends JFrame {

    /**
     * Bouton "Retour" permettant de revenir à l'écran précédent.
     */
    private JButton backButton;

    /**
     * Constructeur de la classe <code>Scoreboard</code>.
     * <p>
     * Initialise une fenêtre avec un tableau affichant les 10 meilleurs scores 
     * pour une série donnée, ainsi qu'un bouton pour revenir à l'écran précédent.
     * </p>
     *
     * @param idSerie L'identifiant de la série pour laquelle les scores sont affichés.
     * @param scores Une liste des scores à afficher (au maximum 10 scores).
     */
    public Scoreboard(int idSerie, ScoreModel model) {
        setTitle("Classement des dix meilleurs scores");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Titre
        JLabel titleLabel = new JLabel("Classement pour la série " + idSerie);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        // Données pour le tableau
        String[] columnNames = {"RANG", "SCORE"};
        Object[][] data = new Object[10][2];
        for (int i = 0; i < 10; i++) {
            data[i][0] = (i + 1) + getRankSuffix(i + 1);
            if (model.getScoresSize() > i)
                data[i][1] = model.getScores().get(i).toString();
            else
                data[i][1] = "0";
        }

        // Création du tableau
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        // Personnalisation des cellules avec un renderer
        table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());

        // Personnalisation de l'entête
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setReorderingAllowed(false);

        // Ajout du tableau et d'un texte qui indique la position du score réalisé
        JPanel tableau = new JPanel(new BorderLayout());
        String score = String.format("Score actuel : %d, vous êtes donc %s", model.getScore(), getRankString(model.getScore(), model.getScores()));
        JTextArea scores = new JTextArea(score);
        scores.setFont(new Font("Arial", Font.PLAIN, 14));
        tableau.add(table, BorderLayout.CENTER);
        tableau.add(scores, BorderLayout.SOUTH);
        add(tableau, BorderLayout.CENTER);
        
        // Bouton "Retour"
        backButton = new JButton("Retour");
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Ajoute le suffixe approprié au rang (par exemple, "1er", "2ème", "3ème").
     *
     * @param rank Le numéro du rang.
     * @return Une chaîne de caractères représentant le rang avec le suffixe.
     */
    private String getRankSuffix(int rank) {
        switch (rank) {
            case 1: return "er";
            default: return "ème";
        }
    }

    private String getRankString(int score, List<Integer> listeScores){
        int rang = listeScores.indexOf(score) + 1;
        String RangEtSuffix = rang + getRankSuffix(rang);
        return String.format("%s sur %s", RangEtSuffix, listeScores.size());
    }

    /**
     * Retourne le bouton "Retour".
     * <p>
     * Ce bouton peut être utilisé pour ajouter un gestionnaire d'événements permettant
     * de revenir à une autre vue.
     * </p>
     *
     * @return Le bouton "Retour".
     */
    public JButton getBackButton() {
        return backButton;
    }
}
