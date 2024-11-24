package fr.iutfbleau.dick.siuda.paysages.views;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.util.List;

public class Scoreboard extends JFrame {
    private JButton backButton;
    public Scoreboard(int idSerie, List<Integer> scores) {
        setTitle("Classement des dix meilleurs scores");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Titre
        JLabel titleLabel = new JLabel("Classement pour la série " + idSerie);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        // Données pour le tableau
        String[] columnNames = {"RANG", "SCORE"};
        Object[][] data = new Object[10][2];
        for (int i = 0; i < 10; i++) {
            data[i][0] = (i + 1) + getRankSuffix(i + 1);
            if (scores.size() > i)
                data[i][1] = scores.get(i).toString();
            else
                data[i][1] = "0";
        }

        // Création du tableau
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        // Personnalisation des cellules avec un renderer
        table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());

        // Personnalisation de l'entête
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setReorderingAllowed(false);

        // Ajout du tableau avec un JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        // Bouton "Retour"
        backButton = new JButton("Retour");
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private String getRankSuffix(int rank) {
        // Ajoute le suffixe approprié à un numéro (1er, 2ème, 3ème, ...)
        switch (rank % 10) {
            case 1: return "er";
            default: return "ème";
        }
    }

    // Classe pour personnaliser les couleurs des cellules
    static class CustomTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Définir les couleurs en fonction du rang
            if (column == 0) { // La colonne des rangs
                switch (row) {
                    case 0: // 1er
                        cell.setForeground(new Color(255, 215, 0)); // Or
                        break;
                    case 1: // 2ème
                        cell.setForeground(new Color(192, 192, 192)); // Argent
                        break;
                    case 2: // 3ème
                        cell.setForeground(new Color(205, 127, 50)); // Bronze
                        break;
                    default: // Les autres rangs
                        cell.setForeground(Color.BLACK);
                        break;
                }
            } else {
                cell.setForeground(Color.BLACK); // Colonne des scores reste noire
            }

            return cell;
        }
    }

    public JButton getBackButton(){
        return backButton;
    }
}
