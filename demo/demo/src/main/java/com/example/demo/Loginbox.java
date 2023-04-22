package com.example.demo;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Enumeration;

public class Loginbox extends JDialog {

    private JPanel contentPane;
    private JButton zalogujbutton;
    private JPasswordField haslo_field;
    private JFormattedTextField login_field;
    private JButton rejestracjaButton;
    private JButton buttonCancel;

    public Loginbox() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(zalogujbutton);

        zalogujbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 String   login = login_field.getText();
                String haslo = haslo_field.getText();

                if (!login.equals("") && !haslo.equals("")) {

                    String url = "jdbc:mysql://127.0.0.1:3306/magazyn";
                    String username = "root";
                    String password = "";


                    try {
                        Connection connection = DriverManager.getConnection(url, username, password);
                        String query = "SELECT haslo FROM uzytkownicy WHERE login = ?";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setString(1, login);
                        ResultSet resultSet = statement.executeQuery();

                        if (resultSet.next()) {
                            String dbhaslo = resultSet.getString("haslo");
                            if (haslo.equals(dbhaslo)) {
                                System.out.println("Zalogowales sie");
                                try {

                                    dispose();
                                    Sklep dialog = new Sklep();

                                    dialog.pack();
                                    dialog.createTable();
                                    dialog.setVisible(true);
                                } catch (Exception ex) {
                                    System.out.println("Test");
                                }


                            } else {
                                System.out.println("Nieprawidłowe hasło");
                            }
                        } else {
                            System.out.println("Użytkownik o podanym loginie nie istnieje");
                        }

                        login_field.setText("");
                        haslo_field.setText("");
                        statement.close();
                        connection.close();


                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }


            }else{
                    System.out.println("Podales puste pole");
                    }

            }
        });
        rejestracjaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {

                    dispose();
                    RejestracjaBox dialog = new RejestracjaBox();

                    dialog.pack();
                    dialog.setVisible(true);
                } catch (Exception ex) {
                    System.out.println("Test");
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
        Loginbox dialog = new Loginbox();
        dialog.pack();
        dialog.setVisible(true);
   //     System.exit(0);
    }
}
