package fr.iutfbleau.dick.siuda.paysages.views;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
     * Classe pour personnaliser le rendu des cellules du tableau.
     * <p>
     * Elle attribue des couleurs spécifiques aux trois premiers rangs : 
     * or pour le 1er, argent pour le 2ème, et bronze pour le 3ème.
     * Les autres rangs sont affichés en noir.
     * </p>
     */
public class CustomTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Définir les couleurs en fonction du rang
            if (column == 0) { // Colonne des rangs
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
                    default: // Autres rangs
                        cell.setForeground(Color.BLACK);
                        break;
                }
            } else {
                cell.setForeground(Color.BLACK); // Colonne des scores en noir
            }

            return cell;
        }
}
