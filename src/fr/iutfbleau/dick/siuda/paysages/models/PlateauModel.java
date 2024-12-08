package fr.iutfbleau.dick.siuda.paysages.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

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
     * La liste des tuiles de la partie courante
     */
    private List<Tuile> tuiles;

    /**
     * Indice de la tuile actuellement active.
     */
    private int currentTuile;

     /**
     * L'identifiant de la série actuellement en cours de jeu.
     */
    private int idSerie;

    /**
     * Le Score de la partie courante
     */
    private int score;

        /**
     * Un dictionnaire permettant de stocker une liste de poches en fonction du terrain
     */
    private Map<Terrains, List<Set<Tuile>>> poches;

    /**
     * Constructeur de la classe <code>PlateauModel</code>.
     * <p>
     * Initialise la connexion à la base de données via la classe <code>Connexion</code>.
     * </p>
     * @param idSerie le numéro de la série sélectionnée
     */
    public PlateauModel(int idSerie) {
        cnx = Connexion.getInstance().getCnx();
        tuiles = recupererTuilesPourSerie(idSerie);
        this.currentTuile = 0;
        this.score = 0;
        this.idSerie = idSerie;
        this.poches = new HashMap<>();
        for (Terrains terrain : Terrains.values()) {
            poches.put(terrain, new ArrayList<>());
        }
        ajouterTuile(tuiles.get(currentTuile));
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
    public Tuile getNextTuile(){
        if (currentTuile <= 48)
            return tuiles.get(currentTuile+1);
        return null;
    }

    /**
     * Permet de mettre en mémoire la liste des tuiles de la série en cours
     * @param list une List<E> de tuiles correspondant à celle de la série donnée en argument au constructeur
     */
    public void setTuiles(List<Tuile> list){
        tuiles = list;
    }

    public List<Tuile> getTuiles(){
        return tuiles;
    }

    public int getCurrentTuile(){
        return currentTuile;
    }

    public void incrementCurrentTuile(){
        ++currentTuile;
    }

    public int getIdSerie(){
        return idSerie;
    }

    public int getScore(){
        return score;
    }

    public void rechercheVoisins(){
        for (int i = 0; i < tuiles.size(); ++i){
            Tuile current = tuiles.get(i);
            if (current.getCenterPoint() != null)
                current.rechercheVoisins(tuiles);
            else
                return;
        }
    }

    public void calculerScore() {
        int score = 0;
        for (Map.Entry<Terrains, List<Set<Tuile>>> entry : poches.entrySet()) {
            for (Set<Tuile> poche : entry.getValue()) {
                int taillePoche = poche.size();
                score += taillePoche * taillePoche;
            }
        }
        this.score = score;
    }

    private void ajouterTuile(Tuile tuile) {
        for (int i = 0; i < 6; i++) { // Parcourir les côtés de la tuile
            Terrains terrain = tuile.getTerrainAt(i); // Terrain du côté i
            Tuile voisin = tuile.getVoisin(i); // Tuile voisine du côté i (peut être null)

            if (voisin != null && voisin.getTerrainAt((i + 3) % 6) == terrain) {
                // Connecté au même terrain
                Set<Tuile> pocheTrouvee = trouverPoche(terrain, voisin);
                if (pocheTrouvee != null) {
                    pocheTrouvee.add(tuile);
                } else {
                    // Ajouter à une nouvelle poche si pas connectée
                    Set<Tuile> nouvellePoche = new HashSet<>();
                    nouvellePoche.add(tuile);
                    poches.get(terrain).add(nouvellePoche);
                }
            } else {
                // Nouveau terrain isolé
                Set<Tuile> nouvellePoche = new HashSet<>();
                nouvellePoche.add(tuile);
                poches.get(terrain).add(nouvellePoche);
            }
        }
    }

    private Set<Tuile> trouverPoche(Terrains terrain, Tuile tuile) {
        for (Set<Tuile> poche : poches.get(terrain)) {
            if (poche.contains(tuile)) {
                return poche;
            }
        }
        return null;
    }

    public void ajouterTuileCourante(){
        ajouterTuile(tuiles.get(currentTuile));
    }
}
