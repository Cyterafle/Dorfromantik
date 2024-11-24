package fr.iutfbleau.dick.siuda.paysages.models;

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
     * Tableau contenant la répartition des différents types de terrains.
     */
    private int[] couleurTerrain;

    public int orientation;

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
        this.couleurTerrain = terrains;
        this.orientation = 0;
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
     * Retourne la répartition des différents types de terrains sur la tuile.
     *
     * @return Un tableau d'entiers représentant la répartition des terrains.
     *         L'ordre est le suivant : [mer, pré, champs, forêt, montagne].
     */
    public int[] getRepartitionTerrains() {
        return couleurTerrain;
    }

    public void setOrientation(char decalage){
        switch (decalage) {
            case '-':
                orientation--;
                break;
        
            default:
                orientation++;
                break;
        }
    }

    public int getOrientation(){
        return orientation;
    }
}
