package fr.iutfbleau.dick.siuda.paysages.views;

import java.awt.*;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;

public class MenuView {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JButton jouerPlateauButton;
    private JButton reglesRetourButton;
    private JButton commandesRetourButton;
    private JFrame seriesFrame;

    public MenuView() {
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

    private JPanel createMenuPanel() {
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

    private JPanel createJouerPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        jouerPlateauButton = new JButton("Jouer un Plateau");
        jouerPlateauButton.setPreferredSize(new Dimension(250, 50));
        gbc.gridy = 1;
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

    public Map<Integer, JButton> ouvrirFenetreSeries(Map<Integer, String> series) {
        seriesFrame = new JFrame("Séries Disponibles");
        Map<Integer, JButton> seriesMap = new HashMap<>();
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

        for (Map.Entry<Integer, String> item : series.entrySet()) {
            JButton seriesButton = new JButton(item.getValue());
            seriesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            buttonsPanel.add(seriesButton);
            buttonsPanel.add(Box.createVerticalStrut(10)); // Espacement entre les boutons
            seriesMap.put(item.getKey(), seriesButton);
        }

        JScrollPane scrollPane = new JScrollPane(buttonsPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(scrollPane);

        seriesFrame.add(mainPanel, BorderLayout.CENTER);
        seriesFrame.setVisible(true);
        return seriesMap;
    }

    private JPanel createReglesPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        gbc.insets = new Insets(10, 10, 10, 10);

        JTextArea reglesTextArea = new JTextArea(
                "Voici les règles du jeu :\n\n" +
                        "Dans Dorfromantik, vous devez créer un paysage harmonieux en posant des tuiles hexagonales représentant différents terrains :\n"
                        +
                        "mer, champ, pré, forêt et montagne. Chaque tuile peut contenir un ou deux types de terrains.\n\n"
                        +
                        "À chaque tour, vous placez une tuile adjacente à au moins une tuile déjà posée.\n\n" +
                        "La partie se termine après avoir posé 50 tuiles.\n\n" +
                        "Les points sont calculés en formant des poches de terrain :\n" +
                        "une poche est un groupe de tuiles connectées par un même type de terrain.\n" +
                        "Plus une poche contient de tuiles, plus elle rapporte de points.");
        reglesTextArea.setEditable(false);
        reglesTextArea.setFocusable(false);
        reglesTextArea.setOpaque(false);
        reglesTextArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        reglesTextArea.setLineWrap(true);
        reglesTextArea.setWrapStyleWord(true);
        reglesTextArea.setPreferredSize(new Dimension(600, 300));

        JScrollPane scrollPane = new JScrollPane(reglesTextArea);
        scrollPane.setBorder(null);

        reglesRetourButton = new JButton("Retour");
        reglesRetourButton.setPreferredSize(new Dimension(200, 50));
        reglesRetourButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(scrollPane, gbc);

        gbc.gridy = 1;
        panel.add(reglesRetourButton, gbc);

        return panel;
    }

    private JPanel createCommandesPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        try {
            panel.add(createImageLabel(MenuView.class.getResource("/res/arrow-right.png")), gbc);
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        }
        gbc.gridx = 1;
        panel.add(createTextLabel("Flèche droite :", "Tourner l'hexagone vers la droite."), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        try {
            panel.add(createImageLabel(MenuView.class.getResource("/res/arrow-left.png")), gbc);
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        }
        gbc.gridx = 1;
        panel.add(createTextLabel("Flèche gauche :", "Tourner l'hexagone vers la gauche."), gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        try {
            panel.add(createImageLabel(MenuView.class.getResource("/res/LeftClick.png")), gbc);
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        }
        gbc.gridx = 1;
        panel.add(createTextLabel("Clic gauche :", "Sélectionner un emplacement pour poser une tuile."), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        try {
            panel.add(createImageLabel(MenuView.class.getResource("/res/RightClickMove.png")), gbc);
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        }
        gbc.gridx = 1;
        panel.add(createTextLabel("Maintien du clic droit :", "Déplacer la vue dans la fenêtre."), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        commandesRetourButton = new JButton("Retour");
        commandesRetourButton.setPreferredSize(new Dimension(200, 50));
        commandesRetourButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(commandesRetourButton, gbc);

        return panel;
    }

    private JLabel createImageLabel(URL imagePath) {
        JLabel imageLabel = new JLabel();
        try {
            Image img = ImageIO.read(imagePath).getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            imageLabel.setText("[Image non trouvée]");
        }
        return imageLabel;
    }

    private JPanel createTextLabel(String titre, String description) {
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

    public JButton getJouerPlateauButton() {
        return jouerPlateauButton;
    }

    public JButton getCommandesRetourButton() {
        return commandesRetourButton;
    }

    public JButton getReglesRetourButton() {
        return reglesRetourButton;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JPanel getCardPanel() {
        return cardPanel;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JFrame getSeriesFrame() {
        return seriesFrame;
    }
}
