package Tables;

public class People {
    String name;
    String team;

    String role;
    int age;

    public People(){
    }

    public People(String name, String team, String role, int age) {
        this.name = name;
        this.team = team;
        this.role = role;
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setRole(String role){
        this.role = role;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public String getRole() {
        return role;
    }

    public int getAge() {
        return age;
    }

    public String toStringCoach(){
        return name + "    " + age + "    " + team + System.lineSeparator();
    }
}
