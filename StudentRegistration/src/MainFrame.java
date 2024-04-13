import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainFrame extends JFrame {
    private static final String URL = "jdbc:mysql://localhost:3306/connector";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";
    private static Connection connection;

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField Name;
    private JTextField Course;
    private JTextField id;
    private JTable table;
    private JPanel addAccPanel;
    private JComboBox Year;
    private JScrollPane scrollPane;
    private JLabel lblName;
    private JLabel lblCourseYear;
    private JLabel lblIdNumber;
    private DefaultTableModel model;

    @SuppressWarnings("serial")
    public MainFrame() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/assets/columbanlogo50x50.png")));
        setTitle("Student List");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 700);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(133, 186, 250));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel headerPanel = new JPanel();
        headerPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        headerPanel.setBackground(new Color(12, 66, 120));
        headerPanel.setBounds(10, 11, 964, 67);
        contentPane.add(headerPanel);
        headerPanel.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon(MainFrame.class.getResource("/assets/columbanlogo50x50.png")));
        lblNewLabel_1.setBounds(10, 2, 95, 62);
        headerPanel.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Student List");
        lblNewLabel_2.setForeground(new Color(255, 255, 255));
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel_2.setBounds(85, 19, 525, 28);
        headerPanel.add(lblNewLabel_2);

        JPanel menuPanel = new JPanel(); // Create menuPanel
        menuPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        menuPanel.setBackground(new Color(0, 128, 192));
        menuPanel.setBounds(10, 89, 187, 562);
        contentPane.add(menuPanel);
        menuPanel.setLayout(null);
//MENU COMPONENTS AND INITIALIZATIONS vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, 200, 30);
        menuPanel.add(menuBar);

        JMenu mnNewMenu = new JMenu("");
        mnNewMenu.setIcon(new ImageIcon(MainFrame.class.getResource("/assets/icons8-hamburger-24.png")));
        menuBar.add(mnNewMenu);

        JMenu StudentAccountMenu = new JMenu("Student Account");
        mnNewMenu.add(StudentAccountMenu);

        JMenuItem mntmNewMenuItem = new JMenuItem("Sample Item");
        StudentAccountMenu.add(mntmNewMenuItem);

        JMenuItem addMenu = new JMenuItem("Add Student Account");
        mnNewMenu.add(addMenu);
        addMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addAccount();
            }
        });

        JMenuItem editMenu = new JMenuItem("Edit Student Account");
        mnNewMenu.add(editMenu);
        editMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveChanges();
            }
        });

        JMenuItem deleteMenu = new JMenuItem("Delete Student Account");
        mnNewMenu.add(deleteMenu);
        deleteMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteSelectedAccount();
                
            }
        });

        JMenuItem saveMenu = new JMenuItem("Save");
        mnNewMenu.add(saveMenu);

        JMenuItem exitMenu_1 = new JMenuItem("Exit");
        exitMenu_1.addActionListener(new ActionListener() {
            private JFrame ExitFrame;
            public void actionPerformed(ActionEvent e) {
                ExitFrame = new JFrame("Exit");
                if (JOptionPane.showConfirmDialog(ExitFrame, "Exit Program?", "Student List",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
                    System.exit(0);
                }
            }
        });
        mnNewMenu.add(exitMenu_1);
        

        JCheckBoxMenuItem chckbxmntmNewCheckItem = new JCheckBoxMenuItem("Sample Check Box");
        chckbxmntmNewCheckItem.setHorizontalAlignment(SwingConstants.LEFT);
        menuBar.add(chckbxmntmNewCheckItem);
        

        JRadioButtonMenuItem rdbtnmntmNewRadioItem = new JRadioButtonMenuItem("Sample Radio Button");
        rdbtnmntmNewRadioItem.setHorizontalAlignment(SwingConstants.LEFT);
        menuBar.add(rdbtnmntmNewRadioItem);
//MENU COMPONENTS AND INITIALIZATIONS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        
        addAccPanel = new JPanel();
        addAccPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(91, 91, 91)));
        addAccPanel.setBackground(new Color(225, 225, 225));
        addAccPanel.setBounds(207, 89, 767, 562);
        contentPane.add(addAccPanel);
        addAccPanel.setLayout(null);
        addAccPanel.setVisible(true);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(62, 182, 644, 280);
        addAccPanel.add(scrollPane);
        //TABLE COMPONENTSvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv       
        model = new DefaultTableModel(
                new Object[][] {},
                new String[] { "StudentID", "Name", "Course", "Year" }
            ) {
                boolean[] columnEditables = new boolean[] { false, false, false, false };
                public boolean isCellEditable(int row, int column) {
                    return columnEditables[column];
                }
            };

        table = new JTable(model);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displaySelectedRowData();
            }
        });
        scrollPane.setViewportView(table);
      //TABLE COMPONENTS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        JButton addCancelbttn = new JButton("Cancel");
        addCancelbttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearInputFields();
            }
        });
        addCancelbttn.setBounds(62, 473, 644, 30);
        addAccPanel.add(addCancelbttn);

        initializeComponents(); 
        connectToDatabase();
        updateTable();
    }


    private void initializeComponents() {//INITIALIZATION OF THE COMPONETS
        lblName = new JLabel("Student's Name");
        lblCourseYear = new JLabel("Course & Year");
        lblIdNumber = new JLabel("StudentID Number");
        Name = new JTextField();
        Name.setColumns(10);
        Course = new JTextField();
        Course.setColumns(10);
        id = new JTextField();
        id.setColumns(10);
        Year = new JComboBox<>();
        Year.setModel(new DefaultComboBoxModel<>(new String[] {"First Year", "Second Year", "Third Year", "Fourth Year", "Fifth Year"}));
        lblName.setBounds(73, 21, 95, 35);
        addAccPanel.add(lblName);
        lblCourseYear.setBounds(73, 113, 95, 35);
        addAccPanel.add(lblCourseYear);
        lblIdNumber.setBounds(73, 67, 95, 35);
        addAccPanel.add(lblIdNumber);
        Name.setBounds(169, 21, 537, 35);
        addAccPanel.add(Name);
        Course.setBounds(169, 113, 333, 35);
        addAccPanel.add(Course);
        id.setBounds(169, 67, 537, 35);
        addAccPanel.add(id);
        Year.setBounds(513, 113, 193, 34);
        addAccPanel.add(Year);

        JLabel lblNewLabel = new JLabel("**Please Select a Row to EDIT or DELETE a Student Account");
        lblNewLabel.setForeground(new Color(255, 45, 45));
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(83, 149, 364, 22);
        addAccPanel.add(lblNewLabel);
    }

    private void displaySelectedRowData() {//DISPLAY THE INSERTED VALUES TO THE TEXT FEILD FORM THE TSELECTED ROW
        int selectedValue = table.getSelectedRow();
        id.setText((String) model.getValueAt(selectedValue, 0));
        Name.setText((String) model.getValueAt(selectedValue, 1));
        Course.setText((String) model.getValueAt(selectedValue, 2));
        Year.setSelectedItem((String) model.getValueAt(selectedValue, 3));
    }

    private void addAccount() {
        if (Name.getText().equals("") || id.getText().equals("") || Course.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please Insert Complete Information!");
        } else {
            model.addRow(new Object[]{
                    id.getText(),
                    Name.getText(),
                    Course.getText(),
                    Year.getSelectedItem()
            });

            JOptionPane.showMessageDialog(null, "Student Account is Succesfully Added.");

            insertStudent(Name.getText(), id.getText(), Course.getText(), (String) Year.getSelectedItem());

            clearInputFields();
        }
    }

    private void saveChanges() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            model.setValueAt(id.getText(), selectedRow, 0);
            model.setValueAt(Name.getText(), selectedRow, 1);
            model.setValueAt(Course.getText(), selectedRow, 2);
            model.setValueAt(Year.getSelectedItem(), selectedRow, 3);

            JOptionPane.showMessageDialog(null, "Changes Saved Successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Please Select a Row to Edit.");
        }
    }

    private void deleteSelectedAccount() {
    	 int selectedRow = table.getSelectedRow();
    	    if (selectedRow >= 0) {
    	        model.removeRow(selectedRow);
    	        JOptionPane.showMessageDialog(null, "Student Account is Successfully Deleted.");

    	  
    	        deleteStudent((String) model.getValueAt(selectedRow, 0));
    	    } else {
    	        JOptionPane.showMessageDialog(null, "Please Select a Row to Delete.");
    	    }
    }

    private void clearInputFields() {
        Name.setText("");
        id.setText("");
        Course.setText("");
        Year.setSelectedItem("First Year");
    }

    private void connectToDatabase() {//CONNECTION TO DATABASE AND WARNINGS
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);//PALITAN YUNG VALUES 'localhost...'root'1234' SA TAAS 
            System.out.println("Connected to the database.");
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
            e.printStackTrace(); 
        }
    }

    private void addStudent(String name, String id, String course, String year) {
        model.addRow(new Object[]{id, name, course, year});
    }

    private void insertStudent(String name, String id, String course, String year) {//METHOD INSERTING ADDED VALUES TO THE DATABASE
        try {
            String query = "INSERT INTO connector (StudentID, Name, Course, Year) VALUES (?, ?, ?, ?)";//SQL CODE
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, id);
                statement.setString(2, name);
                statement.setString(3, course);
                statement.setString(4, year);
                statement.executeUpdate();
            }

       
            updateTable();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to insert student record. Check any DUPLICATION.");
        }
    }

    private void deleteStudent(String id) {
    	  try {
    	        String query = "DELETE FROM connector WHERE StudentID=?";
    	        try (PreparedStatement statement = connection.prepareStatement(query)) {
    	            statement.setString(1, id); // Corrected index
    	            statement.executeUpdate();
    	        }
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	        JOptionPane.showMessageDialog(this, "Failed to delete student record.");
    	    }
    }

    private void updateTable() {
        model.setRowCount(0); 
        try {
            String query = "SELECT * FROM connector";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String id = resultSet.getString("StudentID");
                    String name = resultSet.getString("Name");
                    String course = resultSet.getString("Course");
                    String year = resultSet.getString("Year");
                    model.addRow(new Object[]{id, name, course, year});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrame frame = new MainFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
