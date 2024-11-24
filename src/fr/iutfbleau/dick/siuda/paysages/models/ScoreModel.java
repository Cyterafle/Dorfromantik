package fr.iutfbleau.dick.siuda.paysages.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class ScoreModel {
    private Connection cnx;
    public ScoreModel(){
        cnx = Connexion.getInstance().getCnx();
    }

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
