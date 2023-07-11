package Viewers;

import Exceptions.ExceptionInvalidSearch;
import Tables.Players;
import Tables.Team;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TeamView extends JFrame{
    private JTextField searchByName;
    private JTextArea teamText;
    private JScrollPane scrollPane;
    private Checkbox locationDetails;
    private Checkbox teamDetails;
    private JButton show;
    private Team teams[];
    private int teamIndex = 0;

    private JLabel searchByNameLabel;
    public TeamView(){
        this.setBounds(100, 100, 650,550);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.getContentPane().setLayout(null);

        locationDetails = new Checkbox("Location Details");
        locationDetails.setBounds(100, 25, 150, 20);
        this.getContentPane().add(locationDetails);

        teamDetails = new Checkbox("Team Details");
        teamDetails.setBounds(250, 25, 100, 20);
        this.getContentPane().add(teamDetails);

        searchByName = new JTextField();
        searchByName.setBounds(250,75,100,50);
        this.getContentPane().add(searchByName);

        searchByNameLabel = new JLabel("Search By Name");
        searchByNameLabel.setBounds(100, 75, 200, 50);
        this.getContentPane().add(searchByNameLabel);

        this.teams = new Team[101];

        teamText = new JTextArea();
        teamText.setBounds(50,250, 550,225);
        this.getContentPane().add(teamText);

        scrollPane = new JScrollPane(teamText);
        scrollPane.setBounds(50,250, 550,225);
        this.getContentPane().add(scrollPane);

        show = new JButton("SHOW");
        show.setBounds(275,150,100,50);
        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getTeams();
            }
        });
        this.getContentPane().add(show);

        this.setVisible(true);
    }

    private void addTeam(Team team){
        if(teamIndex < this.teams.length){
            this.teams[teamIndex++] = team;
        }
    }

    private void getTeams(){
        this.teamIndex = 0;
        try{
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String user = "postgres";
            String pass = "victor";

            String teamSearch = " ";
            if(searchByName.getText() != null)
            {
                teamSearch = searchByName.getText();
            }

            Connection connection = DriverManager.getConnection(url,user,pass);
            Statement statement = connection.createStatement();

            String getTeams = new String();

            if(locationDetails.getState()){
                if(teamDetails.getState()){
                    getTeams = "SELECT team.\"teamName\",locations.\"city\",locations.\"stadium\",locations.\"stadiumCapacity\"," +
                            "awayJersey.\"jerseyColor\",homeJersey.\"jerseyColor\" from \"Teams\" team " +
                            "JOIN \"Locations\" locations ON team.\"locationsId\" = locations.\"locationId\" " +
                            "JOIN \"Jersey\" homeJersey ON team.\"homeJerseyId\" = homeJersey.\"jerseyId\" " +
                            "JOIN \"Jersey\" awayJersey ON team.\"awayJerseyId\" = awayJersey.\"jerseyId\"" +
                            "ORDER BY \"teamName\"";
                }
                else{
                    getTeams = "SELECT team.\"teamName\",locations.\"city\",locations.\"stadium\",locations.\"stadiumCapacity\"" +
                            " from \"Teams\" team " +
                            "JOIN \"Locations\" locations ON team.\"locationsId\" = locations.\"locationId\" " +
                            "ORDER BY \"teamName\"";

                }
            }
            else{
                if(teamDetails.getState()){
                    getTeams = "SELECT team.\"teamName\"," +
                            "awayJersey.\"jerseyColor\",homeJersey.\"jerseyColor\" from \"Teams\" team " +
                            "JOIN \"Jersey\" homeJersey ON team.\"homeJerseyId\" = homeJersey.\"jerseyId\" " +
                            "JOIN \"Jersey\" awayJersey ON team.\"awayJerseyId\" = awayJersey.\"jerseyId\"" +
                            "ORDER BY \"teamName\"";

                }
                else {
                    getTeams = "SELECT \"teamName\" from \"Teams\" ORDER BY \"teamName\"";
                }
            }

            statement.execute(getTeams);
            ResultSet resultSet = statement.getResultSet();

            if(!resultSet.next()){
                throw new ExceptionInvalidSearch("Invalid Search!");
            }

            do{
                Team team = new Team();
                team.setTeamName(resultSet.getString("teamName"));
                if(locationDetails.getState()){
                    team.setCity(resultSet.getString("city"));
                    team.setStadium(resultSet.getString("stadium"));
                    team.setStadiumCapacity(resultSet.getInt("stadiumCapacity"));
                    if(teamDetails.getState()){
                        team.setHomeJersey(resultSet.getString(5));
                        team.setAwayJersey(resultSet.getString(6));
                    }
                }
                else {
                    if (teamDetails.getState()) {
                        team.setHomeJersey(resultSet.getString(2));
                        team.setAwayJersey(resultSet.getString(3));
                    }
                }
                addTeam(team);

            }while(resultSet.next());

            String text = new String();

            if(locationDetails.getState()){
                if(teamDetails.getState()){
                    text = text + "NAME    CITY    STADIUM    STADIUM CAPACITY    HOME    AWAY" + System.lineSeparator();
                    for(int i = 0; i < teamIndex; i++){
                        text = text + teams[i].toString4();
                    }

                }
                else{
                    text = text + "NAME    CITY    STADIUM    STADIUM CAPACITY" + System.lineSeparator();
                    for(int i = 0; i < teamIndex; i++){
                        text = text + teams[i].toString2();
                    }

                }
            }
            else{
                if(teamDetails.getState()){
                    text = text + "NAME    HOME    AWAY" + System.lineSeparator();
                    for(int i = 0; i < teamIndex; i++){
                        text = text + teams[i].toString3();
                    }

                }
                else {
                    text = text + "NAME" + System.lineSeparator();
                    for(int i = 0; i < teamIndex; i++){
                        text = text + teams[i].toString1();
                    }
                }
            }

            teamText.setText(text);
            teamText.setLineWrap(true);
            this.teams = new Team[101];

        }catch(SQLException e){
            System.out.println(e);
        } catch (ExceptionInvalidSearch e) {
            JOptionPane.showMessageDialog(new JFrame(),"Invalid search!","ERROR!",JOptionPane.WARNING_MESSAGE);
        }


    }
}
