package Viewers;

import Exceptions.ExceptionMissingDetail;
import Exceptions.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DeletePeople extends JFrame {
    private JSpinner index;
    private JLabel indexToDelete;
    private JButton back;

    private JButton DELETE;

    public DeletePeople(){
        this.setBounds(100, 100, 650,550);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.getContentPane().setLayout(null);

        index = new JSpinner();
        index.setBounds(20, 120, 100, 50);
        this.getContentPane().add(index);

        indexToDelete = new JLabel("Index To Delete");
        indexToDelete.setBounds(140, 120, 200, 50);
        this.getContentPane().add(indexToDelete);

        back = new JButton("BACK");
        back.setBounds(500, 450, 100, 50);
        this.getContentPane().add(back);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        DELETE = new JButton("DELETE");
        DELETE.setBounds(20, 420, 100, 50);
        this.getContentPane().add(DELETE);
        DELETE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePerson();
            }
        });

        this.setVisible(true);
    }

    public void deletePerson(){
        try{
            if(index.getValue().equals(0)){
                throw new ExceptionMissingDetail("Choose an index!");
            }

            String url = "jdbc:postgresql://localhost:5432/postgres";
            String user = "postgres";
            String pass = "victor";

            Connection connection = DriverManager.getConnection(url,user,pass);
            Statement statement = connection.createStatement();

            String verify = "SELECT \"role\" FROM \"People\" WHERE \"personId\" = " + (int)index.getValue();

            statement.execute(verify);

            ResultSet resultSet = statement.getResultSet();

            if(!resultSet.next()) {
                throw new ExceptionNoIndex("There is no person with this index!");
            }

            if(resultSet.getString("role").equals("Coach")) {
                String deleteIndex = "DELETE FROM \"People\" WHERE \"personId\" = " + (int) index.getValue();
                statement.execute(deleteIndex);
            }
            else{
                throw new ExceptionDeletePlayer("Cannot delete players yet!");
            }

        } catch (ExceptionMissingDetail e) {
            JOptionPane.showMessageDialog(new JFrame(), "Choose an index!", "ERROR!", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException e){
            System.out.println(e);
        } catch(ExceptionNoIndex e){
            JOptionPane.showMessageDialog(new JFrame(), "There is no person with this index!", "ERROR!", JOptionPane.WARNING_MESSAGE);
        } catch(ExceptionDeletePlayer e){
            JOptionPane.showMessageDialog(new JFrame(), "Cannot delete players yet!", "ERROR!", JOptionPane.WARNING_MESSAGE);
        }
    }
}
