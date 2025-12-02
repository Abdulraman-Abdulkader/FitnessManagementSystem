import java.sql.*;
import java.util.Scanner;

public class TrainerServiceDAO {

    // 5. View Schedule
    public void viewSchedule(int trainerId) {
        String sql = "SELECT class_id, class_name, scheduled_time FROM GroupClass WHERE trainer_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, trainerId);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- Assigned Classes ---");
            while (rs.next()) {
                System.out.println("Class ID: " + rs.getInt("class_id") +
                        " | " + rs.getString("class_name") +
                        " | " + rs.getTimestamp("scheduled_time"));
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    // 6. View Members in Class
    public void memberLookup() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter member FIRST name: ");
        String fn = sc.nextLine().trim();

        System.out.print("Enter member LAST name: ");
        String ln = sc.nextLine().trim();

        String sql =
                "SELECT * FROM MemberFullInfo " +
                        "WHERE LOWER(first_name)=LOWER(?) AND LOWER(last_name)=LOWER(?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, fn);
            ps.setString(2, ln);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                System.out.println("No member found with that name. ");
                return;
            }

            System.out.println("\n--- MEMBER LOOKUP RESULT ---");
            System.out.println("ID: " + rs.getInt("member_id"));
            System.out.println("Name: " + rs.getString("first_name") + " " + rs.getString("last_name"));

            System.out.println("\n--- FITNESS GOALS ---");
            System.out.println("Weight Target: " + rs.getString("weight_target"));
            System.out.println("Fat Target: " + rs.getString("fat_target"));

            System.out.println("\n--- HEALTH METRICS ---");
            System.out.println("Current Weight: " + rs.getString("current_weight"));
            System.out.println("Current Body Fat: " + rs.getString("current_body_fat"));
            System.out.println("Current Heart Rate: " + rs.getString("current_heart_rate"));

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
