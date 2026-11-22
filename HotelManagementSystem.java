import java.util.Scanner;

public class HotelManagementSystem {

    // Shared Parallel Arrays (Room info, guest info, and availability)
    static int[] roomNumbers = {101, 102, 103, 104, 105};
    static String[] roomTypes = {"Single", "Single", "Double", "Double", "Suite"};
    static double[] roomRates = {1500.00, 1500.00, 2500.00, 2500.00, 4500.00};
    static String[] roomStatus = {"Available", "Available", "Available", "Available", "Available"};
    static String[] guestNames = {"None", "None", "None", "None", "None"};
    static int[] bookedNights = {0, 0, 0, 0, 0};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        mainMenu(sc);
        sc.close();
    }

    // ==========================================
    // 1. MAIN MENU (Member 1)
    // ==========================================
    public static void mainMenu(Scanner sc) {
        int choice;

        do {
            System.out.println("\n=================================================");
            System.out.println("        HOTEL & RESORT RESERVATION SYSTEM");
            System.out.println("=================================================");
            System.out.println("1. Display Rooms & Availability");
            System.out.println("2. Search Room Information");
            System.out.println("3. Book a Room");
            System.out.println("4. Cancel Booking");
            System.out.println("5. Booking & Cost Summary");
            System.out.println("6. Exit");
            System.out.print("Enter your choice (1-6): ");

            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine(); // clear buffer

                if (choice == 1) {
                    displayRoom();
                } else if (choice == 2) {
                    searchRoom(sc);
                } else if (choice == 3) {
                    bookRoom(sc);
                } else if (choice == 4) {
                    cancelBooking(sc);
                } else if (choice == 5) {
                    displaySummary();
                } else if (choice == 6) {
                    System.out.println("\nThank you for using Hotel & Resort Reservation System!");
                } else {
                    System.out.println("\nInvalid choice. Please enter 1-6.");
                }
            } else {
                System.out.println("\nInvalid input! Please enter a number (1-6).");
                sc.nextLine(); // clear invalid input
                choice = 0;
            }
        } while (choice != 6);
    }

    // ==========================================
    // 2. DISPLAY ROOMS (Member 2)
    // ==========================================
    public static void displayRoom() {
        int availableCount = 0;

        System.out.println("\n-----------------------------------------------------------------");
        System.out.println("                         ROOM INVENTORY");
        System.out.println("-----------------------------------------------------------------");
        System.out.printf("%-8s %-10s %-12s %-12s %-15s%n",
                "Room", "Type", "Rate (PHP)", "Status", "Guest");
        System.out.println("-----------------------------------------------------------------");

        for (int i = 0; i < roomNumbers.length; i++) {
            System.out.printf("%-8d %-10s %-12.2f %-12s %-15s%n",
                    roomNumbers[i],
                    roomTypes[i],
                    roomRates[i],
                    roomStatus[i],
                    guestNames[i]);

            if (roomStatus[i].equalsIgnoreCase("Available")) {
                availableCount++;
            }
        }

        System.out.println("-----------------------------------------------------------------");
        System.out.println("Available Rooms: " + availableCount + " out of " + roomNumbers.length);
    }

    // ==========================================
    // SEARCH ROOM
    // ==========================================
    public static void searchRoom(Scanner sc) {
        System.out.print("\nEnter room number to search (101-105): ");
        while (!sc.hasNextInt()) {
            System.out.print("Invalid input. Enter a valid room number: ");
            sc.next();
        }

        int targetRoom = sc.nextInt();
        sc.nextLine();

        int index = findRoomIndex(targetRoom);
        if (index == -1) {
            System.out.println("Room " + targetRoom + " not found.");
        } else {
            System.out.println("\n--- ROOM FOUND ---");
            System.out.println("Room Number : " + roomNumbers[index]);
            System.out.println("Type        : " + roomTypes[index]);
            System.out.printf("Rate/Night  : PHP %.2f%n", roomRates[index]);
            System.out.println("Status      : " + roomStatus[index]);
            System.out.println("Guest       : " + guestNames[index]);
            if (roomStatus[index].equalsIgnoreCase("Booked")) {
                double total = bookedNights[index] * roomRates[index];
                System.out.println("Nights      : " + bookedNights[index]);
                System.out.printf("Total Bill  : PHP %.2f%n", total);
            }
        }
    }

    // ==========================================
    // 3. BOOK A ROOM (Member 3)
    // ==========================================
    public static void bookRoom(Scanner sc) {
        displayRoom();

        System.out.print("\nEnter room number to book (101-105): ");
        if (!sc.hasNextInt()) {
            System.out.println("Invalid room number input.");
            sc.nextLine();
            return;
        }

        int roomNum = sc.nextInt();
        sc.nextLine();
        int index = findRoomIndex(roomNum);

        if (index == -1) {
            System.out.println("Room not found!");
        } else if (roomStatus[index].equalsIgnoreCase("Booked")) {
            System.out.println("Sorry, Room " + roomNumbers[index] + " is already booked by " + guestNames[index] + ".");
        } else {
            System.out.print("Enter guest name: ");
            String guest = sc.nextLine().trim();
            if (guest.isEmpty()) {
                guest = "Guest";
            }

            System.out.print("Enter number of nights: ");
            if (!sc.hasNextInt()) {
                System.out.println("Invalid number of nights. Please enter a positive number.");
                sc.nextLine();
                return;
            }

            int nights = sc.nextInt();
            sc.nextLine();

            if (nights > 0) {
                double totalCost = nights * roomRates[index];
                roomStatus[index] = "Booked";
                guestNames[index] = guest;
                bookedNights[index] = nights;

                System.out.println("\n=========================================");
                System.out.println("           BOOKING CONFIRMED!");
                System.out.println("=========================================");
                System.out.println("Guest Name  : " + guestNames[index]);
                System.out.println("Room        : " + roomNumbers[index] + " (" + roomTypes[index] + ")");
                System.out.println("Nights      : " + bookedNights[index]);
                System.out.printf("Rate/Night  : PHP %.2f%n", roomRates[index]);
                System.out.printf("Total Cost  : PHP %.2f%n", totalCost);
                System.out.println("=========================================");
            } else {
                System.out.println("Invalid number of nights. Please enter a positive number.");
            }
        }
    }

    // ==========================================
    // 4. CANCEL BOOKING (Member 4)
    // ==========================================
    public static void cancelBooking(Scanner sc) {
        System.out.print("\nEnter room number to cancel booking (101-105): ");
        if (!sc.hasNextInt()) {
            System.out.println("Invalid room number input.");
            sc.nextLine();
            return;
        }

        int roomNum = sc.nextInt();
        sc.nextLine();
        int index = findRoomIndex(roomNum);

        if (index == -1) {
            System.out.println("Room not found!");
        } else if (roomStatus[index].equalsIgnoreCase("Available")) {
            System.out.println("Room " + roomNumbers[index] + " is currently Available (no active booking to cancel).");
        } else {
            String cancelledGuest = guestNames[index];
            roomStatus[index] = "Available";
            guestNames[index] = "None";
            bookedNights[index] = 0;

            System.out.println("\nBooking for " + cancelledGuest + " in Room " + roomNumbers[index] + " has been cancelled.");
            System.out.println("Room " + roomNumbers[index] + " is now Available again.");
        }
    }

    // ==========================================
    // 5. BOOKING & COST SUMMARY
    // ==========================================
    public static void displaySummary() {
        int bookedCount = 0;
        int availableCount = 0;
        double totalBookingAmount = 0.0;

        for (int i = 0; i < roomNumbers.length; i++) {
            if (roomStatus[i].equalsIgnoreCase("Booked")) {
                bookedCount++;
                totalBookingAmount += bookedNights[i] * roomRates[i];
            } else {
                availableCount++;
            }
        }

        System.out.println("\n--- RESERVATION SUMMARY ---");
        System.out.println("Total Rooms         : " + roomNumbers.length);
        System.out.println("Available Rooms     : " + availableCount);
        System.out.println("Booked Rooms        : " + bookedCount);
        System.out.printf("Total Booking Amount: PHP %.2f%n", totalBookingAmount);
    }

    // Helper method to find room index in arrays
    public static int findRoomIndex(int roomNum) {
        for (int i = 0; i < roomNumbers.length; i++) {
            if (roomNumbers[i] == roomNum) {
                return i;
            }
        }
        return -1;
    }
}