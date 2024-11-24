package fr.iutfbleau.dick.siuda.paysages.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import fr.iutfbleau.dick.siuda.paysages.views.PlateauView;

public class PlateauModel {
    private Connection cnx;
    private PlateauView view;
    public PlateauModel(){
        cnx = Connexion.getInstance().getCnx();
    }

    public List<Tuile> recupererTuilesPourSerie(int idSerie) {
        String query = "SELECT tt.idTuile, tt.idTerrain, terrain.mer, terrain.pré, terrain.champs, terrain.foret, terrain.montagne " +
                       "FROM Tuiles t " +
                       "JOIN Tuile_Terrain tt ON t.idTuile = tt.idTuile " +
                       "JOIN Terrains terrain ON tt.idTerrain = terrain.idTerrain " +
                       "WHERE t.idSerie = ?";
    
        List<Tuile> tuiles = new ArrayList<>();
    
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, idSerie);
            ResultSet rs = pst.executeQuery();
    
    
            boolean hasResults = false;
            while (rs.next()) {
                int idTuile = rs.getInt("idTuile");
                int idTerrain = rs.getInt("idTerrain");
                int mer = rs.getInt("mer");
                int pré = rs.getInt("pré");
                int champs = rs.getInt("champs");
                int foret = rs.getInt("foret");
                int montagne = rs.getInt("montagne");
    
                // Ajouter la tuile à la liste
                Tuile tuile = new Tuile(idTuile, idTerrain, mer, pré, champs, foret, montagne);
                tuiles.add(tuile);
                hasResults = true;
            }
    
            if (!hasResults) {
                System.out.println("Aucune tuile trouvée pour cette série.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Collections.shuffle(tuiles, new Random());
    
        return tuiles;
    }
}
