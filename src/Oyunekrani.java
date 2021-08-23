
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

public class Oyunekrani extends JFrame  /*actionlar için*/ {
    public Oyunekrani(String title) throws HeadlessException {
        super (title);
    }
    
    public static void main(String[] args) {
        
        Oyunekrani ekran = new Oyunekrani("Uzay Oyunu");//string title içeren bir cosntructor ekledim.
        ekran.setResizable(false);
        ekran.setFocusable(false);//işlemler jframea oduklanmasın panele odaklansın diye
        ekran.setSize(800,600);
        ekran.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Oyun oyun = new Oyun();
        oyun.requestFocus();//odağı almak için
        oyun.addKeyListener(oyun);                    //klavyeden işlem almak için //casting yaptım...
        oyun.setFocusable(true);//panele odak
        oyun.setFocusTraversalKeysEnabled(false);//klavye işlemleri anlayabilmek için
        ekran.add(oyun);
        ekran.setVisible(true);
        ekran.setLocationRelativeTo(null);
        
                
    }

   

}
