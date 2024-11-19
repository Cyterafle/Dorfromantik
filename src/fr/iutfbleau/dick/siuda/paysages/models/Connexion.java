package fr.iutfbleau.dick.siuda.paysages.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe gérant la connexion à la base de données.
 *
 * Cette classe suit le modèle Singleton pour garantir une seule instance de
 * connexion.
 *
 */
public class Connexion {
    private static Connexion instance;
    private Connection cnx;

    /**
     * Constructeur privé de la classe Connexion.
     * Initialise la connexion à la base de données.
     */
    private Connexion() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        try {
            cnx = DriverManager.getConnection("jdbc:mariadb://dwarves.iut-fbleau.fr/siuda", "siuda", "Siuda77140");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Obtient l'instance unique de la classe Connexion.
     *
     * @return L'instance de Connexion.
     */
    public static Connexion getInstance() {
        if (instance == null)
            instance = new Connexion();
        return instance;
    }

    /**
     * Obtient la connexion à la base de données.
     *
     * @return La connexion à la base de données.
     */
    public Connection getCnx() {
        return this.cnx;
    }

    /**
     * Ferme la connexion à la base de données.
     */
    public void fermeture() {
        try {
            cnx.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
