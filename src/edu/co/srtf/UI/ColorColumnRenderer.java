/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.srtf.UI;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author juancamilo
 */
/**
 * Applied background and foreground color to single column of a JTable in order
 * to distinguish it apart from other columns.
 */
class ColorColumnRenderer extends DefaultTableCellRenderer {

    private int rowToColor = 0;
    private Color color;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        JLabel label = new JLabel();
        label.setOpaque(true);
        
        if (value != null) {
            label.setText(value.toString());
        }

        if (row == this.rowToColor) {
            label.setBackground(color);
            return label;
        }
        return label;

    }

    public void setRowToColor(int rowToColor) {
        this.rowToColor = rowToColor;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
