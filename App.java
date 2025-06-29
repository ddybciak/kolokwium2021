import java.awt.*;
import java.awt.event.*;

public class App extends Frame {
    int ox, oy;

    public App() {
        super("Drawing Server - Offset: (0, 0)");
        setSize(500,500);
        setBackground(Color.WHITE);

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {
                    case 37: ox-=10; break; // LEFT
                    case 38: oy-=10; break; // UP
                    case 39: ox+=10; break; // RIGHT
                    case 40: oy+=10; break; // DOWN
                }
                setTitle("Drawing Server - Offset: ("+ox+", "+oy+")");
                repaint();
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { System.exit(0); }
        });
    }

    public void paint(Graphics g) {
        synchronized(Server.segments) {
            for (Segment s : Server.segments) {
                g.setColor(s.c);
                g.drawLine((int)(s.x1+ox),(int)(s.y1+oy),(int)(s.x2+ox),(int)(s.y2+oy));
            }
        }
    }
}
