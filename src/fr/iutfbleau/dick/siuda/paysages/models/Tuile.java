package fr.iutfbleau.dick.siuda.paysages.models;

public class Tuile {
    private int idTuile;
    private int idTerrain;
    private int mer;
    private int pré;
    private int champs;
    private int foret;
    private int montagne;
    private int[] couleurTerrain;

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
    }

    @Override
    public String toString() {
        return String.format("Tuile [ID: %d, Terrain ID: %d, Mer: %d, Pré: %d, Champs: %d, Forêt: %d, Montagne: %d]",
                idTuile, idTerrain, mer, pré, champs, foret, montagne);
    }


    public int[] getRepartitionTerrains(){
        return couleurTerrain;
    }

}
