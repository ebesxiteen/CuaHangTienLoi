package GUI;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.RenderingHints;
import java.util.ArrayList;

public class SimpleLineChart extends JPanel {
    private ArrayList<String> labels = new ArrayList<>();
    private ArrayList<Long> values = new ArrayList<>();

    public void setData(ArrayList<String> labels, ArrayList<Long> values) {
        this.labels = new ArrayList<>(labels);
        this.values = new ArrayList<>(values);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, w, h);
        g2.setColor(Color.BLACK);
        g2.drawRect(40, 10, w - 60, h - 60);
        if (values == null || values.isEmpty()) {
            g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
            g2.drawString("No data", w/2 - 20, h/2);
            return;
        }
        long max = 0;
        for (Long v : values) if (v != null && v > max) max = v;
        if (max == 0) max = 1;
        int plotW = w - 80;
        int plotH = h - 100;
        int n = values.size();
        int prevX = -1, prevY = -1;
        g2.setStroke(new BasicStroke(2f));
        g2.setColor(new Color(30,117,109));
        for (int i = 0; i < n; i++) {
            long v = values.get(i);
            int x = 40 + (int) ((double) i / Math.max(1, n-1) * plotW);
            int y = 10 + plotH - (int) ((double) v / max * plotH);
            g2.fillOval(x-3, y-3, 6, 6);
            if (i > 0) g2.drawLine(prevX, prevY, x, y);
            prevX = x; prevY = y;
        }
        // draw labels (sparse)
        g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
        int step = Math.max(1, n/6);
        for (int i = 0; i < n; i += step) {
            int x = 40 + (int) ((double) i / Math.max(1, n-1) * plotW);
            String lbl = labels.get(i);
            g2.drawString(lbl, x-15, h-35);
        }
    }
}
