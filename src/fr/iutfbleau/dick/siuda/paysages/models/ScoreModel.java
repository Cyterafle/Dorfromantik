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
     * Liste contenant le top 10 des scores pour la série donnée
     */
    private List<Integer> scores;

    /**
     * Constructeur de la classe <code>ScoreModel</code>.
     * <p>
     * Initialise la connexion à la base de données via la classe <code>Connexion</code>.
     * </p>
     */
    public ScoreModel(int score) {
        cnx = Connexion.getInstance().getCnx();
        this.scores = new ArrayList<>();
        this.score = score;
    }

    /**
     * Récupère les scores pour une série donnée.
     * <p>
     * Cette méthode effectue une requête SQL pour obtenir les scores les plus élevés
     * associés à une série spécifique, triés par ordre décroissant.
     * </p>
     *
     * @param idSerie L'identifiant de la série dont les scores doivent être affichés.
     * @return Une liste des scores pour la série. Si aucun score n'est trouvé,
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
            while (rs.next()) {
                int score = rs.getInt("score");
                scores.add(score);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scores;
    }
    /**
     * Ajoute le score de la dernière partie jouée à la base de données
     * @param idSerie la série de tuiles posées dans la partie mentionnée plus haut
     * @param score le score obtenu en fin de partie
     */
    public void addScore(int idSerie, int score){
        String query = "INSERT INTO Scores(idSerie, score) VALUES (?,?)";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, idSerie);
            pst.setInt(2, score);
            pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getScore(){
        return score;
    }

    public void setScores(List<Integer> scores){
        this.scores = scores;
    }

    public List<Integer> getScores(){
        return scores;
    }

    public int getScoresSize(){
        return scores.size();
    }
}
