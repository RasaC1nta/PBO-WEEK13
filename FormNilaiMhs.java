import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class FormNilaiMhs extends JFrame {
    
    JTextField txtNim = new JTextField();
    JTextField txtNama = new JTextField();
    JTextField txtUts = new JTextField();
    JTextField txtUas = new JTextField();
    JTextField txtTugas = new JTextField();
    JTable tabel = new JTable();
    DefaultTableModel model = new DefaultTableModel();

    public FormNilaiMhs() {
        setTitle("Aplikasi Nilai Mahasiswa - Laragon");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        JPanel pnlInput = new JPanel(new GridLayout(6, 2));
        pnlInput.add(new JLabel(" NIM:")); pnlInput.add(txtNim);
        pnlInput.add(new JLabel(" Nama:")); pnlInput.add(txtNama);
        pnlInput.add(new JLabel(" Nilai UTS:")); pnlInput.add(txtUts);
        pnlInput.add(new JLabel(" Nilai UAS:")); pnlInput.add(txtUas);
        pnlInput.add(new JLabel(" Nilai Tugas:")); pnlInput.add(txtTugas);

        JButton btnSimpan = new JButton("Simpan Data");
        pnlInput.add(btnSimpan);
        
        
        model.setColumnIdentifiers(new Object[]{"NIM", "Nama", "Akhir", "Huruf", "Predikat"});
        tabel.setModel(model);

        add(pnlInput, BorderLayout.NORTH);
        add(new JScrollPane(tabel), BorderLayout.CENTER);

        // Event Tombol
        btnSimpan.addActionListener(e -> simpanData());

        tampilkanData();
        setVisible(true);
    }

    private void simpanData() {
        try {
            Mhs m = new Mhs(txtNim.getText(), txtNama.getText(), 
                    Double.parseDouble(txtUts.getText()), 
                    Double.parseDouble(txtUas.getText()), 
                    Double.parseDouble(txtTugas.getText()));

            String sql = "INSERT INTO mhs (nim, nama, nilai_uts, nilai_uas, nilai_tugas, nilai_akhir, nilai_huruf, predikat) VALUES (?,?,?,?,?,?,?,?)";
            
            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, m.getNim());
                ps.setString(2, m.getNama());
                ps.setDouble(3, Double.parseDouble(txtUts.getText()));
                ps.setDouble(4, Double.parseDouble(txtUas.getText()));
                ps.setDouble(5, Double.parseDouble(txtTugas.getText()));
                ps.setDouble(6, m.getAkhir());
                ps.setString(7, m.getHuruf());
                ps.setString(8, m.getPredikat());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data Tersimpan!");
                tampilkanData();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void tampilkanData() {
        model.setRowCount(0);
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM mhs")) {
            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("nim"), rs.getString("nama"), 
                    rs.getDouble("nilai_akhir"), rs.getString("nilai_huruf"), rs.getString("predikat")});
            }
        } catch (SQLException ex) {}
    }

    public static void main(String[] args) {
        new FormNilaiMhs();
    }
}
