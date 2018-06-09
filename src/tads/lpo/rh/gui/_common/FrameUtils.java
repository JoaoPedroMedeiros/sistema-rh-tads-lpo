package tads.lpo.rh.gui._common;

import javax.swing.*;
import java.awt.*;

public class FrameUtils {

    public static void centralizar(Container frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    public static JDialog showModalPanelDialog(Frame pai, JPanel panel, Dimension dimension) {
        final JDialog frame = new JDialog(pai, "Novo funcionário", true);
        frame.getContentPane().add(panel);

        System.out.println(frame);

        frame.setSize(dimension);
        frame.setPreferredSize(dimension);

        centralizar(frame);

        frame.setResizable(false);

        frame.setVisible(true);

        frame.pack();

        return frame;

    }
}
