import java.sql.*;
import java.util.Scanner;

public class MemberServiceDAO {

    // 1. Register Member
    public void registerMember(String fn, String ln, String email, String dob, String gender) {
        String sql = "INSERT INTO Member (first_name, last_name, email, date_of_birth, gender) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, fn);
            ps.setString(2, ln);
            ps.setString(3, email);
            ps.setDate(4, Date.valueOf(dob));
            ps.setString(5, gender);

            ps.executeUpdate();
            System.out.println("Member registered successfully!");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    // 2. Update Member Profile
    public void updateProfile(int memberId) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nUpdating profile info...");

        System.out.print("First name: ");
        String fn = sc.nextLine();

        System.out.print("Last name: ");
        String ln = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("DOB (YYYY-MM-DD): ");
        String dob = sc.nextLine();

        System.out.print("Gender (M/F): ");
        String gender = sc.nextLine();

        String sql = "UPDATE Member SET first_name=?, last_name=?, email=?, date_of_birth=?, gender=? WHERE member_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, fn);
            ps.setString(2, ln);
            ps.setString(3, email);
            ps.setDate(4, Date.valueOf(dob));
            ps.setString(5, gender);
            ps.setInt(6, memberId);
            ps.executeUpdate();

            System.out.println("Profile updated successfully!");

        } catch (Exception e) {
            System.out.println("Error updating profile: " + e.getMessage());
        }
    }

    // ---------------- UPDATE FITNESS GOALS ----------------
    public void updateFitnessGoals(int memberId) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\nUpdating fitness goals...");

        System.out.print("Weight Target (kg): ");
        double wt = Double.parseDouble(sc.nextLine());

        System.out.print("Fat Target (%): ");
        double ft = Double.parseDouble(sc.nextLine());

        String sql =
                "UPDATE FitnessGoal SET weight_target=?, fat_target=? WHERE member_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, wt);
            ps.setDouble(2, ft);
            ps.setInt(3, memberId);

            int rows = ps.executeUpdate();

            // If no goal exists, insert instead
            if (rows == 0) {
                PreparedStatement insert = conn.prepareStatement(
                        "INSERT INTO FitnessGoal (member_id, weight_target, fat_target) VALUES (?, ?, ?)"
                );
                insert.setInt(1, memberId);
                insert.setDouble(2, wt);
                insert.setDouble(3, ft);
                insert.executeUpdate();

                System.out.println("Fitness goals created successfully!");

            } else {
                System.out.println("Fitness goals updated successfully!");
            }

        } catch (Exception e) {
            System.out.println("Error updating fitness goals: " + e.getMessage());
        }
    }

    public void updateHealthMetrics(int memberId) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\nUpdating health metrics...");

        System.out.print("Current Weight: ");
        double weight = Double.parseDouble(sc.nextLine());

        System.out.print("Current Body Fat: ");
        double fat = Double.parseDouble(sc.nextLine());

        System.out.print("Current Heart Rate: ");
        int hr = Integer.parseInt(sc.nextLine());

        String sql =
                "UPDATE HealthMetric SET current_weight=?, current_body_fat=?, current_heart_rate=? WHERE member_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, weight);
            ps.setDouble(2, fat);
            ps.setInt(3, hr);
            ps.setInt(4, memberId);

            int rows = ps.executeUpdate();

            // If no metric exists, insert instead
            if (rows == 0) {
                PreparedStatement insert = conn.prepareStatement(
                        "INSERT INTO HealthMetric (member_id, current_weight, current_body_fat, current_heart_rate) VALUES (?, ?, ?, ?)"
                );
                insert.setInt(1, memberId);
                insert.setDouble(2, weight);
                insert.setDouble(3, fat);
                insert.setInt(4, hr);
                insert.executeUpdate();

                System.out.println("Health metrics created successfully!");
            } else {
                System.out.println("Health metrics updated successfully!");
            }

        } catch (Exception e) {
            System.out.println("Error updating health metrics: " + e.getMessage());
        }
    }


    // 3. Dashboard (Health, Goals, Upcoming Classes)
    public void dashboard(int memberId) {
        try (Connection conn = DBConnection.getConnection()) {

            System.out.println("\n--- HEALTH METRICS ---");
            PreparedStatement ps1 = conn.prepareStatement(
                    "SELECT * FROM HealthMetric WHERE member_id=?");
            ps1.setInt(1, memberId);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                System.out.println("Weight (kg): " + rs1.getDouble("current_weight"));
                System.out.println("Body Fat (%): " + rs1.getDouble("current_body_fat"));
                System.out.println("Heart Rate (bpm): " + rs1.getInt("current_heart_rate"));
            }

            System.out.println("\n--- FITNESS GOALS ---");
            PreparedStatement ps2 = conn.prepareStatement(
                    "SELECT * FROM FitnessGoal WHERE member_id=?");
            ps2.setInt(1, memberId);
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                System.out.println("Weight Target (kg): " + rs2.getDouble("weight_target"));
                System.out.println("Fat Target (%): " + rs2.getDouble("fat_target"));
            }

            System.out.println("\n--- UPCOMING CLASSES ---");
            PreparedStatement ps3 = conn.prepareStatement(
                    "SELECT c.class_name, c.scheduled_time " +
                            "FROM GroupClass c JOIN ClassRegistration cr " +
                            "ON c.class_id = cr.class_id WHERE cr.member_id=? " +
                            "ORDER BY scheduled_time");

            ps3.setInt(1, memberId);
            ResultSet rs3 = ps3.executeQuery();
            while (rs3.next()) {
                System.out.println(rs3.getString("class_name") + " at " +
                        rs3.getTimestamp("scheduled_time"));
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    // 4. Register for Class
    public void registerForClass(int memberId, int classId) {
        String sql = "INSERT INTO ClassRegistration (member_id, class_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, memberId);
            ps.setInt(2, classId);
            ps.executeUpdate();

            System.out.println("Member registered for class!");

        } catch (Exception e) {
            System.out.println("Error: This member is already registered in this class.");
        }
    }
}
