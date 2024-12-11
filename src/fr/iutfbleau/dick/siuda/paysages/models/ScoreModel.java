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
     * Identifiant de la partie précédemment terminée
     */
    private int idSerie;

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
    public ScoreModel(int score, int idSerie) {
        cnx = Connexion.getInstance().getCnx();
        addScore(idSerie, score);
        this.scores = afficherScoresPourSerie(idSerie);
        this.score = score;
        this.idSerie = idSerie;
    }

    /**
     * Récupère jusqu'aux 10 meilleurs scores pour une série donnée.
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

    /**
     * Récupère le nombre de participants de la série
     * @return un entier représentant le nombre de participants
     */
    public int getTotalParticipants(){
        String query = "SELECT COUNT(*) AS players FROM Scores WHERE idSerie = ?";
        int participants = 0;
        try (PreparedStatement pst = cnx.prepareStatement(query)){
            pst.setInt(1, idSerie);
            ResultSet rst = pst.executeQuery();
            if (rst.next())
                participants = rst.getInt(1);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return participants;
    }

    /**
     * Récupère le rang d'un joueur selon son score
     * @return un entier représentant son rang
     */
    public int getJoueurRang(){
        String query="SELECT COUNT(*) + 1 AS rang FROM Scores WHERE score > ? and idSerie = ?";
        int rang = 0;
        try (PreparedStatement pst = cnx.prepareStatement(query)){
            pst.setInt(1, score);
            pst.setInt(2, idSerie);
            ResultSet rst = pst.executeQuery();
            if (rst.next())
                rang = rst.getInt(1);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return rang;
    }

    /**
     * Getter renvoyant le score récupéré de la partie fraîchement terminé
     * @return un entier représentant le score
     */
    public int getScore(){
        return score;
    }

    /**
     * Getter renvoyant l'identifiant récupéré de la partie fraîchement terminé
     * @return un entier représentant l'identifiant
     */
    public int getIdSerie(){
        return idSerie;
    }


    /**
     * Getter renvoyant la liste des meilleurs scores
     * @return la liste chaînée correspondante
     */
    public List<Integer> getScores(){
        return scores;
    }


    /**
     * Getter envoyant la taille de la liste des meilleurs scores
     * @return
     */
    public int getScoresSize(){
        return scores.size();
    }
}
