public class CourierWithoutLogin {
    private String password;
    private String firstName;

    public CourierWithoutLogin(String password, String firstName) {
        this.password = password;
        this.firstName = firstName;
    }

    public CourierWithoutLogin() {

    }

    public String getLogin() {
        return password;
    }

    public void setLogin(String login) {
        this.password = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
