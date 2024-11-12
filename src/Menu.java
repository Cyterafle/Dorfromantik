import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Menu {
    private static JFrame frame;
    private static CardLayout cardLayout;
    private static JPanel cardPanel;

    public static void main(String[] args) {
        frame = new JFrame("Menu");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        JPanel menuPanel = createMenuPanel();
        JPanel jouerPanel = createJouerPanel();
        JPanel reglesPanel = createReglesPanel();
        JPanel commandesPanel = createCommandesPanel();

        cardPanel.add(menuPanel, "Menu");
        cardPanel.add(jouerPanel, "Jouer");
        cardPanel.add(reglesPanel, "Règles");
        cardPanel.add(commandesPanel, "Commandes");

        cardLayout.show(cardPanel, "Menu");

        frame.add(cardPanel);

        frame.setVisible(true);
    }

    private static JPanel createMenuPanel() {
        JPanel panel = new JPanel(new GridBagLayout()); 
        GridBagConstraints gbc = new GridBagConstraints();
    
        int buttonWidth = 200;
        int buttonHeight = 50;
    
        gbc.anchor = GridBagConstraints.CENTER;
    
        gbc.insets = new Insets(10, 10, 10, 10);
    
        JButton jouerButton = new JButton("Jouer");
        jouerButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        gbc.gridx = 0;
        gbc.gridy = 0;
        jouerButton.addActionListener(e -> cardLayout.show(cardPanel, "Jouer"));
        panel.add(jouerButton, gbc);

        JButton reglesButton = new JButton("Règles");
        reglesButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        gbc.gridy = 1;
        reglesButton.addActionListener(e -> cardLayout.show(cardPanel, "Règles"));
        panel.add(reglesButton, gbc);

        JButton commandesButton = new JButton("Commandes");
        commandesButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        gbc.gridy = 2;
        commandesButton.addActionListener(e -> cardLayout.show(cardPanel, "Commandes"));
        panel.add(commandesButton, gbc);

        JButton quitterButton = new JButton("Quitter");
        quitterButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        gbc.gridy = 3;
        quitterButton.addActionListener(e -> System.exit(0));
        panel.add(quitterButton, gbc);

        return panel;
    }

    private static JPanel createJouerPanel() {
    JPanel panel = new JPanel(new GridBagLayout()); 
    GridBagConstraints gbc = new GridBagConstraints();
    panel.setAlignmentX(Component.CENTER_ALIGNMENT);

    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.anchor = GridBagConstraints.CENTER;

    JButton jouerPlateauButton = new JButton("Jouer un Plateau");
    jouerPlateauButton.setPreferredSize(new Dimension(250, 50));
    gbc.gridy = 1;
    jouerPlateauButton.addActionListener(e -> ouvrirFenetreSeries());
    panel.add(jouerPlateauButton, gbc);


    gbc.gridy = 2;
    panel.add(Box.createVerticalStrut(20), gbc);


    JButton retourButton = new JButton("Retour");
    retourButton.setPreferredSize(new Dimension(200, 50));
    gbc.gridy = 3;
    retourButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    retourButton.addActionListener(e -> cardLayout.show(cardPanel, "Menu"));
    panel.add(retourButton, gbc);

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
                    JOptionPane.showMessageDialog(seriesFrame, "Vous avez sélectionné la série : " + nomSerie);
                    seriesFrame.dispose(); // Fermer la fenêtre des séries
                    ouvrirFenetrePlateau(); // Ouvrir la fenêtre du plateau
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
        }
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
        JFrame plateauFrame = new JFrame("Dorfromantik - Plateau");
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
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        gbc.insets = new Insets(10, 10, 10, 10);
        
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
        reglesTextArea.setPreferredSize(new Dimension(600, 300));

        JScrollPane scrollPane = new JScrollPane(reglesTextArea);
        scrollPane.setBorder(null); 

        JButton retourButton = new JButton("Retour");
        retourButton.setPreferredSize(new Dimension(200, 50));
        retourButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        retourButton.addActionListener(e -> cardLayout.show(cardPanel, "Menu"));

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(scrollPane, gbc);

        gbc.gridy = 1;
        panel.add(retourButton, gbc);

        return panel;
    }

    private static JPanel createCommandesPanel() {
    JPanel panel = new JPanel(new GridBagLayout());  
    GridBagConstraints gbc = new GridBagConstraints();
    panel.setAlignmentX(Component.CENTER_ALIGNMENT);

    gbc.insets = new Insets(10, 10, 10, 10);

    gbc.gridx = 0;
    gbc.gridy = 0;
    panel.add(createImageLabel("res/arrow-right.png"), gbc);  
    gbc.gridx = 1;
    panel.add(createTextLabel("Flèche droite :", "Tourner l'hexagone vers la droite."), gbc);  

    gbc.gridx = 0;
    gbc.gridy = 1;
    panel.add(createImageLabel("res/arrow-left.png"), gbc);  
    gbc.gridx = 1;
    panel.add(createTextLabel("Flèche gauche :", "Tourner l'hexagone vers la gauche."), gbc);  
    gbc.gridx = 0;
    gbc.gridy = 2;
    panel.add(createImageLabel("res/LeftClick.png"), gbc);  
    gbc.gridx = 1;
    panel.add(createTextLabel("Clic gauche :", "Sélectionner un emplacement pour poser une tuile."), gbc); 

    gbc.gridx = 0;
    gbc.gridy = 3;
    panel.add(createImageLabel("res/RightClickMove.png"), gbc); 
    gbc.gridx = 1;
    panel.add(createTextLabel("Maintien du clic droit :", "Déplacer la vue dans la fenêtre."), gbc); 

    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.gridwidth = 2; 
    JButton retourButton = new JButton("Retour");
    retourButton.setPreferredSize(new Dimension(200, 50));
    retourButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    retourButton.addActionListener(e -> cardLayout.show(cardPanel, "Menu"));
    panel.add(retourButton, gbc);

    return panel;
}


private static JLabel createImageLabel(String imagePath) {
    JLabel imageLabel = new JLabel();
    try {
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);  
        imageLabel.setIcon(new ImageIcon(img));
    } catch (Exception e) {
        imageLabel.setText("[Image non trouvée]");  
    }
    return imageLabel;
}


private static JPanel createTextLabel(String titre, String description) {
    JPanel textPanel = new JPanel();
    textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
    textPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel titreLabel = new JLabel(titre);
    titreLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
    textPanel.add(titreLabel);

    JLabel descriptionLabel = new JLabel(description);
    descriptionLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
    textPanel.add(descriptionLabel);

    return textPanel;
}


}
