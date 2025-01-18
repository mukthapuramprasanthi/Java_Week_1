// Music Player Application in Java

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class MusicPlayerApplication {

    private static ArrayList<File> playlist = new ArrayList<>();
    private static JLabel songLabel = new JLabel("No song selected");

    public static void main(String[] args) {
        JFrame frame = new JFrame("Music Player");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        songLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(songLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton addButton = new JButton("Add Song");
        JButton playButton = new JButton("Play");
        JButton stopButton = new JButton("Stop");
        JButton nextButton = new JButton("Next");
        JButton previousButton = new JButton("Previous");

        buttonPanel.add(addButton);
        buttonPanel.add(playButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(previousButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Audio Files", "mp3", "wav", "flac"));
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    playlist.add(selectedFile);
                    JOptionPane.showMessageDialog(frame, "Added: " + selectedFile.getName());
                }
            }
        });

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playlist.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Playlist is empty!");
                } else {
                    songLabel.setText("Now Playing: " + playlist.get(0).getName());
                    // Here you can integrate an actual audio playback library
                }
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                songLabel.setText("Playback stopped.");
                // Stop the playback logic
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playlist.size() > 1) {
                    File currentSong = playlist.remove(0);
                    playlist.add(currentSong);
                    songLabel.setText("Now Playing: " + playlist.get(0).getName());
                } else {
                    JOptionPane.showMessageDialog(frame, "No more songs in the playlist.");
                }
            }
        });

        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playlist.size() > 1) {
                    File lastSong = playlist.remove(playlist.size() - 1);
                    playlist.add(0, lastSong);
                    songLabel.setText("Now Playing: " + playlist.get(0).getName());
                } else {
                    JOptionPane.showMessageDialog(frame, "No previous song available.");
                }
            }
        });
    }
}
