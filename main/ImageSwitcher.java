package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImageSwitcher {
    public void ImageSwitcher() {
        // Tạo cửa sổ chính
        JFrame frame = new JFrame("Chương trình hiển thị hình ảnh");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Tạo nút
        JButton button = new JButton("Hiển thị hình ảnh");
        frame.add(button, BorderLayout.CENTER);

        // Gán hành động cho nút
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Tạo cửa sổ mới để hiển thị hình ảnh
                JFrame imageFrame = new JFrame("Hình ảnh");
                imageFrame.setSize(600, 400);
                imageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                // Thêm hình ảnh
                JLabel imageLabel = new JLabel(new ImageIcon("/images/goku/gokuCard.jpg"));
                imageFrame.add(imageLabel);

                // Hiển thị cửa sổ hình ảnh
                imageFrame.setVisible(true);

                // Sau vài giây, đóng cửa sổ hình ảnh và hiển thị cửa sổ khác
                Timer timer = new Timer(3000, new ActionListener() { // Hiển thị trong 3 giây
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        imageFrame.dispose(); // Đóng cửa sổ hình ảnh

                        // Tạo cửa sổ mới
                        JFrame newFrame = new JFrame("Màn hình mới");
                        newFrame.setSize(400, 300);
                        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                        JLabel newLabel = new JLabel("Đây là màn hình mới!", SwingConstants.CENTER);
                        newFrame.add(newLabel);

                        newFrame.setVisible(true);
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        });

        // Hiển thị cửa sổ chính
        frame.setVisible(true);
    }
}
