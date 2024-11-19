package fr.iutfbleau.dick.siuda.paysages.models;

import fr.iutfbleau.dick.siuda.paysages.views.MenuView;
import java.sql.*;

import java.util.Map;
import java.util.HashMap;

public class MenuModel {
    private Connection cnx;
    public MenuModel(MenuView view){
        cnx = Connexion.getInstance().getCnx();
    }

    public Map<Integer, String> listeSeries() {
        String query = "SELECT idSerie, nomSerie FROM Series";
        try (PreparedStatement pst = cnx.prepareStatement(query);
            ResultSet rs = pst.executeQuery()) {
            Map<Integer, String> series = new HashMap<>();
            while (rs.next()) {
                String nomSerie = rs.getString("nomSerie");
                int idSerie = rs.getInt("idSerie");
                series.put(idSerie, nomSerie);
            }
            return series;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}