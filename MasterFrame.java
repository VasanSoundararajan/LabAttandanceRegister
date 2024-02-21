import javax.swing.*;
import java.awt.*;

public class MasterFrame extends JFrame {
    private JTable sessionTable;
    private JLabel statusLabel;
    private String[] columnNames = { "Reg No", "Name", "Date", "System No", "Session" };
    private Object[][] data = {
            { "", "", "", "", "" }
    };

    public MasterFrame() {
        statusLabel = new JLabel("Status: Ready");
        sessionTable = new JTable(data, columnNames);
        sessionTable.setPreferredScrollableViewportSize(new Dimension(500, 200));
        sessionTable.setFillsViewportHeight(true);

        add(statusLabel);
        JScrollPane scrollPane = new JScrollPane(sessionTable);
        add(scrollPane);

        setTitle("Master Login");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MasterFrame();
    }
}