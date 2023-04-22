package com.example.demo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class ZamowieniaBox extends JDialog {
    private JPanel contentPane;
    private JTable ShowTable;
    private JButton wroc;
    private JButton buttonOK;
    private JButton buttonCancel;

    public ZamowieniaBox() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        wroc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createTable();
            }
        });
        wroc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                Sklep dialog = new Sklep();
                dialog.createTable();
                dialog.pack();
                dialog.setVisible(true);
            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
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

            ResultSet resultSet=statement.executeQuery("SELECT zamowienia.nr_zamowienia, magazyn2.nazwa, zamowienia.ilosc, zamowienia.data_zamowienia, uzytkownicy.login FROM zamowienia INNER JOIN magazyn2 ON zamowienia.id_produktu = magazyn2.id_numer INNER JOIN uzytkownicy ON zamowienia.id_uzytkownika = uzytkownicy.id;");


            DefaultTableModel tblmodel=(DefaultTableModel)ShowTable.getModel();
            tblmodel.setColumnIdentifiers(new String[]{"nr_zamowienia","nazwa","ilosc","data_zamowienia"});
            tblmodel.setRowCount(0);
            while(resultSet.next()){
                String zamoweniadb =resultSet.getString("nr_zamowienia");
                String nazwadb=resultSet.getString("nazwa");
                String iloscdb=resultSet.getString("ilosc");
                String data_=resultSet.getString("data_zamowienia");
                String tbData[]= {zamoweniadb,nazwadb,iloscdb,data_};
                tblmodel.addRow(tbData);
            }

            statement.close();
            connection.close();


        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) {
        ZamowieniaBox dialog = new ZamowieniaBox();
        dialog.createTable();
        dialog.pack();
        dialog.setVisible(true);

    }
}
