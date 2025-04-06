public class User {
    private int id;
    private String fullName;
    private String username;
    private String phoneNumber;
    private String email;
    private String dob;
    private String password;

    public User(int id, String fullName, String username, String phoneNumber, String email, String dob, String password) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dob = dob;
        this.password = password;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getDob() {
        return dob;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
