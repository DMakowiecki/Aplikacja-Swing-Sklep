package com.example.demo;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class RejestracjaBox extends JDialog {
    private JPanel contentPane;
    private JButton zarejestrujButton;
    private JPasswordField haslo_field;
    private JFormattedTextField login_field;
    private JFormattedTextField imie_field;
    private JFormattedTextField nazwisko_field;
    private JFormattedTextField adres_field;
    private JButton buttonCancel;

    public RejestracjaBox() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(zarejestrujButton);

        zarejestrujButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String login = login_field.getText();
                String haslo = haslo_field.getText();
                String imie = imie_field.getText();
                String nazwisko = nazwisko_field.getText();
                String adres = adres_field.getText();
                if (!login.equals("") && !haslo.equals("") && !imie.equals("") && !nazwisko.equals("") && !adres.equals("")) {

                    String url = "jdbc:mysql://127.0.0.1:3306/magazyn";
                    String username = "root";
                    String password = "";


                    try {
                        Connection connection = DriverManager.getConnection(url, username, password);
                        Statement statement = connection.createStatement();

                        ResultSet resultSet = statement.executeQuery("SELECT login, haslo FROM uzytkownicy WHERE login='" + login + "' AND haslo='" + haslo + "'");
                        if (resultSet.next()) {
                            String dblogin = resultSet.getString("Login");
                            String dbhaslo = resultSet.getString("Haslo");
                            if (login.equals(dblogin) && haslo.equals(dbhaslo)) {

                                System.out.println("Użytkownik już istnieje");
                            } else {

                                System.out.println("Nieprawidłowe hasło");
                                statement.close();
                                connection.close();
                            }



                        }




                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());

                    }


                    try {
                        Connection connection = DriverManager.getConnection(url, username, password);
                        Statement statement = connection.createStatement();

                        String query = "INSERT INTO uzytkownicy (login, haslo, imie,nazwisko,adres) VALUES ('" + login + "', '" + haslo + "', '" + imie + "', '" + nazwisko + "', '" + adres + "')";
                        statement.executeUpdate(query);


                        statement.close();
                        connection.close();

                        System.out.println("Uzytkownik dodany");

                        try {

                            dispose();
                            Loginbox dialog = new Loginbox();

                            dialog.pack();
                            dialog.setVisible(true);
                        } catch (Exception ex) {
                            System.out.println("Test");
                        }




                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
//                onOK();

                }
            }
        });


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
        }


    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        RejestracjaBox dialog = new RejestracjaBox();
        dialog.pack();
        dialog.setVisible(true);
    //    System.exit(0);
    }
}
