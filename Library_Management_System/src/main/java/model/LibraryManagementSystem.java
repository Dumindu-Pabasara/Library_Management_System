package model;
import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class LibraryManagementSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Library Management System");
            System.out.println("1. Add a New Book");
            System.out.println("2. Update Book Details");
            System.out.println("3. Delete a Book");
            System.out.println("4. Search for a Book");
            System.out.println("5. Add a New Member");
            System.out.println("6. Loan a Book");
            System.out.println("7. Return a Book");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                switch (choice) {
                    case 1:
                        addNewBook(scanner);
                        break;
                    case 2:
                        updateBookDetails(scanner);
                        break;
                    case 3:
                        deleteBook(scanner);
                        break;
                    case 4:
                        searchForBook(scanner);
                        break;
                    case 5:
                        addNewMember(scanner);
                        break;
                    case 6:
                        loanBook(scanner);
                        break;
                    case 7:
                        returnBook(scanner);
                        break;
                    case 8:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
                }catch(SQLException e){
                        
                        }}}

    /**
     *
     * @param scanner
     */
public static void addNewBook(Scanner scanner) {
    System.out.print("Enter title: ");
    String title = scanner.nextLine();
    System.out.print("Enter author: ");
    String author = scanner.nextLine();
    System.out.print("Enter publisher: ");
    String publisher = scanner.nextLine();
    System.out.print("Enter year published: ");
    int yearPublished = scanner.nextInt();
    scanner.nextLine(); // Consume newline

    String query = "INSERT INTO books (title, author, publisher, year_published) VALUES (?, ?, ?, ?)";
    try (Connection conn = DatabaseConnection.connectDB();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, title);
        pstmt.setString(2, author);
        pstmt.setString(3, publisher);
        pstmt.setInt(4, yearPublished);
        pstmt.executeUpdate();
        System.out.println("Book added successfully!");
        conn.close();
    } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
    }
}

public static void updateBookDetails(Scanner scanner) throws SQLException {
    System.out.print("Enter book ID to update: ");
    int bookId = scanner.nextInt();
    scanner.nextLine(); // Consume newline

    System.out.print("Enter new title: ");
    String title = scanner.nextLine();
    System.out.print("Enter new author: ");
    String author = scanner.nextLine();
    System.out.print("Enter new publisher: ");
    String publisher = scanner.nextLine();
    System.out.print("Enter new year published: ");
    int yearPublished = scanner.nextInt();

    String query = "UPDATE books SET title = ?, author = ?, publisher = ?, year_published = ? WHERE id = ?";
    try (Connection conn = DatabaseConnection.connectDB();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, title);
        pstmt.setString(2, author);
        pstmt.setString(3, publisher);
        pstmt.setInt(4, yearPublished);
        pstmt.setInt(5, bookId);
        pstmt.executeUpdate();
        System.out.println("Book details updated successfully!");
        conn.close();
    }
}
public static void deleteBook(Scanner scanner) throws SQLException {
    System.out.print("Enter book ID to delete: ");
    int bookId = scanner.nextInt();

    String query = "DELETE FROM books WHERE id = ?";
    try (Connection conn = DatabaseConnection.connectDB();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, bookId);
        pstmt.executeUpdate();
        System.out.println("Book deleted successfully!");
        conn.close();
    }
}
public static void searchForBook(Scanner scanner) throws SQLException {
    System.out.print("Enter search term (title, author, or year published): ");
    String searchTerm = scanner.nextLine();

    String query = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR year_published LIKE ?";
    try (Connection conn = DatabaseConnection.connectDB();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        String likeTerm = "%" + searchTerm + "%";
        pstmt.setString(1, likeTerm);
        pstmt.setString(2, likeTerm);
        pstmt.setString(3, likeTerm);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("id"));
            System.out.println("Title: " + rs.getString("title"));
            System.out.println("Author: " + rs.getString("author"));
            System.out.println("Publisher: " + rs.getString("publisher"));
            System.out.println("Year Published: " + rs.getInt("year_published"));
            System.out.println();
        }
    }
}
public static void addNewMember(Scanner scanner) throws SQLException {
    System.out.print("Enter name: ");
    String name = scanner.nextLine();
    System.out.print("Enter email: ");
    String email = scanner.nextLine();
    System.out.print("Enter phone: ");
    String phone = scanner.nextLine();

    String query = "INSERT INTO members (name, email, phone) VALUES (?, ?, ?)";
    try (Connection conn = DatabaseConnection.connectDB();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, name);
        pstmt.setString(2, email);
        pstmt.setString(3, phone);
        pstmt.executeUpdate();
        System.out.println("Member added successfully!");
        conn.close();
    }
}
public static void loanBook(Scanner scanner) throws SQLException {
    System.out.print("Enter book ID to loan: ");
    int bookId = scanner.nextInt();
    System.out.print("Enter member ID: ");
    int memberId = scanner.nextInt();
    System.out.print("Enter loan date (YYYY-MM-DD): ");
    String loanDate = scanner.next();
    System.out.print("Enter due date (YYYY-MM-DD): ");
    String dueDate = scanner.next();

    String query = "INSERT INTO loans (book_id, member_id, loan_date, due_date) VALUES (?, ?, ?, ?)";
    try (Connection conn = DatabaseConnection.connectDB();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, bookId);
        pstmt.setInt(2, memberId);
        pstmt.setDate(3, Date.valueOf(loanDate));
        pstmt.setDate(4, Date.valueOf(dueDate));
        pstmt.executeUpdate();
        System.out.println("Book loaned successfully!");
        conn.close();
    }
}
public static void returnBook(Scanner scanner) throws SQLException {
    System.out.print("Enter loan ID to return: ");
    int loanId = scanner.nextInt();
    System.out.print("Enter return date (YYYY-MM-DD)");
    }
            }
        

        
               
