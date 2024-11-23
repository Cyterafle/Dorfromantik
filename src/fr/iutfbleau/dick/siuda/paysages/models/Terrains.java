package fr.iutfbleau.dick.siuda.paysages.models;
import java.awt.Color;

public enum Terrains {
    MER (Color.BLUE),
    PRE (new Color(144,238,144)),
    CHAMPS(Color.YELLOW),
    FORET(new Color(0, 51, 0)),
    MONTAGNE(Color.GRAY);
    private Color color;
    private Terrains (Color color){
        this.color = color;
    }

    public Color getColor(){
        return color;
    }
    
}
