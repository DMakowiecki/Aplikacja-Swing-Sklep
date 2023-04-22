package com.example.demo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDateTime;

public class Sklep extends JDialog {
    private JPanel contentPane;
    private JTable ShowTable;
    private JButton SEARCHbutton;
    private JTextField nazwa_field;
    private JButton ZamawiamButton;
    private JTextField ilosc_field;
    private JButton zamowienieButton;
    private JButton magazynButton;

    private JButton buttonOK;


    public Sklep() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        SEARCHbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                wyswietl();
            }
        });
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });


        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ZamawiamButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://127.0.0.1:3306/magazyn";
                String username = "root";
                String password = "";

                try {


                    String nazwa = nazwa_field.getText();
                    String ilosc_ = ilosc_field.getText();
                    int iloscc = Integer.parseInt(ilosc_);

                    Connection connection = DriverManager.getConnection(url, username, password);
                    Statement statementt = connection.createStatement();
                    ResultSet resultt = statementt.executeQuery("SELECT id_numer FROM magazyn2 WHERE nazwa = '" + nazwa + "'");
                    String idd = null;
                    if (resultt.next()) {
                        idd = resultt.getString(1);
                    }
                    Statement statement = connection.createStatement();
                    ResultSet result = statement.executeQuery("SELECT ilosc FROM magazyn2 WHERE nazwa = '" + nazwa + "'");
                    int aktualnaIlosc = 0;
                    if (result.next()) {
                        aktualnaIlosc = result.getInt(1);
                    }


                    int nowaIlosc = aktualnaIlosc - iloscc;
                    String query = "UPDATE magazyn2 SET ilosc =" + nowaIlosc + " WHERE nazwa = '" + nazwa + "'";
                    statement.executeUpdate(query);
                    String query1 = "INSERT INTO zamowienia (id_uzytkownika, id_produktu, ilosc, data_zamowienia) VALUES ('1', '" + idd + "', '" + iloscc + "', '" + LocalDateTime.now() + "')";


                    statement.executeUpdate(query1);

                    statement.close();
                    connection.close();


                    nazwa_field.setText("");
                    ilosc_field.setText("");
                    createTable();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        zamowienieButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                ZamowieniaBox dialog = new ZamowieniaBox();
                dialog.createTable();
                dialog.pack();
                dialog.setVisible(true);
            }
            });
        magazynButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                Magazynbox dialog = new Magazynbox();
                dialog.createTable();
                dialog.pack();
                dialog.setVisible(true);
            }
        });
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
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


        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void wyswietl() {
        String url = "jdbc:mysql://127.0.0.1:3306/magazyn";
        String username = "root";
        String password = "";

        try {

            String nazwa_ = nazwa_field.getText();


            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();


            ResultSet resultSet=statement.executeQuery("SELECT * FROM magazyn2 WHERE nazwa = '" + nazwa_+ "';");

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


            nazwa_field.setText("");



        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }};






public static void main(String[] args) {

        Sklep dialog = new Sklep();
        dialog.pack();
    dialog.createTable();
        dialog.setVisible(true);


    }
}
