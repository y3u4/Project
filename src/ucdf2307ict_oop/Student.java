package ucdf2307ict_oop;

import java.io.*;

public class Student extends User {
    // Constructor
    public Student(String id, String username, String password) {
        super(id, username, password);
    }
    
    @Override
    public boolean authenticate(String password) {
        return this.password.equals(password);
    }
    
    @Override
    public boolean validateId() {
        return id.matches("TP\\d{6}");
    }
    
    @Override
    public boolean validatePassword() {
        return password.length() >= 8 &&
               password.matches(".*[A-Z].*") &&
               password.matches(".*[a-z].*") &&
               password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*");
    }
    
    @Override
    public void logout() {
        System.out.println("Student logged out successfully");
    }
    
    // Static method to find a student in the database
    public static Student findStudent(String studentId) {
        try (BufferedReader br = new BufferedReader(new FileReader("studentInfo.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(studentId)) {
                    return new Student(parts[0], parts[1], parts[2]);
                }
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }
}
