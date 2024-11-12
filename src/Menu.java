import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Menu {
    private static JFrame frame;
    private static CardLayout cardLayout;
    private static JPanel cardPanel;

    public static void main(String[] args) {
        frame = new JFrame("Menu");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        JPanel menuPanel = createMenuPanel();
        JPanel jouerPanel = createJouerPanel();
        JPanel reglesPanel = createReglesPanel();
        JPanel commandesPanel = createCommandesPanel(); // Panel for game commands

        cardPanel.add(menuPanel, "Menu");
        cardPanel.add(jouerPanel, "Jouer");
        cardPanel.add(reglesPanel, "Règles");
        cardPanel.add(commandesPanel, "Commandes"); // Adding the new panel

        cardLayout.show(cardPanel, "Menu");

        frame.add(cardPanel);

        frame.setVisible(true);
    }

    private static JPanel createMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton jouerButton = new JButton("Jouer");
        jouerButton.setPreferredSize(new Dimension(200, 50));
        jouerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        jouerButton.addActionListener(e -> cardLayout.show(cardPanel, "Jouer"));

        JButton reglesButton = new JButton("Règles");
        reglesButton.setPreferredSize(new Dimension(200, 50));
        reglesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        reglesButton.addActionListener(e -> cardLayout.show(cardPanel, "Règles"));

        JButton commandesButton = new JButton("Commandes"); // New button for "Commandes"
        commandesButton.setPreferredSize(new Dimension(200, 50));
        commandesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        commandesButton.addActionListener(e -> cardLayout.show(cardPanel, "Commandes"));

        JButton quitterButton = new JButton("Quitter");
        quitterButton.setPreferredSize(new Dimension(200, 50));
        quitterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitterButton.addActionListener(e -> System.exit(0));

        panel.add(Box.createVerticalStrut(20));
        panel.add(jouerButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(reglesButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(commandesButton); // Adding the new button
        panel.add(Box.createVerticalStrut(20));
        panel.add(quitterButton);
        panel.add(Box.createVerticalStrut(20));

        return panel;
    }

    private static JPanel createJouerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton creerPlateauButton = new JButton("Créer un Plateau");
        creerPlateauButton.setPreferredSize(new Dimension(250, 50));
        creerPlateauButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        creerPlateauButton.addActionListener(e -> ouvrirFenetrePlateau());

        JButton jouerPlateauButton = new JButton("Jouer un Plateau");
        jouerPlateauButton.setPreferredSize(new Dimension(250, 50));
        jouerPlateauButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        jouerPlateauButton.addActionListener(e -> ouvrirFenetreSeries());

        panel.add(Box.createVerticalStrut(20));
        panel.add(jouerPlateauButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(creerPlateauButton);
        panel.add(Box.createVerticalStrut(20));

        return panel;
    }

    private static void ouvrirFenetreSeries() {
        JFrame seriesFrame = new JFrame("Séries Disponibles");
        seriesFrame.setSize(400, 500);
        seriesFrame.setLocationRelativeTo(null);
        seriesFrame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Choisissez une série");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(label);
        mainPanel.add(Box.createVerticalStrut(20));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        try (Connection cnx = DriverManager.getConnection("jdbc:mariadb://dwarves.iut-fbleau.fr/siuda", "siuda", "Siuda77140")) {
            Serveur.afficherSeries(cnx, buttonsPanel);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(buttonsPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(scrollPane);

        seriesFrame.add(mainPanel, BorderLayout.CENTER);
        seriesFrame.setVisible(true);
    }

    private static void ouvrirFenetrePlateau() {
        JFrame plateauFrame = new JFrame("Dorfromantik - Création d'un Plateau");
        Plateau hexGrid = new Plateau();

        int preferredWidth = (2 * Plateau.BORDER_HEXAGONS + 1) * (int) (Plateau.HEX_SIZE * 3 / 2);
        int preferredHeight = (2 * Plateau.BORDER_HEXAGONS + 1) * (int) (Math.sqrt(3) * Plateau.HEX_SIZE);
        hexGrid.setPreferredSize(new Dimension(preferredWidth, preferredHeight));

        JScrollPane scrollPane = new JScrollPane(hexGrid);

        int centerX = (preferredWidth / 2) - (800 / 2);
        int centerY = (preferredHeight / 2) - (600 / 2);
        scrollPane.getViewport().setViewPosition(new Point(centerX, centerY));

        plateauFrame.add(scrollPane);
        plateauFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        plateauFrame.setLocationRelativeTo(null);
        plateauFrame.setVisible(true);
    }

    private static JPanel createReglesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea reglesTextArea = new JTextArea(
            "Voici les règles du jeu :\n\n" +
            "Dans Dorfromantik, vous devez créer un paysage harmonieux en posant des tuiles hexagonales représentant différents terrains :\n" +
            "mer, champ, pré, forêt et montagne. Chaque tuile peut contenir un ou deux types de terrains.\n\n" +
            "À chaque tour, vous placez une tuile adjacente à au moins une tuile déjà posée.\n\n" +
            "La partie se termine après avoir posé 50 tuiles.\n\n" +
            "Les points sont calculés en formant des poches de terrain :\n" +
            "une poche est un groupe de tuiles connectées par un même type de terrain.\n" +
            "Plus une poche contient de tuiles, plus elle rapporte de points."
        );
        reglesTextArea.setEditable(false);
        reglesTextArea.setFocusable(false);
        reglesTextArea.setOpaque(false);
        reglesTextArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        reglesTextArea.setLineWrap(true);
        reglesTextArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(reglesTextArea);
        scrollPane.setBorder(null); 

        JButton retourButton = new JButton("Retour");
        retourButton.setPreferredSize(new Dimension(200, 50));
        retourButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        retourButton.addActionListener(e -> cardLayout.show(cardPanel, "Menu"));

        panel.add(Box.createVerticalStrut(20));
        panel.add(scrollPane);
        panel.add(Box.createVerticalStrut(20));
        panel.add(retourButton);
        panel.add(Box.createVerticalStrut(20));

        return panel;
    }

    private static JPanel createCommandesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea commandesTextArea = new JTextArea(
            "Commandes de jeu :\n\n" +
            "Flèche droite : fait pivoter l'hexagone de 1/6 de tour vers la droite.\n" +
            "Flèche gauche : fait pivoter l'hexagone de 1/6 de tour vers la gauche.\n" +
            "Clic gauche : sélectionne un emplacement pour poser une tuile.\n" +
            "Maintien du clic droit : permet de déplacer la vue dans la fenêtre."
        );
        commandesTextArea.setEditable(false);
        commandesTextArea.setFocusable(false);
        commandesTextArea.setOpaque(false);
        commandesTextArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        commandesTextArea.setLineWrap(true);
        commandesTextArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(commandesTextArea);
        scrollPane.setBorder(null); 

        JButton retourButton = new JButton("Retour");
        retourButton.setPreferredSize(new Dimension(200, 50));
        retourButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        retourButton.addActionListener(e -> cardLayout.show(cardPanel, "Menu"));

        panel.add(Box.createVerticalStrut(20));
        panel.add(scrollPane);
        panel.add(Box.createVerticalStrut(20));
        panel.add(retourButton);
        panel.add(Box.createVerticalStrut(20));

        return panel;
    }
}
