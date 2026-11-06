import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MessageController {

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/text_connect_db";
    private static final String USER = "root"; 
    private static final String PASSWORD = "your_password"; 

    // Method to save text messages into MySQL
    public boolean saveMessage(String sender, String textMessage) {
        String insertQuery = "INSERT INTO user_messages (sender_name, message_text) VALUES (?, ?)";
        
        // Using try-with-resources for automatic resource closing
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            
            // Setting values to prevent SQL injection (Best practice!)
            preparedStatement.setString(1, sender);
            preparedStatement.setString(2, textMessage);
            
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0; // Returns true if saved successfully
            
        } catch (SQLException e) {
            System.out.println("Database Connection Error: " + e.getMessage());
            return false;
        }
    }

    // Main method to test logic independently on your phone/browser IDE
    public static void main(String[] args) {
        MessageController controller = new MessageController();
        
        // Simulating data received from the HTML frontend
        String testSender = "MCA Graduate";
        String testMessage = "This text is safely processed by Java and saved in MySQL.";
        
        boolean success = controller.saveMessage(testSender, testMessage);
        
        if (success) {
            System.out.println("Success: Text data successfully mapped and saved!");
        } else {
            System.out.println("Failure: Could not save data.");
        }
    }
}
