package edu.co.srtf.UI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import edu.co.srtf.models.Process;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author juancamilo
 */
public class GUI extends JFrame {

    Color c1 = new Color(51, 106, 184);
    Color c2 = new Color(176, 209, 255);
    Color c3 = new Color(176, 209, 255);

    Font font = new Font("Agency FB", Font.BOLD, 34);
    Font font2 = new Font("Agency FB", Font.BOLD, 20);
    Font fontAyuda = new Font("Agency FB", Font.BOLD, 12);
    Font fontTabla = new Font("Agency FB", Font.BOLD, 15);

    JLabel lblTitle = new JLabel();
    JLabel lblAutorD = new JLabel();
    JLabel lblAutorJ = new JLabel();
    JLabel lblAyuda = new JLabel("<html>** El número de procesos y la rafaga de "
            + "cada uno se generan de forma aleatoría **</html>", SwingConstants.CENTER);

    JPanel pnlHeader = new JPanel();
    JPanel pnlSubHeader = new JPanel();
    JPanel pnlContent = new JPanel();
    JPanel pnlDiagram = new JPanel();

    JTable tblProcess;
    JTable diagram;

    JScrollPane scrollProcess;
    JScrollPane scrollDiagram;
    public JButton btnStart = new JButton("INICIAR");

    final int screenWidth = 1020;
    final int screenHeigth = 720;
    final String nombrePrograma = "Short Remaining Time First";

    public GUI() {
        Container c = getContentPane();
        c.setLayout(null);
        this.setTitle(nombrePrograma);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drawHeader();
        drawSubHeader();
        drawContent();

        setVisible(true);
        setSize(screenWidth, screenHeigth);
    }

    void drawHeader() {
        add(pnlHeader);
        pnlHeader.setLayout(null);
        pnlHeader.setBounds(0, 0, screenWidth, 100);
        pnlHeader.setBackground(c1);
        pnlHeader.add(lblTitle);
        lblTitle.setText(nombrePrograma);
        lblTitle.setBounds(100, 50, 500, 40);
        lblTitle.setFont(font);
        lblTitle.setForeground(c3);
    }

    void drawSubHeader() {
        add(pnlSubHeader);
        pnlSubHeader.setLayout(null);
        pnlSubHeader.setBounds(0, 100, screenWidth, 75);
        pnlSubHeader.setBackground(c1);
        pnlSubHeader.add(lblAutorD);
        lblAutorD.setText("DAVID STEVEN SANTOS SANTOS");
        lblAutorD.setBounds(650, 10, 350, 20);
        lblAutorD.setFont(font2);
        pnlSubHeader.add(lblAutorJ);
        lblAutorJ.setText("JUAN CAMILO SARMIENTO REYES");
        lblAutorJ.setBounds(650, 33, 350, 20);
        lblAutorJ.setFont(font2);

    }

    void drawContent() {
        add(pnlContent);
        pnlContent.setLayout(null);
        pnlContent.setBounds(0, 175, screenWidth, 300);
        pnlContent.setBackground(c2);

        dibujarBoton();
        dibujarAyuda();
    }
    
    void dibujarAyuda() {
        pnlContent.add(lblAyuda);
        lblAyuda.setBounds(820, 50, 200, 40);
        lblAyuda.setFont(fontAyuda);
    }
    
    void dibujarBoton() {
        pnlContent.add(btnStart);
        btnStart.setBounds(850, 10, 100, 40);
        btnStart.setForeground(c1);
        btnStart.setFont(font2);
    }

    public void dibujarTabla(List<Process> processes) {
        pnlContent.removeAll();
        pnlContent.repaint();
        
        dibujarBoton();
        dibujarAyuda();
        tblProcess = new JTable(processes.size(), 7);
        
        for (int i = 0; i < processes.size(); i++) {
            for (int j = 0; j < 3; j++) {
                tblProcess.setValueAt(processes.get(i).getNombre(), i, 0);
                tblProcess.setValueAt(processes.get(i).getTiempoLlegada(), i, 1);
                tblProcess.setValueAt(processes.get(i).getTiempoRafaga(), i, 2);
            }
        }

        tblProcess.setPreferredScrollableViewportSize(new Dimension(700, 150));
        tblProcess.setFillsViewportHeight(true);
        tblProcess.setBackground(c2);
        tblProcess.setForeground(Color.BLACK);
        tblProcess.setFont(fontTabla);
        tblProcess.setEnabled(false);

        JScrollPane scrollTableProcess = new JScrollPane(tblProcess);
        pnlContent.add(scrollTableProcess);

        scrollTableProcess.setBounds(10, 10, 800, 300);
        scrollTableProcess.setBackground(c2);
        scrollTableProcess.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        changeColumNames();
    }

    /**
     * @param process Nombre del proceso
     * @param arriveTime Hora de llegada
     * @param executeTime Tiempo de rafaga
     */
    public void addTableRow(String process, int arriveTime, int executeTime) {
        DefaultTableModel model = (DefaultTableModel) tblProcess.getModel();
        model.addRow(new Object[]{process, arriveTime, executeTime});
    }

    public void addTableInfo(int tComienzo, int tFinal, int tRetorno, int tEspera, int row) {
        tblProcess.setValueAt(tComienzo, row, 3);
        tblProcess.setValueAt(tFinal, row, 4);
        tblProcess.setValueAt(tRetorno, row, 5);
        tblProcess.setValueAt(tEspera, row, 6);
        tblProcess.repaint();
    }

    public void dibujarDiagrama(List<Process> processes, int totalTime) {
        pnlDiagram.removeAll();
        pnlDiagram.repaint();

        add(pnlDiagram);
        pnlDiagram.setBackground(c2);
        pnlDiagram.setLayout(null);
        pnlDiagram.setBounds(0, 475, screenWidth, 225);

        String columns[] = new String[totalTime + 1];
        String data[][] = new String[processes.size()][totalTime + 1];
        columns[0] = "Proceso";

        for (int i = 1; i < totalTime + 1; i++) {
            for (int j = 0; j < processes.size(); j++) {
                data[j][i] = " ";

            }
            columns[i] = Integer.toString(i - 1);
        }
        DefaultTableModel model = new DefaultTableModel(data, columns);
        diagram = new JTable(model);
        diagram.setEnabled(false);
        diagram.setBackground(c2);
        diagram.setPreferredScrollableViewportSize(new Dimension(screenWidth - 50, 150));
        diagram.setFillsViewportHeight(true);

        JScrollPane scroll = new JScrollPane(diagram);
        scroll.setBounds(10, 10, screenWidth - 50, 150);

        pnlDiagram.add(scroll);
    }

    public void editDiagramCell(String value, int row, int column) {
        diagram.setValueAt(value, row, column);
        diagram.repaint();
    }

    public void addDiagramRow(String processName) {
        DefaultTableModel model = (DefaultTableModel) diagram.getModel();
        model.addRow(new Object[]{processName});
    }

    public void pintarCelda(int column, int row, Color color) {
        ColorColumnRenderer cellRender = new ColorColumnRenderer();
        try {
            TableColumn tableColumn = diagram.getColumnModel().getColumn(column);
            cellRender.setRowToColor(row);
            cellRender.setColor(color);
            tableColumn.setCellRenderer(cellRender);
        } catch (Exception e) {
            System.out.println("column: " + column + " row: " + row);
            System.out.println("Error en GUI.paintCell(): " + e.getMessage());
        } finally {
            diagram.repaint();
        }
    }

    void changeColumNames() {
        JTableHeader tblHeader = tblProcess.getTableHeader();
        TableColumnModel tcm = tblHeader.getColumnModel();
        TableColumn tblColumn = tcm.getColumn(0);
        tblColumn.setHeaderValue("Proceso");
        TableColumn tblColumn2 = tcm.getColumn(1);
        tblColumn2.setHeaderValue("T. Llegada");
        TableColumn tblColumn3 = tcm.getColumn(2);
        tblColumn3.setHeaderValue("T. Rafaga");
        TableColumn tblColumn4 = tcm.getColumn(3);
        tblColumn4.setHeaderValue("T. Comienzo");
        TableColumn tblColumn5 = tcm.getColumn(4);
        tblColumn5.setHeaderValue("T. Final");
        TableColumn tblColumn6 = tcm.getColumn(5);
        tblColumn6.setHeaderValue("T. Retorno");
        TableColumn tblColumn7 = tcm.getColumn(6);
        tblColumn7.setHeaderValue("T. Espera");
        tblHeader.repaint();
    }

    public Color getRandomColor() {
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        return new Color(r, g, b);
    }
}
