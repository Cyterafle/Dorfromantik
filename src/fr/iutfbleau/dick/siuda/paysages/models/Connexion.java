package fr.iutfbleau.dick.siuda.paysages.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * La classe <code>Connexion</code> gère la connexion à la base de données en suivant
 * le modèle de conception Singleton.
 * <p>
 * Cette classe assure qu'une seule instance de connexion est créée et partagée à travers
 * toute l'application, ce qui permet une gestion centralisée des interactions avec la base
 * de données.
 * </p>
 *
 * @version 1.0
 * @author Siuda Matéo
 * @author Dick Adrien
 */
public class Connexion {

    /**
     * L'unique instance de la classe Connexion (Singleton).
     */
    private static Connexion instance;

    /**
     * L'objet <code>Connection</code> représentant la connexion à la base de données.
     */
    private Connection cnx;

    /**
     * Constructeur privé de la classe <code>Connexion</code>.
     * <p>
     * Ce constructeur initialise le driver de la base de données et établit une connexion
     * à la base. Il est inaccessible depuis l'extérieur pour garantir l'utilisation du Singleton.
     * </p>
     */
    private Connexion() {
        try {
            // Chargement du driver JDBC MariaDB
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur de chargement du driver : " + e.getMessage());
        }
        try {
            // Établir la connexion à la base de données
            cnx = DriverManager.getConnection(
                    "jdbc:mariadb://dwarves.iut-fbleau.fr/siuda", 
                    "siuda", 
                    "Siuda77140"
            );
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
    }

    /**
     * Obtient l'unique instance de la classe <code>Connexion</code>.
     * <p>
     * Si aucune instance n'existe encore, une nouvelle est créée. Sinon, l'instance
     * existante est retournée.
     * </p>
     * 
     * @return L'instance unique de <code>Connexion</code>.
     */
    public static Connexion getInstance() {
        if (instance == null) {
            instance = new Connexion();
        }
        return instance;
    }

    /**
     * Retourne l'objet <code>Connection</code> utilisé pour interagir avec la base de données.
     * 
     * @return L'objet <code>Connection</code> représentant la connexion à la base de données.
     */
    public Connection getCnx() {
        return this.cnx;
    }

    /**
     * Ferme la connexion à la base de données.
     * <p>
     * Cette méthode doit être appelée pour libérer les ressources après que la connexion
     * n'est plus nécessaire.
     * </p>
     */
    public void fermeture() {
        try {
            if (cnx != null && !cnx.isClosed()) {
                cnx.close();
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
        }
    }
}
