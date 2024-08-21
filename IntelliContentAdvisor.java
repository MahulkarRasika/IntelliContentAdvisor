import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IntelliContentAdvisor {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/jdbc_Schema";
        String username = "root";
        String password = "root";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {

            IntelliContentAdvisor.createUser(connection, " Diya Doe", "Heath,Technology");
            IntelliContentAdvisor.addContent(connection, "Tech trends 2024", "Latest trend in Tech", "tech_trends.jpg", "article");
            IntelliContentAdvisor.addContent(connection, "How to Stay Fit", "Fitness tips and tricks", "fitness_tips.jpg", "video");
            System.out.println("***-----------------------------------**--------------------------");

            IntelliContentAdvisor.interactions(connection, 1, 1, "viewed");
            List<String> historyAfterFirstInteraction = IntelliContentAdvisor.getUserInteractionHistory(connection, 1);
            System.out.println("History after first interaction: " + historyAfterFirstInteraction);
            System.out.println("-----------------------------------------------------------------");

            IntelliContentAdvisor.interactions(connection, 1, 2, "liked");
            List<String> historyAfterSecondInteraction = IntelliContentAdvisor.getUserInteractionHistory(connection, 1);
            System.out.println("History after second interaction: " + historyAfterSecondInteraction);
            System.out.println("-----------------------------------------------------------------");

            // Get recommendations
            List<String> recommendations = IntelliContentAdvisor.getUserInteractionHistory(connection, 1);
            System.out.println("Recommended Content: " + recommendations);
            System.out.println("-----------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Creating the user
    public static void createUser(Connection con, String username, String preferences) {
        String sql = "INSERT INTO users (username, preferences) VALUES (?, ?)";

        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, username);
            stm.setString(2, preferences);
            stm.executeUpdate();
            System.out.println("User added successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Add content
    public static void addContent(Connection con, String title, String description, String image_url, String type) {
        String sql = "INSERT INTO content (title, description, image_url, type) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, title);
            stm.setString(2, description);
            stm.setString(3, image_url);
            stm.setString(4, type);
            stm.executeUpdate();
            System.out.println("Content added successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Recording user interaction
    public static void interactions(Connection con, int user_id, int content_id, String interaction_type) {
        String sql = "INSERT INTO interactions (user_id, content_id, interaction_type, timestamp) VALUES (?, ?, ?, NOW())";

        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, user_id);
            stm.setInt(2, content_id);
            stm.setString(3, interaction_type);
            stm.executeUpdate();
            System.out.println("Interaction recorded successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    // Fetching the history of the user
    public static List<String> getUserInteractionHistory(Connection connection, int user_id) {
        List<String> history = new ArrayList<>();

        String sql = "SELECT c.title, i.interaction_type, i.timestamp FROM interactions i " +
                "JOIN content c ON i.content_id = c.id WHERE i.user_id = ? ORDER BY i.timestamp DESC";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, user_id);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                String title = rs.getString("title");
                String interactionType = rs.getString("interaction_type");
                String timestamp = rs.getString("timestamp");
                history.add("Content: " + title + ", Interaction: " + interactionType + ", Time: " + timestamp);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return history;
    }
}
