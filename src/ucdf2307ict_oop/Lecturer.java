package ucdf2307ict_oop;

public class Lecturer extends User {

    // Constructor
    public Lecturer(String staffID, String username, String password) {
        super(staffID, username, password);
    }

    // Implement abstract methods
    @Override
    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    @Override
    public boolean validateId() {
        // Example validation: ID must start with "L" followed by numbers
        return this.id != null && this.id.matches("L\\d+");
    }

    @Override
    public boolean validatePassword() {
        // Example validation: Password must be at least 6 characters long
        return this.password != null && this.password.length() >= 6;
    }

    @Override
    public void logout() {
        System.out.println("Lecturer " + this.username + " has logged out.");
    }
}






