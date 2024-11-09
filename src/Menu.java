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
        // Créer la fenêtre principale
        frame = new JFrame("Menu");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Centrer la fenêtre sur l'écran
        frame.setLocationRelativeTo(null);  // Cela place la fenêtre au centre de l'écran

        // Créer le gestionnaire de mise en page CardLayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Ajouter les différentes cartes (panneaux)
        JPanel menuPanel = createMenuPanel();
        JPanel jouerPanel = createJouerPanel();
        JPanel reglesPanel = createReglesPanel();

        // Ajouter les panneaux à la carte
        cardPanel.add(menuPanel, "Menu");
        cardPanel.add(jouerPanel, "Jouer");
        cardPanel.add(reglesPanel, "Règles");

        // Afficher la carte "Menu" par défaut
        cardLayout.show(cardPanel, "Menu");

        // Ajouter le panneau principal à la fenêtre
        frame.add(cardPanel);

        // Afficher la fenêtre
        frame.setVisible(true);
    }

    private static JPanel createMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centrer tout le contenu

        // Bouton Jouer
        JButton jouerButton = new JButton("Jouer");
        jouerButton.setPreferredSize(new Dimension(200, 50));  // Agrandir le bouton
        jouerButton.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centrer le bouton
        jouerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Jouer");  // Afficher le panneau "Jouer"
            }
        });

        // Bouton Règles
        JButton reglesButton = new JButton("Règles");
        reglesButton.setPreferredSize(new Dimension(200, 50));  // Agrandir le bouton
        reglesButton.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centrer le bouton
        reglesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Règles");  // Afficher le panneau "Règles"
            }
        });

        // Bouton Quitter
        JButton quitterButton = new JButton("Quitter");
        quitterButton.setPreferredSize(new Dimension(200, 50));  // Agrandir le bouton
        quitterButton.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centrer le bouton
        quitterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);  // Quitter l'application
            }
        });

        // Ajouter les boutons au panneau
        panel.add(Box.createVerticalStrut(20)); // Espacement en haut
        panel.add(jouerButton);
        panel.add(Box.createVerticalStrut(20)); // Espacement entre les boutons
        panel.add(reglesButton);
        panel.add(Box.createVerticalStrut(20)); // Espacement entre les boutons
        panel.add(quitterButton);
        panel.add(Box.createVerticalStrut(20)); // Espacement en bas

        return panel;
    }

    private static JPanel createJouerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Bouton Créer un Plateau
        JButton creerPlateauButton = new JButton("Créer un Plateau");
        creerPlateauButton.setPreferredSize(new Dimension(250, 50));
        creerPlateauButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        creerPlateauButton.addActionListener(e -> ouvrirFenetrePlateau());
    
        // Bouton Jouer un Plateau
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
    
        // Panneau principal avec un BoxLayout (Y_AXIS) pour empiler les composants verticalement
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    
        // Ajoutez un label en haut pour le texte
        JLabel label = new JLabel("Choisissez une série");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centrer le texte
        label.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(Box.createVerticalStrut(20)); // Espacement en haut
        mainPanel.add(label);
        mainPanel.add(Box.createVerticalStrut(20)); // Espacement entre le texte et les boutons
    
        // Panneau pour les boutons, avec un BoxLayout pour l'alignement vertical
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centre le panel des boutons horizontalement
    
        try (Connection cnx = DriverManager.getConnection("jdbc:mariadb://dwarves.iut-fbleau.fr/siuda", "siuda", "Siuda77140")) {
            // Appel à la fonction afficherSeries pour remplir le panneau avec des boutons
            Serveur.afficherSeries(cnx, buttonsPanel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        // Ajouter le panneau des boutons avec une barre de défilement
        JScrollPane scrollPane = new JScrollPane(buttonsPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Désactiver la barre horizontale
        mainPanel.add(scrollPane);
    
        // Ajouter le panneau principal à la fenêtre
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
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centrer tout le contenu

        JLabel reglesLabel = new JLabel("Voici les règles du jeu...");
        reglesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centrer le texte

        JButton retourButton = new JButton("Retour");
        retourButton.setPreferredSize(new Dimension(200, 50));  // Agrandir le bouton
        retourButton.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centrer le bouton
        retourButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Menu");  // Retourner au menu
            }
        });

        // Ajouter les composants au panneau
        panel.add(Box.createVerticalStrut(20)); // Espacement en haut
        panel.add(reglesLabel);
        panel.add(Box.createVerticalStrut(20)); // Espacement entre le texte et le bouton
        panel.add(retourButton);
        panel.add(Box.createVerticalStrut(20)); // Espacement en bas

        return panel;
    }
}
