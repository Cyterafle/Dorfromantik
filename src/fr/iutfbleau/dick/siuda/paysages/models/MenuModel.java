package fr.iutfbleau.dick.siuda.paysages.models;

import fr.iutfbleau.dick.siuda.paysages.views.MenuView;
import java.sql.*;
import java.util.Map;
import java.util.HashMap;

/**
 * La classe <code>MenuModel</code> représente le modèle dans l'architecture MVC
 * pour la gestion des données liées au menu de l'application.
 * <p>
 * Cette classe interagit avec une base de données pour récupérer les informations nécessaires,
 * telles que la liste des séries. Elle utilise une connexion à la base de données via la classe 
 * <code>Connexion</code> pour exécuter les requêtes SQL.
 * </p>
 * 
 * @version 1.0
 * @author Siuda Matéo
 * @author Dick Adrien
 */
public class MenuModel {

    /**
     * Connexion à la base de données.
     */
    private Connection cnx;

    /**
     * Constructeur de la classe <code>MenuModel</code>.
     * <p>
     * Initialise la connexion à la base de données en utilisant la classe <code>Connexion</code>.
     * </p>
     * 
     * @param view une instance de la classe <code>MenuView</code>, utilisée pour lier la vue au modèle.
     */
    public MenuModel(MenuView view) {
        cnx = Connexion.getInstance().getCnx();
    }

    /**
     * Récupère la liste des séries disponibles dans la base de données.
     * <p>
     * Cette méthode exécute une requête SQL pour obtenir les identifiants et noms des séries
     * depuis la table <code>Series</code>. Les résultats sont stockés dans une 
     * <code>Map</code> où la clé correspond à l'identifiant de la série (<code>idSerie</code>)
     * et la valeur au nom de la série (<code>nomSerie</code>).
     * </p>
     * 
     * @return une <code>Map</code> contenant les séries (id comme clé, nom comme valeur),
     *         ou <code>null</code> en cas d'erreur de connexion ou d'exécution de la requête.
     */
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
