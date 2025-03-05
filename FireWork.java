import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

class Particle {
    int x, y;
    int size;
    double velocityX, velocityY;
    Color color;
    int lifespan = 100; // Controls how long the particle stays visible

    public Particle(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.size = new Random().nextInt(6) + 2; // Random size (2-7)
        this.velocityX = (Math.random() - 0.5) * 10; // Random X speed
        this.velocityY = (Math.random() - 0.5) * 10; // Random Y speed
        this.color = color;
    }

    public void move() {
        x += velocityX;
        y += velocityY;
        velocityY += 0.2; // Simulate gravity
        lifespan--; // Decrease lifespan over time
    }
}

class FireworkPanel extends JPanel {
    private final ArrayList<Particle> particles = new ArrayList<>();
    private final Timer timer;
    private final Random random = new Random();

    public FireworkPanel() {
        setBackground(Color.BLACK);

        // Timer to update animation every 30ms
        timer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateParticles();
                repaint();
            }
        });
        timer.start();
    }

    public void createFirework(int x, int y) {
        Color randomColor = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        for (int i = 0; i < 100; i++) { // Create 100 particles per explosion
            particles.add(new Particle(x, y, randomColor));
        }
    }

    private void updateParticles() {
        Iterator<Particle> iterator = particles.iterator();
        while (iterator.hasNext()) {
            Particle p = iterator.next();
            p.move();
            if (p.lifespan <= 0) {
                iterator.remove();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Particle p : particles) {
            g.setColor(new Color(p.color.getRed(), p.color.getGreen(), p.color.getBlue(), Math.max(0, p.lifespan * 2))); // Fade effect
            g.fillOval(p.x, p.y, p.size, p.size);
        }
    }
}

public class Fireworks {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Amazing Fireworks!");
        FireworkPanel panel = new FireworkPanel();

        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Generate fireworks at random positions
        Timer explosionTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x = (int) (Math.random() * 800);
                int y = (int) (Math.random() * 400 + 100); // Start slightly above the middle
                panel.createFirework(x, y);
            }
        });
        explosionTimer.start();
    }
}
