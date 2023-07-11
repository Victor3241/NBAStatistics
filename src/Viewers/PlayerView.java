package Viewers;

import Exceptions.*;
import Tables.People;
import Tables.Players;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class PlayerView extends JFrame {
    private Checkbox stats;
    private Checkbox advancedStats;
    private Checkbox coach;
    private TextField searchByName;
    private TextField searchByTeam;
    private Players players[];
    private People coaches[];
    private int index = 0;
    private JButton button;

    private JTextArea playerText;

    private JScrollPane scrollPane;

    private JButton back;

    private JLabel searchByNameLabel;

    private  JLabel searchByTeamLabel;

    public PlayerView() {
        this.setBounds(100, 100, 650,650);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.getContentPane().setLayout(null);

        stats = new Checkbox("Stats");
        stats.setBounds(100, 25, 50, 20);
        this.getContentPane().add(stats);

        advancedStats = new Checkbox("Advanced Stats");
        advancedStats.setBounds(200, 25, 100, 20);
        this.getContentPane().add(advancedStats);

        coach = new Checkbox("Coach");
        coach.setBounds(320, 25, 100, 20);
        this.getContentPane().add((coach));


        searchByNameLabel = new JLabel("Search By Name");
        searchByNameLabel.setBounds(100, 145, 200, 50);
        this.getContentPane().add(searchByNameLabel);

        searchByName = new TextField();
        searchByName.setBounds(100,75,100,50);
        this.getContentPane().add(searchByName);


        searchByTeamLabel = new JLabel("Search By Team");
        searchByTeamLabel.setBounds(300, 145, 200, 50);
        this.getContentPane().add(searchByTeamLabel);

        searchByTeam = new TextField();
        searchByTeam.setBounds(300,75,100,50);
        this.getContentPane().add(searchByTeam);

        this.players = new Players[101];
        this.coaches = new People[101];

        playerText = new JTextArea();
        playerText.setBounds(50,250, 550,225);
        this.getContentPane().add(playerText);

        scrollPane = new JScrollPane(playerText);
        scrollPane.setBounds(50,250, 550,225);
        this.getContentPane().add(scrollPane);

        button = new JButton("SHOW");
        button.setBounds(480,75,73,30);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getPlayers();
            }
        });
        this.getContentPane().add(button);

        back = new JButton("BACK");
        back.setBounds(500, 550, 100, 50);
        this.getContentPane().add(back);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });



        this.setVisible(true);
    }

    private void addPlayer(Players player){
        if(index < this.players.length){
            this.players[index++] = player;
        }
    }

    private void addPerson(People person){
        if(index < this.coaches.length){
            this.coaches[index++] = person;
        }
    }
    private void getPlayers(){

        this.index = 0;

        try{
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String user = "postgres";
            String pass = "victor";

            String teamSearch = " ";
            if(searchByTeam.getText() != null){
                teamSearch = searchByTeam.getText();
            }

            String nameSearch = " ";
            if(searchByName.getText() != null){
                nameSearch = searchByName.getText();
            }

            Connection connection = DriverManager.getConnection(url,user,pass);
            Statement statement = connection.createStatement();

            String getPlayers = new String();

            if(!coach.getState()) {
                if (stats.getState()) {
                    if (!advancedStats.getState()) {
                        getPlayers = "SELECT people.\"name\", people.\"age\", team.\"teamName\", positions.\"position\", players.\"ppG\", players.\"apG\", players.\"rpG\" from \"People\" people " +
                                "JOIN  \"Teams\" team ON people.\"teamId\" = team.\"teamId\" " +
                                " JOIN \"Players\" players ON players.\"personId\" = people.\"personId\" JOIN \"Positions\" positions ON positions.\"positionId\" = players.\"positionId\" " +
                                " WHERE people.\"role\" = 'Player' AND people.\"name\" LIKE '%" + nameSearch + "%' AND  team.\"teamName\" LIKE '%" + teamSearch + "%'";
                    } else {
                        getPlayers = "SELECT people.\"name\", people.\"age\", team.\"teamName\", positions.\"position\", players.\"ppG\", players.\"apG\", players.\"rpG\", advancedStats.\"spG\", advancedStats.\"bpG\", advancedStats.\"per\" " +
                                " from \"People\" people JOIN  \"Teams\" team ON people.\"teamId\" = team.\"teamId\" " +
                                " JOIN \"Players\" players ON players.\"personId\" = people.\"personId\" JOIN \"Positions\" positions ON positions.\"positionId\" = players.\"positionId\" " +
                                "JOIN \"AdvancedStats\" advancedStats ON advancedStats.\"playerId\" = players.\"playerId\"" +
                                " WHERE people.\"role\" = 'Player' AND people.\"name\" LIKE '%" + nameSearch + "%' AND  team.\"teamName\" LIKE '%" + teamSearch + "%' " +
                                "ORDER BY players.\"ppG\" DESC";
                    }
                } else {
                    if (!advancedStats.getState()) {
                        getPlayers = "SELECT people.\"name\", people.\"age\", team.\"teamName\", positions.\"position\" from \"People\" people " +
                                " JOIN \"Players\" players ON players.\"personId\" = people.\"personId\"" +
                                " JOIN \"Positions\" positions ON positions.\"positionId\" = players.\"positionId\" " +
                                " JOIN \"Teams\" team ON team.\"teamId\" = people.\"teamId\" " +
                                " WHERE people.\"role\" = 'Player' AND people.\"name\" LIKE '%" + nameSearch + "%' AND  team.\"teamName\" LIKE '%" + teamSearch + "%' " +
                                "ORDER BY players.\"ppG\" DESC";
                    } else
                        getPlayers = "SELECT people.\"name\", people.\"age\", team.\"teamName\", positions.\"position\", advancedStats.\"spG\", advancedStats.\"bpG\", advancedStats.\"per\" " +
                                " from \"People\" people JOIN  \"Teams\" team ON people.\"teamId\" = team.\"teamId\" " +
                                " JOIN \"Players\" players ON players.\"personId\" = people.\"personId\" JOIN \"Positions\" positions ON positions.\"positionId\" = players.\"positionId\" " +
                                "JOIN \"AdvancedStats\" advancedStats ON advancedStats.\"playerId\" = players.\"playerId\"" +
                                " WHERE people.\"role\" = 'Player' AND people.\"name\" LIKE '%" + nameSearch + "%' AND  team.\"teamName\" LIKE '%" + teamSearch + "%' " +
                                "ORDER BY advancedStats.\"per\" DESC";
                }
            }
            else{
                if(stats.getState() || advancedStats.getState()){
                    throw new ExceptionCoach("Coaches don't have stats");
                }
                else
                    getPlayers = "SELECT people.\"name\", people.\"age\", team.\"teamName\" from \"People\" people " +
                            " JOIN \"Teams\" team ON team.\"teamId\" = people.\"teamId\" " +
                            " WHERE people.\"role\" = 'Coach' AND people.\"name\" LIKE '%" + nameSearch + "%' AND  team.\"teamName\" LIKE '%" + teamSearch + "%'";
            }

            //String getEmailAndPasswordQuery = "select \"username\",\"password\" from "+tableName+" where username = '"+username+"'";
            statement.execute(getPlayers);

            ResultSet resultSet = statement.getResultSet();

            if(!resultSet.next()) {
                throw new ExceptionInvalidSearch("Invalid Search!");
            }

            do{
                if(!coach.getState()) {
                    Players player = new Players();
                    player.setRole("Player");
                    player.setName(resultSet.getString("name"));
                    player.setTeam(resultSet.getString("teamName"));
                    player.setPosition(resultSet.getString("position"));
                    player.setAge(resultSet.getInt("age"));
                    if (stats.getState()) {
                        player.setPpG(resultSet.getFloat("ppG"));
                        player.setApG(resultSet.getFloat("apG"));
                        player.setRpG(resultSet.getFloat("rpG"));
                    }
                    if (advancedStats.getState()) {
                        player.setPer(resultSet.getFloat("per"));
                        player.setSpG(resultSet.getFloat("spG"));
                        player.setbPg(resultSet.getFloat("bpG"));
                    }
                    addPlayer(player);
                }
                else {
                    People person = new People();
                    person.setRole("Coach");
                    person.setAge(resultSet.getInt("age"));
                    person.setName(resultSet.getString("name"));
                    person.setTeam(resultSet.getString("teamName"));
                    addPerson(person);
                }

            }while(resultSet.next());

            String Text = new String();
            if(!coach.getState()) {
                if (!stats.getState()) {
                    if (!advancedStats.getState()) {
                        Text = Text + "NAME     AGE     POSITION" + System.lineSeparator();
                        for (int i = 0; i < this.index; i++) {
                            Text = Text + this.players[i].toString1();
                            System.out.println(this.players[i].getName() + " " + this.players[i].getTeam());
                        }
                    } else {
                        Text = Text + "NAME     PER     SPG     BPG" + System.lineSeparator();
                        for (int i = 0; i < this.index; i++) {
                            Text = Text + this.players[i].toString3();
                            System.out.println(this.players[i].getName() + " " + this.players[i].getTeam());
                        }
                    }
                } else {
                    if (!advancedStats.getState()) {
                        Text = Text + "NAME     PPG     APG     RPG" + System.lineSeparator();
                        for (int i = 0; i < this.index; i++) {
                            Text = Text + this.players[i].toString2();
                            System.out.println(this.players[i].getName() + " " + this.players[i].getTeam());
                        }
                    } else {
                        Text = Text + "NAME     PPG     APG     RPG     PER     SPG     BPG" + System.lineSeparator();
                        for (int i = 0; i < this.index; i++) {
                            Text = Text + this.players[i].toString4();
                            System.out.println(this.players[i].getName() + " " + this.players[i].getTeam());
                        }
                    }
                }
            }
            else{
                Text = Text + "NAME     AGE     TEAMNAME" + System.lineSeparator();
                for (int i = 0; i < this.index; i++) {
                    Text = Text + this.coaches[i].toStringCoach();
                    System.out.println(this.coaches[i].getName() + " " + this.coaches[i].getTeam());
                }

            }
            playerText.setText(Text);
            playerText.setLineWrap(true);
            this.players = new Players[101];

        }
        catch (SQLException e) {
            System.out.println(e);
        } catch (ExceptionCoach e) {
            JOptionPane.showMessageDialog(new JFrame(), "Coaches don't have stats!", "ERROR!", JOptionPane.WARNING_MESSAGE);
        }catch (ExceptionInvalidSearch e) {
            JOptionPane.showMessageDialog(new JFrame(), "Invalid search!", "ERROR!", JOptionPane.WARNING_MESSAGE);
        }
    }
}
