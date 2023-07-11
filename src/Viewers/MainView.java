package Viewers;

import Exceptions.ExceptionFeature;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MainView extends JFrame {
    private JButton Admin;
    private JComboBox Table;
    private JButton Show;



    public MainView(){
        this.setBounds(100, 100, 450,350);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        Admin = new JButton("Admin?");
        Admin.setFont(new Font("Tahoma", Font.PLAIN, 15));
        Admin.setBounds(300, 20, 100, 50);
         Admin.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 AdminLogIn adminLogIn = new AdminLogIn();
             }
         });

        this.getContentPane().add(Admin);

        Table = new JComboBox();
        Table.setFont(new Font("Tahoma", Font.PLAIN, 15));
        Table.setBounds(100 , 50, 150, 25);
        Table.addItem("Teams");
        Table.addItem("People");
        Table.addItem("Performance");
        this.getContentPane().add(Table);

        Show = new JButton("Go");
        Show.setFont(new Font("Tahoma", Font.PLAIN, 15));
        Show.setBounds(137, 100, 75, 25);
        Show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                if(Table.getSelectedItem().equals("People")){
                    PlayerView playerView = new PlayerView();
                }
                else if(Table.getSelectedItem().equals("Teams")){
                    TeamView teamView = new TeamView();
                }else {
                    throw new ExceptionFeature("Feature not developed yet!");
                }
                } catch (ExceptionFeature e1){
                    JOptionPane.showMessageDialog(new JFrame(), "Feature not developed yet!", "ERROR!", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        this.getContentPane().add(Show);

        this.setVisible(true);
    }


}
