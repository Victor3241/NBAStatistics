package Viewers;

import Exceptions.*;
import Tables.People;
import org.w3c.dom.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddPeople extends JFrame {

    private JLabel nameLabel;
    private JTextField name;
    private JLabel ageLabel;
    private JSpinner age;
    private JLabel roleLabel;
    private JTextField role;
    private JLabel teamLabel;
    private JTextField team;

    private JButton ADD;

    private JButton back;
    public AddPeople(){
        this.setBounds(100, 100, 650,550);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.getContentPane().setLayout(null);

        name = new JTextField();
        name.setBounds(20, 20, 100, 50);
        this.getContentPane().add(name);

        nameLabel = new JLabel("NAME");
        nameLabel.setBounds(140, 20, 100, 50);
        this.getContentPane().add(nameLabel);

        age = new JSpinner();
        age.setBounds(20, 120, 100, 50);
        this.getContentPane().add(age);

        ageLabel = new JLabel("AGE");
        ageLabel.setBounds(140, 120, 100, 50);
        this.getContentPane().add(ageLabel);

        role = new JTextField();
        role.setBounds(20, 220, 100, 50);
        this.getContentPane().add(role);

        roleLabel = new JLabel("ROLE");
        roleLabel.setBounds(140, 220, 100, 50);
        this.getContentPane().add(roleLabel);


        team = new JTextField();
        team.setBounds(20, 320, 100, 50);
        this.getContentPane().add(team);

        teamLabel = new JLabel("TEAM");
        teamLabel.setBounds(140, 320, 100, 50);
        this.getContentPane().add(teamLabel);


        ADD = new JButton("ADD");
        ADD.setBounds(20, 420, 100, 50);
        this.getContentPane().add(ADD);
        ADD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPerson();
            }
        });

        back = new JButton("BACK");
        back.setBounds(500, 450, 100, 50);
        this.getContentPane().add(back);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        this.setVisible(true);
    }

    public void addPerson(){
        People person = new People();
        try{
            if(name.getText().equals("")){
                throw new ExceptionInvalidName("You need a name!");
            }
            String personName = name.getText();

            if(age.getValue().equals(0)){
                throw new ExceptionInvalidAge("You need to innput age!");
            }
            int personAge = (int) age.getValue();

            if(role.getText().equals("")) {
                throw new ExceptionInvalidRole("You need a role!");
            }
            String personRole = role.getText();

            if(team.getText().equals("")){
                throw new ExceptionInvalidTeam("You need a team!");
            }
            String personTeam = team.getText();

            String url = "jdbc:postgresql://localhost:5432/postgres";
            String user = "postgres";
            String pass = "victor";

            int teamIndex;

            Connection connection = DriverManager.getConnection(url,user,pass);
            Statement statement = connection.createStatement();

            String getTeamIndex = "SELECT \"teamId\" FROM \"Teams\" WHERE  \"teamName\" = '" + personTeam + "'";

            statement.execute(getTeamIndex);

            ResultSet resultSet = statement.getResultSet();

            if(!resultSet.next()) {
                throw new ExceptionNoTeam("There is no team with this name!");
            }

            teamIndex = resultSet.getInt("teamId");
            System.out.println(teamIndex);

            String addPerson = "INSERT INTO \"People\"(\"personId\", \"age\", \"name\", \"role\", \"teamId\") VALUES (DEFAULT, " + personAge + ", '" + personName + "', '" + personRole + "'," + teamIndex + ")";

            statement.execute(addPerson);

            throw new ExceptionOK("OK!");


        } catch (ExceptionInvalidName e) {
            JOptionPane.showMessageDialog(new JFrame(), "You need a name!", "ERROR!", JOptionPane.WARNING_MESSAGE);
        } catch (ExceptionInvalidAge e){
            JOptionPane.showMessageDialog(new JFrame(), "You need to input age!", "ERROR!", JOptionPane.WARNING_MESSAGE);
        } catch (ExceptionInvalidRole e) {
            JOptionPane.showMessageDialog(new JFrame(), "You need a role!", "ERROR!", JOptionPane.WARNING_MESSAGE);
        } catch (ExceptionInvalidTeam e) {
            JOptionPane.showMessageDialog(new JFrame(), "You need a team!", "ERROR!", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException e){
            System.out.println(e);
        } catch (ExceptionNoTeam e) {
            JOptionPane.showMessageDialog(new JFrame(), "There is no team with this name!", "ERROR!", JOptionPane.WARNING_MESSAGE);
        } catch (ExceptionOK e) {
            JOptionPane.showMessageDialog(new JFrame(), "OK!", "ERROR!", JOptionPane.WARNING_MESSAGE);
        }
    }

}
