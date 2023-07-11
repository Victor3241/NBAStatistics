package Viewers;


import Exceptions.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.sql.*;

public class AdminLogIn extends JFrame{
    private JButton LogIn;

    private JTextField Username;
    private JTextField Password;

    private JLabel message;
    private JLabel UsernameLabel;
    private JLabel PasswordLabel;

    private ImageIcon imageIcon;
    private JLabel image;


    public AdminLogIn(){
        Color c1 = new Color(169, 187, 252);
        Color c2 = new Color(255, 221, 221);

        this.setBounds(100, 100, 450, 500);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.getContentPane().setBackground(c1);
        this.getContentPane().setLayout(null);

        imageIcon = new ImageIcon("Nba-Logo.png");
        image = new JLabel(imageIcon);
        image.setBounds(10, 5, 267, 150);
        this.getContentPane().add(image);

        UsernameLabel = new JLabel("Username");
        UsernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        UsernameLabel.setBounds(178, 160, 200, 40);
        this.getContentPane().add(UsernameLabel);

        Username = new JTextField();
        Username.setFont(new Font("Tahoma", Font.PLAIN, 15));
        Username.setBounds(125, 200, 200, 40);
        Username.setBackground(c2);
        this.getContentPane().add(Username);

        PasswordLabel = new JLabel("Password");
        PasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        PasswordLabel.setBounds(178, 250, 200, 40);
        this.getContentPane().add(PasswordLabel);

        Password = new JPasswordField();
        Password.setFont(new Font("Tahoma", Font.PLAIN, 15));
        Password.setBounds(125, 290, 200, 40);
        Password.setBackground(c2);
        this.getContentPane().add(Password);

        LogIn = new JButton("Login");
        LogIn.setFont(new Font("Tahoma", Font.PLAIN, 15));
        LogIn.setBounds(175, 380, 100, 30);
        LogIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkLogin(Username.getText(), Password.getText());
            }
        });
        this.getContentPane().add(LogIn);

        this.setVisible(true);
    }

    private void checkLogin(String username,String password){

        try {
            if (username.isEmpty() || password.isEmpty()) {
                throw new ExceptionMissingDetail("Missing details!");
            }

            String url = "jdbc:postgresql://localhost:5432/postgres";
            String user = "postgres";
            String pass = "victor";

            String tableName = "\"Account\"";

            Connection connection = DriverManager.getConnection(url,user,pass);
            Statement statement = connection.createStatement();

            String getEmailAndPasswordQuery = "select \"username\",\"password\" from "+tableName+" where username = '"+username+"'";
            statement.execute(getEmailAndPasswordQuery);

            ResultSet resultSet = statement.getResultSet();

            if(!resultSet.next()){
                throw new ExceptionInvalidUsername("Invalid email!");
            }

            String checkPassword = password;
            if(!resultSet.getString("password").contains(checkPassword) ||
                    resultSet.getString("password").length() != checkPassword.length()){
                throw new ExceptionIncorrectPassword("Wrong password!");
            }

            AddDelete menu = new AddDelete();
            dispose();

        }
        catch(ExceptionMissingDetail e){
            JOptionPane.showMessageDialog(new JFrame(),"Required fields are empty!","ERROR",JOptionPane.WARNING_MESSAGE);
        } catch (SQLException e) {
            System.out.println(e);
        } catch (ExceptionInvalidUsername e) {
            JOptionPane.showMessageDialog(new JFrame(),"No account associated with this username!","Wrong username!",JOptionPane.WARNING_MESSAGE);
        } catch (ExceptionIncorrectPassword e) {
            JOptionPane.showMessageDialog(new JFrame(),"The password is incorrect!!","Wrong Password!",JOptionPane.WARNING_MESSAGE);
        }

    }
}
