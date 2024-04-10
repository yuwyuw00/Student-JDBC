import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField name;
    private JTextField course;
    private JTextField id;
    private JTable table;
    private JPanel addAccPanel;
    private JComboBox year;
    private JScrollPane scrollPane;
    private JLabel lblName;
    private JLabel lblCourseYear;
    private JLabel lblIdNumber;
    private DefaultTableModel model;
    JPanel editPanel;
    JPanel deletePanel;

    @SuppressWarnings("serial")
	public MainFrame() {
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

        
        JLabel lblNewLabel_2 = new JLabel("Student Attendance ");
        lblNewLabel_2.setForeground(new Color(255, 255, 255));
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel_2.setBounds(85, 19, 525, 28);
        headerPanel.add(lblNewLabel_2);


        JPanel menuPanel = new JPanel();
        menuPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        menuPanel.setBackground(new Color(0, 128, 192));
        menuPanel.setBounds(10, 89, 200, 562);
        contentPane.add(menuPanel);
        menuPanel.setLayout(null);

       
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, 200, 30);
        menuPanel.add(menuBar);

    
        JMenu mnNewMenu = new JMenu("");
        mnNewMenu.setIcon(new ImageIcon(MainFrame.class.getResource("/assets/icons8-hamburger-24.png")));
        menuBar.add(mnNewMenu);

        JMenu StudentAccountMenu = new JMenu("Student Account");
        mnNewMenu.add(StudentAccountMenu);

        JMenuItem addMenu = new JMenuItem("Add Student Account");
        addMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAddAccountPanel();
            }
        });
        StudentAccountMenu.add(addMenu);


        JMenuItem editMenu = new JMenuItem("Edit Student Account");
        editMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showEditPanel();
            }
        });
        StudentAccountMenu.add(editMenu);

 
        JMenuItem deleteMenu = new JMenuItem("Delete Student Account");
        deleteMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showDeletePanel();
            }
        });
        StudentAccountMenu.add(deleteMenu);

        
        JMenuItem attedanceMenu = new JMenuItem("Attendance");
        attedanceMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        mnNewMenu.add(attedanceMenu);

        
        JMenuItem listMenu = new JMenuItem("Show Student List");
        listMenu.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        	}
        });
        mnNewMenu.add(listMenu);

        
        JMenuItem saveMenu = new JMenuItem("Save");
        mnNewMenu.add(saveMenu);

        
        addAccPanel = new JPanel();
        addAccPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(91, 91, 91)));
        addAccPanel.setBackground(new Color(225, 225, 225));
        addAccPanel.setBounds(220, 89, 754, 562);
        contentPane.add(addAccPanel);
        addAccPanel.setLayout(null);
        addAccPanel.setVisible(false);

        
        JButton addAccountbttn = new JButton("Add Account");
        addAccountbttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addAccount();
            }
        });
        addAccountbttn.setBounds(581, 56, 125, 30);
        addAccPanel.add(addAccountbttn);

        
        scrollPane = new JScrollPane();
        scrollPane.setBounds(73, 209, 633, 236);
        addAccPanel.add(scrollPane);

        
        table = new JTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displaySelectedRowData();
            }
        });
        table.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        table.setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] { "ID Number", "Student Name", "Course", "Year" }
        ) {
            boolean[] columnEditables = new boolean[] { false, false, false, false };
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        scrollPane.setViewportView(table);

        
        JButton addCancelbttn = new JButton("Cancel");
        addCancelbttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearInputFields();
            }
        });
        addCancelbttn.setBounds(581, 97, 125, 30);
        addAccPanel.add(addCancelbttn);

        
        editPanel = new JPanel();
        editPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        editPanel.setBackground(new Color(225, 225, 225));
        editPanel.setBounds(220, 89, 754, 562);
        contentPane.add(editPanel);
        editPanel.setVisible(false);
        editPanel.setLayout(null);

        
        JButton SaveChanges = new JButton("Save Changes");
        SaveChanges.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveChanges();
            }
        });
        SaveChanges.setBounds(532, 354, 194, 41);
        editPanel.add(SaveChanges);

        
        JButton Cancel = new JButton("Cancel");
        Cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearInputFields();
            }
        });
        Cancel.setBounds(532, 402, 194, 41);
        editPanel.add(Cancel);

        
        deletePanel = new JPanel();
        deletePanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        deletePanel.setBackground(new Color(225, 225, 225));
        deletePanel.setBounds(220, 89, 754, 562);
        contentPane.add(deletePanel);
        deletePanel.setLayout(null);
        deletePanel.setVisible(false);

        
        JButton btnNewButton = new JButton("Delete Student Account");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteSelectedAccount();
            }
        });
        btnNewButton.setBounds(490, 473, 222, 37);
        deletePanel.add(btnNewButton);

       
        JLabel lblNewLabel = new JLabel("Select a Row ");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNewLabel.setBounds(10, 11, 129, 14);
        deletePanel.add(lblNewLabel);

        initializeComponents();
    }

   
    private void initializeComponents() {
        lblName = new JLabel("Student's Name");
        lblCourseYear = new JLabel("Course & Year");
        lblIdNumber = new JLabel("ID Number");

        name = new JTextField();
        name.setColumns(10);

        course = new JTextField();
        course.setColumns(10);

        id = new JTextField();
        id.setColumns(10);

        year = new JComboBox();
        year.setModel(new DefaultComboBoxModel(new String[] {"First Year", "Second Year", "Third Year", "Fourth Year", "Fifth Year"}));

        model = (DefaultTableModel) table.getModel();
    }

    
    private void displaySelectedRowData() {
        int selectedValue = table.getSelectedRow();
        id.setText((String) model.getValueAt(selectedValue, 0));
        name.setText((String) model.getValueAt(selectedValue, 1));
        course.setText((String) model.getValueAt(selectedValue, 2));
        year.setToolTipText((String) model.getValueAt(selectedValue, 3));
    }

  
    private void addAccount() {
        if (name.getText().equals("") || id.getText().equals("") || course.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please Insert Complete Information!");
        } else {
            model.addRow(new Object[]{
                    name.getText(),
                    id.getText(),
                    course.getText(),
                    year.getSelectedItem()
            });

            JOptionPane.showMessageDialog(null, "Student Account is Succesfully Added.");

            clearInputFields();
        }
    }

    
    private void saveChanges() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            model.setValueAt(id.getText(), selectedRow, 0);
            model.setValueAt(name.getText(), selectedRow, 1);
            model.setValueAt(course.getText(), selectedRow, 2);
            model.setValueAt(year.getSelectedItem(), selectedRow, 3);

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
        } else {
            JOptionPane.showMessageDialog(null, "Please Select a Row to Delete.");
        }
    }

    
    private void clearInputFields() {
        name.setText("");
        id.setText("");
        course.setText("");
        year.setSelectedItem("First Year");
    }

    
    private void showAddAccountPanel() {
        editPanel.setVisible(false);
        deletePanel.setVisible(false);
        addAccPanel.setVisible(true);
        scrollPane.setBounds(73, 209, 633, 236);
        lblName.setBounds(73, 54, 95, 35);
        addAccPanel.add(lblName);
        lblCourseYear.setBounds(73, 146, 95, 35);
        addAccPanel.add(lblCourseYear);
        lblIdNumber.setBounds(73, 100, 95, 35);
        addAccPanel.add(lblIdNumber);
        name.setBounds(169, 54, 360, 35);
        addAccPanel.add(name);
        course.setBounds(169, 146, 255, 35);
        addAccPanel.add(course);
        id.setBounds(169, 100, 360, 35);
        addAccPanel.add(id);
        year.setBounds(434, 146, 95, 34);
        addAccPanel.add(year);
        addAccPanel.add(scrollPane);

        clearInputFields();
    }

   
    private void showEditPanel() {
        addAccPanel.setVisible(false);
        deletePanel.setVisible(false);
        editPanel.setVisible(true);

        scrollPane.setBounds(10, 11, 734, 335);
        editPanel.add(scrollPane);
        lblName.setBounds(20, 357, 95, 35);
        editPanel.add(lblName);
        lblCourseYear.setBounds(20, 449, 95, 35);
        editPanel.add(lblCourseYear);
        lblIdNumber.setBounds(20, 403, 95, 35);
        editPanel.add(lblIdNumber);
        name.setBounds(116, 357, 360, 35);
        editPanel.add(name);
        course.setBounds(116, 449, 255, 35);
        editPanel.add(course);
        id.setBounds(116, 403, 360, 35);
        editPanel.add(id);
        year.setBounds(381, 449, 95, 34);
        editPanel.add(year);

        clearInputFields();
    }

    
    private void showDeletePanel() {
        addAccPanel.setVisible(false);
        editPanel.setVisible(false);
        deletePanel.setVisible(true);
        scrollPane.setBounds(28, 34, 697, 400);
        deletePanel.add(scrollPane);
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
