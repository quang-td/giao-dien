package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;
import java.io.File;



public class SelectChar extends JFrame {
    private JPanel mainPanel;
    private JLabel gokuImage, vegetaImage;
    private Timer animationTimer;
    private int currentFps = 60; // Lưu giá trị FPS


    public SelectChar() {
        setTitle("Dragon Smash Ball Z");
        setSize(1920, 1080); // Tăng kích thước màn hình
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    
        
        setupBackground();
        //setupTitle();
        setupCharacterAnimations();
        //setupButtons();
        setupBackgroundMusic();
        
        setVisible(true);
    }

    private void setupBackground() {
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Vẽ hình nền
                g.drawImage(new ImageIcon("src/images/backgroundSelect.jpg").getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(null);  // Sử dụng bố cục tuyệt đối để có thể định vị chính xác
        setContentPane(mainPanel);
    }

    // private void setupTitle() {
    //     JLabel titleLabel = new JLabel("DragonSmashBallZ");
    //     titleLabel.setFont(new Font("Arial", Font.BOLD, 40)); // Đặt font và kích thước chữ
    //     titleLabel.setForeground(Color.YELLOW); // Đặt màu chữ
    //     titleLabel.setBounds(300, 20, 400, 50); // Định vị dòng chữ tiêu đề
    //     titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    //     mainPanel.add(titleLabel);
    // }

    private void setupCharacterAnimations() {
        gokuImage = new JLabel(new ImageIcon("src\\images\\gokuCard.jpg"));
        vegetaImage = new JLabel(new ImageIcon("src\\images\\vegetaCard.jpg"));
        // Điều chỉnh kích thước và vị trí hình ảnh Goku và Gohan
        gokuImage.setBounds(15, 95, 300, 439);  // Phóng to Goku
        vegetaImage.setBounds(1220, 95, 300, 439); // Phóng to Vegeta
        
        mainPanel.add(gokuImage);
        mainPanel.add(vegetaImage);

        // animationTimer = new Timer(1000 / currentFps, new ActionListener() { // Điều chỉnh FPS
        //     int direction = 1;
        //     public void actionPerformed(ActionEvent e) {
        //         int gokuY = gokuImage.getY();
        //         int gohanY = gohanImage.getY();
        //         // Đảo chiều di chuyển khi đạt giới hạn
        //         if (gokuY >= 170 || gokuY <= 90) direction *= -1;
        //         gokuImage.setLocation(gokuImage.getX(), gokuY + direction);
        //         gohanImage.setLocation(gohanImage.getX(), gohanY - direction);
        //     }
        // });
        // animationTimer.start();
    }

    // private void setupButtons() {
    //     String[] buttonLabels = {"Vào Game", "Cài đặt", "Thoát Game"};
    //     int yPosition = 280;

    //     for (String label : buttonLabels) {
    //         JButton button = createButton(label);
    //         button.setBounds(450, yPosition, 600, 60); // Tăng chiều dài và chiều cao của nút
    //         yPosition += 70;
    //         mainPanel.add(button);
    //     }
    // }

    // private JButton createButton(String label) {
    //     JButton button = new JButton(label);
    //     button.setIcon(new ImageIcon("src\\images\\titlemenucursorbefore.gif"));
    //     button.setRolloverIcon(new ImageIcon("src\\images\\titlemenucursorAfter.gif"));
    //     button.setHorizontalTextPosition(SwingConstants.CENTER);
    //     button.setVerticalTextPosition(SwingConstants.CENTER);
    //     button.setContentAreaFilled(false);
    //     button.setBorderPainted(false);
        
    //     button.addActionListener(new ActionListener() {
    //         public void actionPerformed(ActionEvent e) {
    //             // Xử lý khi nhấn các nút để chuyển màn hình
    //             if (label.equals("Cài đặt")) showSettingsScreen();
    //             if(label.equals("Thoát Game")) System.exit(0);
    //         }
    //     });
    //     return button;
    // }

 // Biến để lưu trạng thái âm thanh và Clip âm thanh
    private boolean isSoundOn = true; // Âm thanh mặc định là bật
    private Clip menuMusicClip;

    // Phương thức để bật/tắt âm thanh
    private void toggleSound() {
        if (isSoundOn) {
            menuMusicClip.stop(); // Tắt âm thanh nếu đang bật
        } else {
            menuMusicClip.loop(Clip.LOOP_CONTINUOUSLY); // Bật lại âm thanh
        }
        isSoundOn = !isSoundOn; // Đổi trạng thái âm thanh
    }

    // Phương thức để thiết lập âm thanh nền
    private void setupBackgroundMusic() {
        try {
            File soundFile = new File("menu.wav"); // Đảm bảo có file menu_music.wav trong thư mục
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            menuMusicClip = AudioSystem.getClip();
            menuMusicClip.open(audioStream);
            if (isSoundOn) {
                menuMusicClip.loop(Clip.LOOP_CONTINUOUSLY); // Phát lại âm thanh liên tục nếu đang bật
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showSettingsScreen() {
        mainPanel.removeAll();
        setupBackground();
        //setupTitle();
        setupCharacterAnimations();
        int yPosition = 280;

        // Điều chỉnh kích cỡ màn hình
        JLabel screenSizeLabel = new JLabel("Chọn kích cỡ màn hình:");
        screenSizeLabel.setForeground(Color.YELLOW);
        screenSizeLabel.setBounds(450, yPosition, 600, 30);
        mainPanel.add(screenSizeLabel);

        String[] screenSizes = {"1920x1080", "1280x720", "800x600"};
        JComboBox<String> screenSizeComboBox = new JComboBox<>(screenSizes);
        screenSizeComboBox.setBounds(450, yPosition + 40, 600, 30);
        mainPanel.add(screenSizeComboBox);

        screenSizeComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
            }
        });

        yPosition += 100;

        // Điều chỉnh FPS
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

        fpsSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                currentFps = fpsSlider.getValue(); // Lưu giá trị FPS
                if (animationTimer != null) {
                    animationTimer.setDelay(1000 / currentFps); // Cập nhật lại FPS
                }
            }
        });
        yPosition += 130;

        // Nút bật/tắt âm thanh
//         JButton soundToggleButton = createButton("Âm thanh: " + (isSoundOn ? "ON" : "OFF"));
//         soundToggleButton.setBounds(450, yPosition, 600, 60);
//         mainPanel.add(soundToggleButton);

//         // Thêm hành động cho nút âm thanh để bật/tắt
//         soundToggleButton.addActionListener(new ActionListener() {
//             public void actionPerformed(ActionEvent e) {
//                 toggleSound(); // Chuyển trạng thái âm thanh
//                 soundToggleButton.setText("Âm thanh: " + (isSoundOn ? "ON" : "OFF")); // Cập nhật văn bản nút
//             }
//         });

//         yPosition += 70;

//         // Thêm nút Back để quay lại màn hình chính
//         JButton backButton = createButton("Quay lại");
//         backButton.setBounds(450, yPosition, 600, 60);
//         mainPanel.add(backButton);

//         // Thêm hành động cho nút Back để quay lại màn hình chính
//         backButton.addActionListener(new ActionListener() {
//             public void actionPerformed(ActionEvent e) {
//                 showMainScreen();
//             }
//         });

//         mainPanel.revalidate();
//         mainPanel.repaint();
//     }


//     // Hàm để hiển thị lại màn hình chính
//     private void showMainScreen() {
//         mainPanel.removeAll();
//         setupBackground();
//         //setupTitle();
//         setupCharacterAnimations();
//         setupButtons(); // Thêm lại các nút của màn hình chính
//         setupBackgroundMusic(); // Phát nhạc nền khi quay lại màn hình chính
//         mainPanel.revalidate();
//         mainPanel.repaint();
     }

}