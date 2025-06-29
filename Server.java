import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.Color;

public class Server {
    static final List<Segment> segments = Collections.synchronizedList(new ArrayList<>());
    static final Map<Socket, Color> colors = new HashMap<>();

    public static void start() {
        try (ServerSocket ss = new ServerSocket(12345)) {
            while (true) new SocketHandler(ss.accept()).start();
        } catch (IOException e) {}
    }
}

class SocketHandler extends Thread {
    Socket s;

    public SocketHandler(Socket s) { this.s = s; }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                if (line.length() == 6) {
                    Server.colors.put(s, new Color(
                            Integer.parseInt(line.substring(0,2),16),
                            Integer.parseInt(line.substring(2,4),16),
                            Integer.parseInt(line.substring(4,6),16)));
                } else {
                    String[] p = line.split(" ");
                    Server.segments.add(new Segment(
                            Float.parseFloat(p[0]),
                            Float.parseFloat(p[1]),
                            Float.parseFloat(p[2]),
                            Float.parseFloat(p[3]),
                            Server.colors.getOrDefault(s, Color.BLACK)));
                }
            }
        } catch (IOException e) {}
    }
}

class Segment {
    float x1,y1,x2,y2; Color c;
    public Segment(float x1, float y1, float x2, float y2, Color c) {
        this.x1=x1; this.y1=y1; this.x2=x2; this.y2=y2; this.c=c;
    }
}
