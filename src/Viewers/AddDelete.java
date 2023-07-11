package Viewers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Exceptions.*;

public class AddDelete extends JFrame {

    private JComboBox Choose;
    private JButton Add;
    private JButton Delete;
    private JButton back;

    public AddDelete(){
        this.setBounds(100, 100, 650,450);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.getContentPane().setLayout(null);

        Choose = new JComboBox();
        Choose.setFont(new Font("Tahoma", Font.PLAIN, 15));
        Choose.setBounds(100 , 50, 150, 25);
        Choose.addItem("Teams");
        Choose.addItem("People");
        Choose.addItem("Players");
        Choose.addItem("Games");
        Choose.addItem("Performance");
        Choose.addItem("Statistics");
        Choose.addItem("Advanced Stats");
        Choose.addItem("Jersey");
        Choose.addItem("Location");
        this.getContentPane().add(Choose);

        Add = new JButton("Add");
        Add.setFont(new Font("Tahoma", Font.PLAIN, 15));
        Add.setBounds(100, 100, 75, 25);
        Add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                if(Choose.getSelectedItem().equals("People")) {
                    AddPeople addPeople = new AddPeople();
                }
                else {
                    throw new ExceptionFeature("Feature not developed yet!");
                }
                } catch (ExceptionFeature e1){
                    JOptionPane.showMessageDialog(new JFrame(), "Feature not developed yet!", "ERROR!", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        this.getContentPane().add(Add);

        back = new JButton("BACK");
        back.setBounds(500, 450, 100, 50);
        this.getContentPane().add(back);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        Delete = new JButton("Delete");
        Delete.setFont(new Font("Tahoma", Font.PLAIN, 15));
        Delete.setBounds(175, 100, 75, 25);
        Delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (Choose.getSelectedItem().equals("People")) {
                        DeletePeople deletePeople = new DeletePeople();
                    }
                    else {
                        throw new ExceptionFeature("Feature not developed yet!");
                    }
                } catch (ExceptionFeature e1){
                    JOptionPane.showMessageDialog(new JFrame(), "Feature not developed yet!", "ERROR!", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        this.getContentPane().add(Delete);

        this.setVisible(true);
    }
}
