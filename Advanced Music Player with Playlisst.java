// Advanced Music Player with Playlist Management using JDBC

import java.sql.*;
import java.util.*;

class Song {
    private String title;
    private String artist;

    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }
}

public class MusicPlayerWithJDBC {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/musicplayer";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    private static Connection connection;

    public static void main(String[] args) {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Connected to database.");

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("\nMusic Player with Playlist Management");
                System.out.println("1. Add Song to Library");
                System.out.println("2. View Library");
                System.out.println("3. Create Playlist");
                System.out.println("4. View Playlist");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        addSongToLibrary(scanner);
                        break;
                    case 2:
                        viewLibrary();
                        break;
                    case 3:
                        createPlaylist(scanner);
                        break;
                    case 4:
                        viewPlaylist(scanner);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        connection.close();
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addSongToLibrary(Scanner scanner) throws SQLException {
        System.out.print("Enter Song Title: ");
        String title = scanner.next();
        System.out.print("Enter Artist: ");
        String artist = scanner.next();

        String query = "INSERT INTO library (title, artist) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, title);
            pstmt.setString(2, artist);
            pstmt.executeUpdate();
            System.out.println("Song added to library.");
        }
    }

    private static void viewLibrary() throws SQLException {
        String query = "SELECT * FROM library";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\nLibrary:");
            while (rs.next()) {
                System.out.println("Title: " + rs.getString("title") + ", Artist: " + rs.getString("artist"));
            }
        }
    }

    private static void createPlaylist(Scanner scanner) throws SQLException {
        System.out.print("Enter Playlist Name: ");
        String playlistName = scanner.next();

        String query = "INSERT INTO playlists (name) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, playlistName);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int playlistId = rs.getInt(1);
                System.out.println("Playlist created with ID: " + playlistId);
                addSongsToPlaylist(scanner, playlistId);
            }
        }
    }

    private static void addSongsToPlaylist(Scanner scanner, int playlistId) throws SQLException {
        while (true) {
            System.out.print("Enter Song ID to add to Playlist (or -1 to finish): ");
            int songId = scanner.nextInt();

            if (songId == -1) {
                break;
            }

            String query = "INSERT INTO playlist_songs (playlist_id, song_id) VALUES (?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setInt(1, playlistId);
                pstmt.setInt(2, songId);
                pstmt.executeUpdate();
                System.out.println("Song added to playlist.");
            }
        }
    }

    private static void viewPlaylist(Scanner scanner) throws SQLException {
        System.out.print("Enter Playlist Name: ");
        String playlistName = scanner.next();

        String query = "SELECT s.title, s.artist FROM playlists p " +
                       "JOIN playlist_songs ps ON p.id = ps.playlist_id " +
                       "JOIN library s ON ps.song_id = s.id WHERE p.name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, playlistName);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\nPlaylist: " + playlistName);
            while (rs.next()) {
                System.out.println("Title: " + rs.getString("title") + ", Artist: " + rs.getString("artist"));
            }
        }
    }
}
