import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author EYMEN SEZEN
 */
class Ates {

    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Ates(int x, int y) {
        this.x = x;
        this.y = y;
    }

}

public class Oyun extends JPanel implements KeyListener, ActionListener/*action vermek icin*/ {

    Timer timer = new Timer(1, this);
    private int gecensure = 0;
    private int harcananates = 0;
    private BufferedImage image;//dosyadan png alıcaz.
    private ArrayList<Ates> atesler = new ArrayList<Ates>();
    private int atesdirY = 4;//ateş hareketlenmesi sadece dikey
    private int topX = 0;
    private int topdirX = 4;
    private int uzaygemisiX = 0;
    private int diruzayX = 20;//20 birimşer kaydırma

    public boolean kontrolet()//ÇARPIŞMA
    {
        for (Ates ates : atesler) {
            if (new Rectangle(ates.getX(), ates.getY(), 10, 20).intersects(new Rectangle(topX, 0, 20, 20)))//iyi iki rectangle ile ateşi ve topu tuttuk
            {
                return true;
            }
        }
        return false;
    }

    public Oyun() {
        try {
            image = ImageIO.read(new FileImageInputStream(new File("spaceX.png")));
        } catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }
        setBackground(Color.BLACK);//arkaplan

        timer.start();//timer başlasın
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        gecensure += 5;
        g.setColor(Color.red);
        g.fillOval(topX, 0, 20, 20);//y koordinat değişmediği için 0 topa ait koordinatlar
        g.drawImage(image, uzaygemisiX/*x koord sıfırdan başlıyor*/, 490/*ykoord*/, image.getWidth() / 20, image.getHeight() / 20, this);//imageımızı çizsin
        for (Ates ates : atesler) {
            if (ates.getY() < 0) {
                atesler.remove(ates);//frame dısı ateşleri silmek icin
            }
        }
        g.setColor(Color.BLUE);
        for (Ates ates : atesler) {
            g.fillRect(ates.getX(), ates.getY(), 10, 20);//atesi ve koordinatları oluşturduk zaten xideğişmeyecek
        }
        if (kontrolet()) {//çarpışma kontrol edilip true dönmüşse
            timer.stop();
            String message = "KAZANDINIZ...\n"
                    + "HARCANAN ATES: " + harcananates
                    + "\nGEÇEN SÜRE: " + gecensure / 300.0 + "s";
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }

    }

    @Override//ileride paint için yardım edecek
    public void repaint() {
        super.repaint(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();
        /*sola basılırsa*/
        if (c == KeyEvent.VK_LEFT) {
            if (uzaygemisiX <= 0) {
                uzaygemisiX = 0;//en solumuz belli olsun diye
            } else {
                uzaygemisiX -= diruzayX;//sola doğru 20 birim kaydırma amacı taşıyor
            }

        } else if (c == KeyEvent.VK_RIGHT) {
            if (uzaygemisiX >= 740) {
                uzaygemisiX = 740;
            } else {
                uzaygemisiX += diruzayX;
            }

        } else if (c == KeyEvent.VK_CONTROL)//ates icin
        {
            atesler.add(new Ates(uzaygemisiX + 15, 470));//uzay gemisinin x ekseninde hafif sağa kaymış ve y ekseni 490da yani uzay gemisinin içinden başladığıiçin

            harcananates++;

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Ates ates : atesler) {
            ates.setY(ates.getY() - atesdirY);
        }
        topX += topdirX;//sınırdan çıkarsa ne yapcağız???
        if (topX >= 750) {
            topdirX = -topdirX;//geriye gidecek
        }
        if (topX <= 0) {
            topdirX = -topdirX;
        }
        repaint();
    }

}
