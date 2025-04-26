package ucdf2307ict_oop;

public abstract class User {
    protected String id;
    protected String username;
    protected String password;
    
    public User(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
    
    // Common getters
    public String getId() {
        return id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    // Abstract methods
    public abstract boolean authenticate(String password);
    public abstract boolean validateId();
    public abstract boolean validatePassword();
    public abstract void logout();
}

