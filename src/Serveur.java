import java.sql.*;
import javax.swing.*;
import java.awt.*;

public class Serveur {

    public static void main(String[] args) {
        // Connexion à la base de données
        try (Connection cnx = DriverManager.getConnection("jdbc:mariadb://dwarves.iut-fbleau.fr/siuda", "siuda", "Siuda77140")) {

            // Appel des différentes fonctions selon les besoins
            afficherScoresPourSerie(cnx, 1); // Afficher les scores pour une série donnée (ex : série avec ID 1)

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Fonction pour afficher toutes les séries disponibles sous forme de boutons
    public static void afficherSeries(Connection cnx, JPanel buttonsPanel, JFrame parentFrame, Runnable onSelection) {
        String query = "SELECT idSerie, nomSerie FROM Series";

        try (PreparedStatement pst = cnx.prepareStatement(query);
            ResultSet rs = pst.executeQuery()) {

            boolean hasResults = false;

            while (rs.next()) {
                String nomSerie = rs.getString("nomSerie");
                JButton seriesButton = new JButton(nomSerie);
                seriesButton.setAlignmentX(Component.CENTER_ALIGNMENT);

                // Ajouter un ActionListener pour gérer le clic sur le bouton
                seriesButton.addActionListener(e -> {
                    JOptionPane.showMessageDialog(parentFrame, "Vous avez sélectionné la série : " + nomSerie);
                    parentFrame.dispose(); // Fermer la fenêtre parent
                    onSelection.run();    // Exécuter l'action après la sélection
                });

                buttonsPanel.add(seriesButton);
                buttonsPanel.add(Box.createVerticalStrut(10)); // Espacement entre les boutons
                hasResults = true;
            }

            if (!hasResults) {
                JLabel noSeriesLabel = new JLabel("Aucune série disponible.");
                noSeriesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                buttonsPanel.add(noSeriesLabel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    

    // Fonction pour afficher les scores pour une série donnée
    public static void afficherScoresPourSerie(Connection cnx, int idSerie) {
        String query = "SELECT s.idScore, s.score " +
                       "FROM Scores s " +
                       "WHERE s.idSerie = ? " +
                       "ORDER BY s.score DESC";

        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, idSerie);
            ResultSet rs = pst.executeQuery();

            System.out.println("Scores pour la série ID " + idSerie + ":");

            boolean hasResults = false;
            while (rs.next()) {
                int idScore = rs.getInt("idScore");
                int score = rs.getInt("score");
                System.out.printf("ID: %d, Score: %d\n", idScore, score);
                hasResults = true;
            }

            if (!hasResults) {
                System.out.println("Aucun score disponible pour cette série.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
