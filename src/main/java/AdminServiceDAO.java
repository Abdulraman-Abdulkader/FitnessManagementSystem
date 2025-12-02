import java.sql.*;

public class AdminServiceDAO {

    // 7. Book Room / Create Class
    public void createClass(String className, int trainerId, int roomId, String time, int capacity) {
        String sql = "INSERT INTO GroupClass (class_name, trainer_id, room_id, scheduled_time, capacity) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, className);
            ps.setInt(2, trainerId);
            ps.setInt(3, roomId);
            ps.setTimestamp(4, Timestamp.valueOf(time));
            ps.setInt(5, capacity);

            ps.executeUpdate();
            System.out.println("Class created successfully!");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    // 8. Pay Invoice
    public void payInvoice(int invoiceId) {
        String sql = "UPDATE Invoice SET status='PAID' WHERE invoice_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, invoiceId);
            ps.executeUpdate();

            System.out.println("Invoice marked as PAID.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    // List unpaid invoices
    public void listUnpaidInvoices() {
        String sql = "SELECT invoice_id, member_id, amount FROM Invoice WHERE status='UNPAID'";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n--- UNPAID INVOICES ---");
            while (rs.next()) {
                System.out.println("Invoice: " + rs.getInt("invoice_id") +
                        " | Member: " + rs.getInt("member_id") +
                        " | Amount: $" + rs.getInt("amount"));
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}