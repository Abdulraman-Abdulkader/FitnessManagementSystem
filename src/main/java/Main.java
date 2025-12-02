import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    static MemberServiceDAO memberDAO = new MemberServiceDAO();
    static TrainerServiceDAO trainerDAO = new TrainerServiceDAO();
    static AdminServiceDAO adminDAO = new AdminServiceDAO();

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== FITNESS CLUB SYSTEM =====");
            System.out.println("1. Member Menu");
            System.out.println("2. Trainer Menu");
            System.out.println("3. Admin Menu");
            System.out.println("0. Exit");
            System.out.print("Select: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> memberMenu();
                case 2 -> trainerMenu();
                case 3 -> adminMenu();
                case 0 -> endProgram();
            }
        }
    }


    // ---------------- MEMBER MENU ----------------
    private static void memberMenu() {
        System.out.println("\n--- MEMBER MENU ---");
        System.out.println("1. Register");
        System.out.println("2. Profile Management");
        System.out.println("3. Dashboard");
        System.out.println("4. Register for Class");
        System.out.print("Select: ");

        int c = Integer.parseInt(sc.nextLine());

        switch (c) {
            case 1 -> registerMember();
            case 2 -> updateMember();
            case 3 -> dashboard();
            case 4 -> registerForClass();
        }
    }

    private static void registerMember() {
        System.out.print("First name: "); String fn = sc.nextLine();
        System.out.print("Last name: "); String ln = sc.nextLine();
        System.out.print("Email: "); String email = sc.nextLine();
        System.out.print("DOB (YYYY-MM-DD): "); String dob = sc.nextLine();
        System.out.print("Gender (M/F): "); String g = sc.nextLine();

        memberDAO.registerMember(fn, ln, email, dob, g);
    }

    private static void updateMember() {
        System.out.print("Member ID: ");
        int memberId = Integer.parseInt(sc.nextLine());

        System.out.println("\n--- PROFILE MANAGEMENT ---");
        System.out.println("1. Update Profile Information");
        System.out.println("2. Update Fitness Goals");
        System.out.println("3. Update Health Metrics");
        System.out.print("Select: ");

        int choice = Integer.parseInt(sc.nextLine());

        switch (choice) {
            case 1 -> memberDAO.updateProfile(memberId);
            case 2 -> memberDAO.updateFitnessGoals(memberId);
            case 3 -> memberDAO.updateHealthMetrics(memberId);
        }
    }

    private static void dashboard() {
        System.out.print("Member ID: ");
        int id = Integer.parseInt(sc.nextLine());
        memberDAO.dashboard(id);
    }

    private static void registerForClass() {
        System.out.print("Member ID: "); int mid = Integer.parseInt(sc.nextLine());
        System.out.print("Class ID: "); int cid = Integer.parseInt(sc.nextLine());
        memberDAO.registerForClass(mid, cid);
    }


    // ---------------- TRAINER MENU ----------------
    private static void trainerMenu() {
        System.out.println("\n--- TRAINER MENU ---");
        System.out.println("1. View Assigned Classes");
        System.out.println("2. Member Lookup");
        System.out.print("Select: ");

        int c = Integer.parseInt(sc.nextLine());

        switch (c) {
            case 1 -> {
                System.out.print("Trainer ID: ");
                trainerDAO.viewSchedule(Integer.parseInt(sc.nextLine()));
            }
            case 2 -> {
                trainerDAO.memberLookup();
            }
        }
    }


    // ---------------- ADMIN MENU ----------------
    private static void adminMenu() {
        System.out.println("\n--- ADMIN MENU ---");
        System.out.println("1. Create Class");
        System.out.println("2. Pay Invoice");
        System.out.println("3. List Unpaid Invoices");
        System.out.print("Select: ");

        int c = Integer.parseInt(sc.nextLine());

        switch (c) {
            case 1 -> createClass();
            case 2 -> payInvoice();
            case 3 -> adminDAO.listUnpaidInvoices();
        }
    }

    private static void createClass() {
        System.out.print("Class name: ");
        String cn = sc.nextLine();
        System.out.print("Trainer ID: ");
        int tid = Integer.parseInt(sc.nextLine());
        System.out.print("Room ID: ");
        int rid = Integer.parseInt(sc.nextLine());
        System.out.print("Scheduled time (YYYY-MM-DD HH:MM:SS): ");
        String t = sc.nextLine();
        System.out.print("Capacity: ");
        int cap = Integer.parseInt(sc.nextLine());

        adminDAO.createClass(cn, tid, rid, t, cap);
    }

    private static void payInvoice() {
        System.out.print("Invoice ID: ");
        adminDAO.payInvoice(Integer.parseInt(sc.nextLine()));
    }

    private static void endProgram(){
        System.out.println("Goodbye!!");
        System.exit(0);
    }
}
