package Tables;

public class Team {
    String city;
    String stadium;
    int stadiumCapacity;
    String homeJersey;
    String awayJersey;
    String teamName;

    public Team(){

    }

    public String getCity() {
        return city;
    }

    public String getStadium() {
        return stadium;
    }

    public int getStadiumCapacity() {
        return stadiumCapacity;
    }

    public String getHomeJersey() {
        return homeJersey;
    }

    public String getAwayJersey() {
        return awayJersey;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public void setStadiumCapacity(int stadiumCapacity) {
        this.stadiumCapacity = stadiumCapacity;
    }

    public void setHomeJersey(String homeJersey) {
        this.homeJersey = homeJersey;
    }

    public void setAwayJersey(String awayJersey) {
        this.awayJersey = awayJersey;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String toString1() {
        return teamName + System.lineSeparator();
    }
    public String toString2(){
        return teamName + "    " + city + "    " + stadium + " " + stadiumCapacity + System.lineSeparator();
    }
    public String toString3(){
        return teamName + "    " + homeJersey + " " + awayJersey + System.lineSeparator();
    }
    public String toString4(){
        return teamName + "   " + city + "    " + stadium + " " + stadiumCapacity + "    " + homeJersey + " " + awayJersey + System.lineSeparator();
    }
}
