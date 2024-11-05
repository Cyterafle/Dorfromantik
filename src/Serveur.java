import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Serveur {

    try {
        // Connexion à la base de données
        Connection cnx = DriverManager.getConnection("jdbc:mariadb://dwarves.iut-fbleau.fr/siuda", "siuda", "Siuda77140");

        // Requête SQL pour obtenir les points obtenus par le compétiteur dans d'autres pays
        String query = "SELECT p1.Pays AS Competiteur, p2.Pays AS Votant, v.Points " +
            "FROM Vote v " +
            "JOIN PaysDEV p1 ON v.IDCompétiteur = p1.ID " +
            "JOIN PaysDEV p2 ON v.IDVotants = p2.ID " +
            "WHERE p1.Pays = ?";

            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setString(1, paysCompetiteur);

            ResultSet rs = pst.executeQuery();

            int totalPoints = 0;
            boolean hasResults = false;

            System.out.println("Points obtenus par " + paysCompetiteur + ":");

            // Parcours des résultats et affichage formaté
        while (rs.next()) {
            String votant = rs.getString("Votant");
            int points = rs.getInt("Points");
            totalPoints += points;
            hasResults = true;

            System.out.printf("  %s\t%d\n", votant, points);
        }

        if (hasResults) {
            System.out.println("\t\t---");
            System.out.printf("  Total\t\t%d\n", totalPoints);
        } else {
            System.out.println("Aucun point obtenu pour ce pays.");
        }

        // Fermeture des ressources
        rs.close();
        pst.close();
        cnx.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
