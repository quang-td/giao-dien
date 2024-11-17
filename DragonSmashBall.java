package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class DragonSmashBall extends JFrame {
    private JPanel mainPanel;
    private JLabel gokuImage, gohanImage;
    private Timer animationTimer;
    private int currentFps = 60; // Lưu giá trị FPS
    private boolean isSoundOn = true; // Âm thanh mặc định là bật
    private Clip menuMusicClip;

    public DragonSmashBall() {
        setTitle("Dragon Smash Ball Z");
        setSize(1920, 1080); // Tăng kích thước màn hình
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setupBackground();
        setupTitle();
        setupCharacterAnimations();
        setupButtons();
        setupBackgroundMusic();

        setVisible(true);
    }

    private void setupBackground() {
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                URL imageUrl = getClass().getResource("/images/menuback.png");
                if (imageUrl != null) {
                    g.drawImage(new ImageIcon(imageUrl).getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        mainPanel.setLayout(null);
        setContentPane(mainPanel);
    }

    private void setupTitle() {
        JLabel titleLabel = new JLabel("DragonSmashBallZ");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setBounds(500, 20, 400, 50);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel);
    }

    private void setupCharacterAnimations() {
        gokuImage = createImageLabel("/images/songokumenu.gif", 30, 120, 450, 580);
        gohanImage = createImageLabel("/images/gohanmenu.gif", 1200, 120, 300, 280);

        animationTimer = new Timer(1000 / currentFps, new ActionListener() {
            int direction = 1;

            public void actionPerformed(ActionEvent e) {
                int gokuY = gokuImage.getY();
                int gohanY = gohanImage.getY();
                if (gokuY >= 170 || gokuY <= 90) direction *= -1;
                gokuImage.setLocation(gokuImage.getX(), gokuY + direction);
                gohanImage.setLocation(gohanImage.getX(), gohanY - direction);
            }
        });
        animationTimer.start();
    }

    private JLabel createImageLabel(String resourcePath, int x, int y, int width, int height) {
        URL imageUrl = getClass().getResource(resourcePath);
        JLabel label = new JLabel();
        if (imageUrl != null) {
            label.setIcon(new ImageIcon(imageUrl));
        }
        label.setBounds(x, y, width, height);
        mainPanel.add(label);
        return label;
    }

    private void setupButtons() {
        String[] buttonLabels = {"Vào Game", "Cài đặt", "Thoát Game"};
        int yPosition = 280;

        for (String label : buttonLabels) {
            JButton button = createButton(label);
            button.setBounds(450, yPosition, 600, 60);
            yPosition += 70;
            mainPanel.add(button);
        }
    }

    private JButton createButton(String label) {
        JButton button = new JButton(label);
        URL beforeIcon = getClass().getResource("/images/titlemenucursorbefore.gif");
        URL afterIcon = getClass().getResource("/images/titlemenucursorAfter.gif");
        if (beforeIcon != null) button.setIcon(new ImageIcon(beforeIcon));
        if (afterIcon != null) button.setRolloverIcon(new ImageIcon(afterIcon));
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        button.addActionListener(e -> {
            if (label.equals("Vào Game")) {
                showCharacterSelectionScreen(); // Hiển thị màn hình chọn nhân vật
            } else if (label.equals("Cài đặt")) {
                showSettingsScreen(); // Hiển thị màn hình cài đặt
            } else if (label.equals("Thoát Game")) {
                System.exit(0); // Thoát game
            }
        });
        return button;
    }

    private void setupBackgroundMusic() {
        try {
            URL soundUrl = getClass().getResource("/Sound/menu.wav");
            if (soundUrl != null) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundUrl);
                menuMusicClip = AudioSystem.getClip();
                menuMusicClip.open(audioStream);
                if (isSoundOn) {
                    menuMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void toggleSound() {
        if (isSoundOn) {
            menuMusicClip.stop();
        } else {
            menuMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        isSoundOn = !isSoundOn;
    }

    private void showSettingsScreen() {
        mainPanel.removeAll();
        setupBackground();
        setupTitle();
        setupCharacterAnimations();
        int yPosition = 280;

        JLabel screenSizeLabel = new JLabel("Chọn kích cỡ màn hình:");
        screenSizeLabel.setForeground(Color.YELLOW);
        screenSizeLabel.setBounds(450, yPosition, 600, 30);
        mainPanel.add(screenSizeLabel);

        String[] screenSizes = {"1920x1080", "1280x720", "800x600"};
        JComboBox<String> screenSizeComboBox = new JComboBox<>(screenSizes);
        screenSizeComboBox.setBounds(450, yPosition + 40, 600, 30);
        mainPanel.add(screenSizeComboBox);

        screenSizeComboBox.addActionListener(e -> {
            String selectedSize = (String) screenSizeComboBox.getSelectedItem();
            switch (selectedSize) {
                case "1920x1080":
                    setSize(1920, 1080);
                    break;
                case "1280x720":
                    setSize(1280, 720);
                    break;
                case "800x600":
                    setSize(800, 600);
                    break;
            }
        });

        yPosition += 100;

        JLabel fpsLabel = new JLabel("Chỉnh FPS:");
        fpsLabel.setForeground(Color.YELLOW);
        fpsLabel.setBounds(450, yPosition, 600, 30);
        mainPanel.add(fpsLabel);

        JSlider fpsSlider = new JSlider(JSlider.HORIZONTAL, 30, 120, currentFps);
        fpsSlider.setBounds(450, yPosition + 40, 600, 60);
        fpsSlider.setMajorTickSpacing(30);
        fpsSlider.setMinorTickSpacing(10);
        fpsSlider.setPaintTicks(true);
        fpsSlider.setPaintLabels(true);
        mainPanel.add(fpsSlider);

        fpsSlider.addChangeListener(e -> {
            currentFps = fpsSlider.getValue();
            if (animationTimer != null) {
                animationTimer.setDelay(1000 / currentFps);
            }
        });

        yPosition += 130;

        JButton soundToggleButton = createButton("Âm thanh: " + (isSoundOn ? "ON" : "OFF"));
        soundToggleButton.setBounds(450, yPosition, 600, 60);
        mainPanel.add(soundToggleButton);

        soundToggleButton.addActionListener(e -> {
            toggleSound();
            soundToggleButton.setText("Âm thanh: " + (isSoundOn ? "ON" : "OFF"));
        });

        yPosition += 70;

        JButton backButton = createButton("Quay lại");
        backButton.setBounds(450, yPosition, 600, 60);
        mainPanel.add(backButton);

        backButton.addActionListener(e -> showMainScreen());

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showMainScreen() {
        mainPanel.removeAll();
        setupBackground();
        setupTitle();
        setupCharacterAnimations();
        setupButtons();
        setupBackgroundMusic();
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    private void showCharacterSelectionScreen() {
        mainPanel.removeAll();

        // Đặt nền cho giao diện chọn nhân vật
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                URL backgroundUrl = getClass().getResource("/images/menuback.png");

                if (backgroundUrl != null) {
                    g.drawImage(new ImageIcon(backgroundUrl).getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        mainPanel.setLayout(null);
        setContentPane(mainPanel);

        // Hiển thị tiêu đề
        JLabel titleLabel = new JLabel("Chọn nhân vật");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 60));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setBounds(600, 20, 600, 50);
        mainPanel.add(titleLabel);

        // Khu vực hiển thị các nhân vật
        JPanel characterGrid = new JPanel();
        characterGrid.setBounds(300, 150, 800, 400); // Điều chỉnh tọa độ và kích thước
        characterGrid.setLayout(new GridLayout(2, 4, 10, 10));
        characterGrid.setOpaque(false);

        // Danh sách nhân vật (ảnh tối và sáng)
        String[][] characterImages = {
            {"/images/gokuAvatarOff.png", "/images/gokuAvatar.png"},
            {"/images/picoloAvatarOff.png", "/images/picoloAvatar.png"},
            {"/images/vegetaAvatarOff.png", "/images/vegetaAvatar.png"},
            {"/images/kameSenninAvatarOff.png", "/images/kameSenninAvatar.png"},
            {"/images/frieezaAvatarOff.png", "/images/frieezaAvatar.png"},
            {"/images/buuAvatarOff.png", "/images/buuAvatar.png"},
            {"/images/cellAvatarOff.png", "/images/cellAvatar.png"},
            {"/images/gohanAvatarOff.png", "/images/gohanAvatar.png"}
        };

        // Tạo các nút nhân vật
        for (int i = 0; i < characterImages.length; i++) {
            JButton charButton = createCharacterButton(characterImages[i], i);
            characterGrid.add(charButton);
        }

        mainPanel.add(characterGrid);

        // Nút Lock
        JButton lockButton = createButton("Lock");
        lockButton.setBounds(850, 650, 200, 50);
        lockButton.addActionListener(e -> handleLockSelection());
        mainPanel.add(lockButton);
        JLabel  player1Label = new JLabel();
        ImageIcon player1Image = new ImageIcon(getClass().getResource("/images/gokuAvatar.png"));
        player1Label.setIcon(player1Image);
        JLabel  player2Label = new JLabel();
        ImageIcon player2Image = new ImageIcon(getClass().getResource("/images/gokuAvatar.png"));
        player2Label.setIcon(player2Image);
        player1Label.setBounds(100, 300, 200, 200); // Đặt ở góc trái
        player2Label.setBounds(1200, 300, 200, 200); // Đặt ở góc phải
        mainPanel.add(player1Label);

        mainPanel.add(player2Label);
        
        player1Label.revalidate();
        player1Label.repaint();
        player2Label.revalidate();
        player2Label.repaint();
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    private JButton createCharacterButton(String[] images, int characterIndex) {
        JButton button = new JButton();
        
        URL darkImageUrl = getClass().getResource(images[0]);
        URL lightImageUrl = getClass().getResource(images[1]);
        if (darkImageUrl != null && lightImageUrl != null) {
            button.setIcon(new ImageIcon(darkImageUrl));
            button.setRolloverIcon(new ImageIcon(lightImageUrl));
        } else {
            System.err.println("Missing image for character: " + characterIndex);
        }

        if (darkImageUrl != null) {
            button.setIcon(new ImageIcon(darkImageUrl)); // Đặt ảnh tối màu làm mặc định
        }

        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        button.addActionListener(e -> {
            if (currentPlayer == 1) {
                player1Selection = characterIndex;
                updateCharacterPreview(1, lightImageUrl);
            } else if (currentPlayer == 2) {
                player2Selection = characterIndex;
                updateCharacterPreview(2, lightImageUrl);
            }

            // Đổi ảnh sang trạng thái sáng
            if (lightImageUrl != null) {
                button.setIcon(new ImageIcon(lightImageUrl));
            }
        });
        
        return button;
    }
    
    private void updateCharacterPreview(int player, URL imageUrl) {
        if (imageUrl == null) return;

        if (player == 1) {
            gokuImage.setIcon(new ImageIcon(imageUrl));
        } else {
            gohanImage.setIcon(new ImageIcon(imageUrl));
        }
    }

    
    private int currentPlayer = 1; // Người chơi hiện tại
    private int player1Selection = -1; // Nhân vật của người chơi 1
    private int player2Selection = -1; // Nhân vật của người chơi 2
    
    
    private void handleLockSelection() {
        if (currentPlayer == 1 && player1Selection != -1) {
            currentPlayer = 2; // Chuyển sang người chơi 2
        } else if (currentPlayer == 2 && player2Selection != -1) {
            showMapSelectionScreen(); // Chuyển sang trang chọn bản đồ
        }
    }
    

    
    private void showMapSelectionScreen() {
        mainPanel.removeAll();
        setupBackground();

        JLabel titleLabel = new JLabel("Chọn bản đồ");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setBounds(600, 20, 600, 50);
        mainPanel.add(titleLabel);

        // Hiển thị các bản đồ
        JPanel mapGrid = new JPanel();
        mapGrid.setBounds(550, 200, 400, 400);
        mapGrid.setLayout(new GridLayout(1, 3, 10, 10));
        mapGrid.setOpaque(false);

        String[] mapImages = {
            "/images/valley_map.png", "/images/valley_map.png", "/images/valley_map.png"
        };

        for (String mapImage : mapImages) {
            JButton mapButton = new JButton();
            mapButton.setIcon(new ImageIcon(getClass().getResource(mapImage)));
            mapButton.setContentAreaFilled(false);
            mapButton.setBorderPainted(false);

            mapButton.addActionListener(e -> startGameWithMap(mapImage));
            mapGrid.add(mapButton);
        }

        mainPanel.add(mapGrid);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void startGameWithMap(String mapImage) {
        // Bắt đầu game với map đã chọn
        System.out.println("Bắt đầu game với map: " + mapImage);
    }


}
