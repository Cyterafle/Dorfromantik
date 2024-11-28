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

/**
 * La classe <code>PlateauModel</code> gère les données du plateau de jeu.
 * <p>
 * Elle permet de récupérer les tuiles associées à une série depuis la base de données,
 * et fournit les outils nécessaires pour manipuler et accéder aux tuiles lors du jeu.
 * </p>
 *
 * @version 1.0
 * @author Siuda Matéo
 * @author Dick Adrien
 */
public class PlateauModel {

    /**
     * Connexion à la base de données.
     */
    private Connection cnx;

    /**
     * La vue associée au plateau de jeu.
     */
    private PlateauView view;

    /**
     * Constructeur de la classe <code>PlateauModel</code>.
     * <p>
     * Initialise la connexion à la base de données via la classe <code>Connexion</code>.
     * </p>
     */
    public PlateauModel() {
        cnx = Connexion.getInstance().getCnx();
    }

    /**
     * Récupère les tuiles associées à une série spécifique depuis la base de données.
     * <p>
     * Cette méthode effectue une requête SQL pour obtenir les informations des tuiles
     * liées à une série donnée. Les tuiles sont ensuite mélangées aléatoirement pour
     * préparer le jeu.
     * </p>
     *
     * @param idSerie L'identifiant de la série dont les tuiles doivent être récupérées.
     * @return Une liste de tuiles mélangées associées à la série.
     */
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
    
                // Crée une tuile et l'ajoute à la liste
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

        // Mélanger aléatoirement les tuiles
        Collections.shuffle(tuiles, new Random());
    
        return tuiles;
    }

    /**
     * Associe une vue au modèle du plateau.
     *
     * @param view La vue à associer au modèle.
     */
    public void setView(PlateauView view) {
        this.view = view;
    }

    /**
     * Retourne la prochaine tuile active sur le plateau.
     * <p>
     * Cette méthode permet d'accéder à la tuile qui sera utilisée lors de la prochaine
     * action de l'utilisateur.
     * </p>
     *
     * @return La prochaine tuile active sur le plateau.
     */
    public Tuile getNexTuile() {
        return view.getPanel().getNextTuile();
    }
}
