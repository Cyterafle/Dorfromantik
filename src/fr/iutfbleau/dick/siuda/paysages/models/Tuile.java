package fr.iutfbleau.dick.siuda.paysages.models;

import java.util.List;

/**
 * La classe <code>Tuile</code> représente une tuile du jeu, caractérisée par un identifiant, 
 * un type de terrain principal, et une répartition des terrains adjacents.
 * <p>
 * Chaque tuile contient des informations sur les différents types de terrains 
 * (mer, pré, champs, forêt, montagne) et leur répartition.
 * </p>
 *
 * @version 1.0
 * @author Siuda Matéo
 * @author Dick Adrien
 */
public class Tuile {

    /**
     * L'identifiant unique de la tuile.
     */
    private int idTuile;

    /**
     * L'identifiant du terrain principal de la tuile.
     */
    private int idTerrain;

    /**
     * La répartition de la mer sur la tuile.
     */
    private int mer;

    /**
     * La répartition des prés sur la tuile.
     */
    private int pré;

    /**
     * La répartition des champs sur la tuile.
     */
    private int champs;

    /**
     * La répartition des forêts sur la tuile.
     */
    private int foret;

    /**
     * La répartition des montagnes sur la tuile.
     */
    private int montagne;

    /**
     * Tableau décrivant la répartition des terrains sur les triangles composant la tuile.
     */
    private Terrains[] repartitionTerrain;


    private List<Tuile> voisins;

    /**
     * Constructeur de la classe <code>Tuile</code>.
     * <p>
     * Initialise une tuile avec les informations fournies sur son identifiant, 
     * son type de terrain principal, et la répartition des terrains.
     * </p>
     *
     * @param idTuile L'identifiant unique de la tuile.
     * @param idTerrain L'identifiant du terrain principal.
     * @param mer La répartition de la mer sur la tuile.
     * @param pré La répartition des prés sur la tuile.
     * @param champs La répartition des champs sur la tuile.
     * @param foret La répartition des forêts sur la tuile.
     * @param montagne La répartition des montagnes sur la tuile.
     */
    public Tuile(int idTuile, int idTerrain, int mer, int pré, int champs, int foret, int montagne) {
        this.idTuile = idTuile;
        this.idTerrain = idTerrain;
        this.mer = mer;
        this.pré = pré;
        this.champs = champs;
        this.foret = foret;
        this.montagne = montagne;
        int[] terrains = {mer, pré, champs, foret, montagne};
        setRepartitionTerrains(terrains);
    }

    /**
     * Retourne une représentation textuelle de la tuile.
     * <p>
     * Cette méthode fournit des informations détaillées sur l'identifiant 
     * et la répartition des terrains de la tuile.
     * </p>
     *
     * @return Une chaîne de caractères décrivant la tuile.
     */
    @Override
    public String toString() {
        return String.format("Tuile [ID: %d, Terrain ID: %d, Mer: %d, Pré: %d, Champs: %d, Forêt: %d, Montagne: %d]",
                idTuile, idTerrain, mer, pré, champs, foret, montagne);
    }


    /**
     * Méthode remplissant la liste décrivant la répartition des terrains à partir des données de la base de données
     * @param terrains les données brutes de la base de données
     */
    private void setRepartitionTerrains(int[] terrains){
        repartitionTerrain = new Terrains[6];
        int slice = 0, j = 0, lastPoint = -1;
        for (int i = 0; i < 6; ++i){
            if (slice == 0){
                while (terrains[j] == 0 | j == lastPoint)
                    ++j;
                slice = terrains[j];
                lastPoint = j;
                
            }
            repartitionTerrain[i] = Terrains.values()[j];
            --slice;
        }
    }


    /**
     * Méthode qui renvoie la répartition des terrains sur la tuile
     * @return une liste de terrains
     */
    public Terrains[] getRepartitionTerrains(){
        return repartitionTerrain;
    }

    /**
     * Renvoie le type de terrain situé à l'indive i
     * @param i l'indice concerné
     * @return le type de terrain associé
     */
    public Terrains getTerrainAt(int i){
        return repartitionTerrain[i];
    }


    /**
     * Méthode qui recréé la liste décrivant la répartition des terrains
     * @param decalage qui indique si la liste doit être décalée à droite ou à gauche
     */
    public void setOrientation(char decalage){
        Terrains[] newCouleurs = new Terrains[6];
        int offset;
        for (int i = 0; i < 6; ++i){
            if (decalage == '-'){
                offset = (6 + (i - 1)) %6;
            } else {
                offset = (6 + (i + 1)) %6;
            }
            newCouleurs[offset] = repartitionTerrain[i];
        }
        repartitionTerrain = newCouleurs;
    }
}
