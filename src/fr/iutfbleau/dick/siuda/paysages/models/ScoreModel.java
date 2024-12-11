package fr.iutfbleau.dick.siuda.paysages.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 * La classe <code>ScoreModel</code> gère l'accès aux données des scores dans la base de données.
 * <p>
 * Cette classe permet de récupérer les scores les plus élevés pour une série donnée,
 * en les triant par ordre décroissant.
 * </p>
 *
 * @version 1.0
 * @author Siuda Matéo
 * @author Dick Adrien
 */
public class ScoreModel {

    /**
     * Connexion à la base de données.
     */
    private Connection cnx;

    /**
     * Score de la partie précédemment terminée
     */
    private int score;

    /**
     * Constructeur de la classe <code>ScoreModel</code>.
     * <p>
     * Initialise la connexion à la base de données via la classe <code>Connexion</code>.
     * </p>
     */
    public ScoreModel(int score) {
        cnx = Connexion.getInstance().getCnx();
        this.score = score;
    }

    /**
     * Récupère les 10 meilleurs scores pour une série donnée.
     * <p>
     * Cette méthode effectue une requête SQL pour obtenir les scores les plus élevés
     * associés à une série spécifique, triés par ordre décroissant.
     * </p>
     *
     * @param idSerie L'identifiant de la série dont les scores doivent être affichés.
     * @return Une liste des 10 meilleurs scores pour la série. Si aucun score n'est trouvé,
     *         la liste sera vide.
     */
    public List<Integer> afficherScoresPourSerie(int idSerie) {
        String query = "SELECT s.score " +
                       "FROM Scores s " +
                       "WHERE s.idSerie = ? " +
                       "ORDER BY s.score DESC " +
                       "LIMIT 10";

        List<Integer> scores = new ArrayList<>();

        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, idSerie);
            ResultSet rs = pst.executeQuery();

            boolean hasResults = false;
            while (rs.next()) {
                int score = rs.getInt("score");
                scores.add(score);
                hasResults = true;
            }

            if (!hasResults) {
                System.out.println("Aucun score disponible pour cette série.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scores;
    }
}
