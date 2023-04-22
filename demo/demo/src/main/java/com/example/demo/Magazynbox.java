package com.example.demo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Magazynbox extends JDialog {
    private JPanel contentPane;
    private JTextField id_numer_field;
    private JTextField nazwa_field;
    private JTextField ilosc_field;
    private JTable ShowTable;
    private JButton UPDATEButton;
    private JButton DELETEButton;
    private JButton ADDButton;
    private JButton SEARCHButton;
    private JButton sklepButton;

    public Magazynbox() {
        setContentPane(contentPane);
        setModal(true);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });


        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);



        ADDButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://127.0.0.1:3306/magazyn";
                String username = "root";
                String password = "";

                try {

                    String id_numer = id_numer_field.getText();
                    String nazwa = nazwa_field.getText();
                    String ilosc = ilosc_field.getText();


                    Connection connection = DriverManager.getConnection(url, username, password);
                    Statement statement = connection.createStatement();


                    String query = "INSERT INTO magazyn2 (id_numer, Nazwa, Ilosc) VALUES ('" + id_numer + "', '" + nazwa + "', '" + ilosc + "')";
                    statement.executeUpdate(query);


                    statement.close();
                    connection.close();

                    id_numer_field.setText("");
                    nazwa_field.setText("");
                    ilosc_field.setText("");
                    createTable();

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

        UPDATEButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://127.0.0.1:3306/magazyn";
                String username = "root";
                String password = "";

                try {

                    String id_numer = id_numer_field.getText();
                    String nazwa = nazwa_field.getText();
                    String ilosc = ilosc_field.getText();


                    Connection connection = DriverManager.getConnection(url, username, password);
                    Statement statement = connection.createStatement();
                    String query = "UPDATE magazyn2 SET nazwa = '" + nazwa + "', ilosc = '" + ilosc + "' WHERE id_numer = '" + id_numer+ "';";
                    statement.executeUpdate(query);

                    statement.close();
                    connection.close();

                    id_numer_field.setText("");
                    nazwa_field.setText("");
                    ilosc_field.setText("");
                    createTable();

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });


        DELETEButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://127.0.0.1:3306/magazyn";
                String username = "root";
                String password = "";

                try {
                    String id_numer = id_numer_field.getText();
                    Connection connection = DriverManager.getConnection(url, username, password);
                    Statement statement = connection.createStatement();
                    String query = "DELETE FROM magazyn2 WHERE id_numer='" + id_numer + "'";
                    statement.executeUpdate(query);
                    statement.close();
                    connection.close();

                    id_numer_field.setText("");
                    nazwa_field.setText("");
                    ilosc_field.setText("");
                    createTable();

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

        SEARCHButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://127.0.0.1:3306/magazyn";
                String username = "root";
                String password = "";

                try {

                    String id_numer = id_numer_field.getText();


                    Connection connection = DriverManager.getConnection(url, username, password);
                    Statement statement = connection.createStatement();


                    ResultSet resultSet=statement.executeQuery("SELECT * FROM magazyn2 WHERE id_numer = '" + id_numer+ "';");

                    DefaultTableModel tblmodel=(DefaultTableModel)ShowTable.getModel();
                    tblmodel.setColumnIdentifiers(new String[]{"id_produktu","Nazwa","Ilosc"});
                    tblmodel.setRowCount(0);
                    while(resultSet.next()){
                        String id_numerd=resultSet.getString("id_numer");
                        String nazwa=resultSet.getString("Nazwa");
                        String ilosc=resultSet.getString("Ilosc");
                        String tbData[]= {id_numerd,nazwa,ilosc};
                        tblmodel.addRow(tbData);
                    }
                    statement.close();
                    connection.close();

                    id_numer_field.setText("");
                    nazwa_field.setText("");
                    ilosc_field.setText("");


                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
    createTable();

        sklepButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                Sklep dialog = new Sklep();
                dialog.createTable();
                dialog.pack();
                dialog.setVisible(true);
            }
    });

    }


    public void createTable(){
        String url="jdbc:mysql://127.0.0.1:3306/magazyn";
        String username="root";
        String password="";

        try{
            Connection connection= DriverManager.getConnection(url,username,password);
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery("select * from magazyn2");

            DefaultTableModel tblmodel=(DefaultTableModel)ShowTable.getModel();
            tblmodel.setColumnIdentifiers(new String[]{"id_produktu","Nazwa","Ilosc"});
            
            while(resultSet.next()){
                String id_numerd=resultSet.getString("id_numer");
                String nazwa=resultSet.getString("Nazwa");
                String ilosc=resultSet.getString("Ilosc");
                String tbData[]= {id_numerd,nazwa,ilosc};
                tblmodel.addRow(tbData);
            }

            statement.close();
            connection.close();


        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {


        Magazynbox dialog = new Magazynbox();
        dialog.pack();
        dialog.setVisible(true);


    }

}
