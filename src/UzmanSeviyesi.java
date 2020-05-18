import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UzmanSeviyesi implements  ActionListener{
    JMenuBar menu = new JMenuBar();
    JMenu Oyun_Ayarlari = new JMenu("Oyun Ayarları");
    JMenuItem Yeni_Oyun = new JMenuItem("Yeni Oyun");
    JMenuItem Başlangıç_Seviyesi = new JMenuItem("Başlangıç Seviyesi");
    JMenuItem Normal_Seviye = new JMenuItem("Normal Seviye");
    JMenuItem Uzman_Seviye = new JMenuItem("Uzman Seviye");
    JMenuItem Çıkış = new JMenuItem("Çıkış");
    JFrame frame = new JFrame("Mayın Tarlası");
    ImageIcon resim = new ImageIcon(getClass().getResource("emoji.png"));
    JButton reset = new JButton(resim);
    
    JPanel panel = new JPanel();
    JLabel zaman = new JLabel();
    JLabel a1 = new JLabel();
    JLabel a2 = new JLabel();
    JLabel a3 = new JLabel();
    JLabel a4 = new JLabel();
    int süre =0;
    
    JButton[][] buton = new JButton[25][25];
    int[][] sayım = new int[25][25];
    Container grid = new Container();
    final int mayın = 10;
   
   
    public UzmanSeviyesi() {
     
  
        frame.setSize(1050, 900);
        frame.setLayout(new BorderLayout());
        zaman.setPreferredSize(new Dimension(30,30));
        reset.setPreferredSize(new Dimension(40,40));
       
        a1.setPreferredSize(new Dimension(200,30));
        a2.setPreferredSize(new Dimension(200,30));
        a3.setPreferredSize(new Dimension(200,30));
        a4.setPreferredSize(new Dimension(200,30));
        frame.setJMenuBar(menu);
        frame.add(panel,BorderLayout.NORTH);
        
        panel.add(a1);
        panel.add(a2);
        panel.add(reset);
        panel.add(a3);
        panel.add(a4);
        panel.add(zaman);
       
        
        menu.add(Oyun_Ayarlari);
        Oyun_Ayarlari.add(Yeni_Oyun);
        Oyun_Ayarlari.add(Başlangıç_Seviyesi);
        Oyun_Ayarlari.add(Normal_Seviye);
        Oyun_Ayarlari.add(Uzman_Seviye);
        Oyun_Ayarlari.add(Çıkış);

       
      Çıkış.addActionListener(this);
      Yeni_Oyun.addActionListener(this);
      Başlangıç_Seviyesi.addActionListener(this);
      Normal_Seviye.addActionListener(this);
      Uzman_Seviye.addActionListener(this);
      reset.addActionListener(this);

       mayinolusturma();
        frame.add(grid, BorderLayout.CENTER);  
        grid.setLayout(new GridLayout(25, 25));
        for (int i = 0; i < buton.length; i++) {
            for (int j = 0; j < buton.length; j++) {
                buton[i][j] = new JButton();
                buton[i][j].addActionListener(this);
                grid.add(buton[i][j]);
            }
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
 
    public void mayinolusturma() {
     ArrayList<Integer> list = new ArrayList<Integer>();
        for (int x = 0; x < sayım.length; x++) {
            for (int y = 0; y < sayım[0].length; y++) {
                list.add(x * 100 + y);
            }
        }
        sayım = new int[25][25];
       
        for (int i = 0; i < 99; i++) {
            int secim = (int) (Math.random() * list.size());
            sayım[list.get(secim) / 100][list.get(secim) % 100] = mayın;
            list.remove(secim);
        }

        for (int x = 0; x < sayım.length; x++) {
            for (int y = 0; y < sayım[0].length; y++) {
                if (sayım[x][y] != mayın) {
                    int komsusayisi = 0;
                    if (x > 0 && y > 0 && sayım[x - 1][y - 1] == mayın) {
                        
                        komsusayisi++;

                    }

                    if (y > 0 && sayım[x][y - 1] == mayın) {
                        komsusayisi++;

                    }

                    if (x < sayım.length - 1 && y > 0 && sayım[x + 1][y - 1] == mayın) {
                        komsusayisi++;

                    }
                    if (x > 0 && sayım[x - 1][y] == mayın) {
                        komsusayisi++;

                    }
                    if (x < sayım.length - 1 && sayım[x + 1][y] == mayın) {
                        komsusayisi++;

                    }
                    if (x > 0 && y < sayım[0].length - 1 && sayım[x - 1][y + 1] == mayın) {
                        komsusayisi++;

                    }
                    if (y < sayım[0].length - 1 && sayım[x][y + 1] == mayın) {
                        komsusayisi++;

                    }
                    if (x < sayım.length - 1 && y < sayım[0].length - 1 && sayım[x + 1][y + 1] == mayın) {
                        komsusayisi++;

                    }
                    sayım[x][y] = komsusayisi;
                }
            }
        }
    } 
    
   public void oyunkontrol() {
        boolean Kazandınız = true;
        for (int x = 0; x < sayım.length; x++) {
            for (int y = 0; y < sayım[0].length; y++) {
                if (sayım[x][y] != mayın && buton[x][y].isEnabled() == true) {
                Kazandınız = false;
                }
            }
        }
        if (Kazandınız == true) {           
            JOptionPane.showMessageDialog(frame, "Kazandınız...");
        }
    }
   
    public void sıfırgöster(ArrayList<Integer> temizle) {
        if (temizle.size() == 0) {
            return;
        }
        
        else {
            int x = temizle.get(0) / 100;
            int y = temizle.get(0) % 100;
            temizle.remove(0);
            
            if (x > 0 && y > 0 && buton[x - 1][y - 1].isEnabled()){
                buton[x - 1][y - 1].setText(sayım[x - 1][y - 1] + "");
                buton[x - 1][y - 1].setEnabled(false);
                buton[x][y].setBackground(Color.pink);
                if (sayım[x - 1][y - 1] == 0) {
                    temizle.add((x - 1) * 100 + (y - 1));
                }
            }
            
            if (y > 0 && buton[x][y - 1].isEnabled())
            {
                buton[x][y - 1].setText(sayım[x][y - 1] + "");
                buton[x][y - 1].setEnabled(false);
                buton[x][y].setBackground(Color.pink);
                if (sayım[x][y - 1] == 0) {
                temizle.add(x * 100 + (y - 1));
                }
            }
            
            if (x < sayım.length - 1 && y > 0 && buton[x + 1][y - 1].isEnabled()){
                buton[x + 1][y - 1].setText(sayım[x + 1][y - 1] + "");
                buton[x + 1][y - 1].setEnabled(false);
                buton[x][y].setBackground(Color.pink);
                if (sayım[x + 1][y - 1] == 0) {
                temizle.add((x + 1) * 100 + (y - 1));
                }
            }
            
            if (x > 0 && buton[x - 1][y].isEnabled()){
                buton[x - 1][y].setText(sayım[x - 1][y] + "");
                buton[x - 1][y].setEnabled(false);
                buton[x][y].setBackground(Color.pink);
                if (sayım[x - 1][y] == 0) {
                temizle.add((x - 1) * 100 + y);
             }
            }

            if (x < sayım.length - 1 && buton[x + 1][y].isEnabled()){
               buton[x + 1][y].setText(sayım[x + 1][y] + "");
               buton[x + 1][y].setEnabled(false);
               buton[x][y].setBackground(Color.pink);
               if (sayım[x + 1][y] == 0) {
               temizle.add((x + 1) * 100 + y);
                }
            }
           
            if (x > 0 && y < sayım[0].length - 1 && buton[x - 1][y + 1].isEnabled()) {
                buton[x - 1][y + 1].setText(sayım[x - 1][y + 1] + "");
                buton[x - 1][y + 1].setEnabled(false);
                buton[x][y].setBackground(Color.pink);
                if (sayım[x - 1][y + 1] == 0) {
                temizle.add((x - 1) * 100 + (y + 1));
                }
            }
            
            if (y < sayım[0].length - 1 && buton[x][y + 1].isEnabled()){
                buton[x][y + 1].setText(sayım[x][y + 1] + "");
                buton[x][y + 1].setEnabled(false);
                buton[x][y].setBackground(Color.pink);
                if (sayım[x][y + 1] == 0) {
                temizle.add(x * 100 + (y + 1));
                }
            }
            
            if (x < sayım.length - 1 && y < sayım[0].length - 1 && buton[x + 1][y + 1].isEnabled()){
                buton[x + 1][y + 1].setText(sayım[x + 1][y + 1] + "");
                buton[x + 1][y + 1].setEnabled(false);
                buton[x][y].setBackground(Color.pink);
                if (sayım[x + 1][y + 1] == 0) {
                temizle.add((x + 1) * 100 + (y + 1));
                }               
            }
            sıfırgöster(temizle);
        }
    }

  
      public void Oyun_Bitişi() {
        for (int i = 0; i < buton.length; i++) {
            for (int j = 0; j < buton[0].length; j++) {
                if (buton[i][j].isEnabled()) {
                    if (sayım[i][j] != mayın) {
                        buton[i][j].setText(sayım[i][j] + "");
                        buton[i][j].setEnabled(false);
                       }
                    else {
                        ImageIcon mayınresim = new ImageIcon(getClass().getResource("mayın.GIF"));
                         buton[i][j].setBackground(Color.red);
                        buton[i][j].setIcon(mayınresim);                        
                        zamansayacı.cancel();        
                    }        
                }
            }
        }
        JOptionPane.showMessageDialog(null,"Mayına Bastınız bummmmmm..","Yandın",JOptionPane.INFORMATION_MESSAGE);
    }

       Timer zamansayacı = new Timer();
      TimerTask say = new TimerTask() {

           @Override
           public void run() {
         
           süre++;
          String süree = String.valueOf(süre);
           zaman.setText(süree);
           
           }
       };     
      
    @Override
    public void actionPerformed(ActionEvent e) {
       
         if(e.getSource()==Yeni_Oyun){
           frame.setVisible(false);
          UzmanSeviyesi uzman=new UzmanSeviyesi();
             uzman.mayinolusturma();
    
        }
        if(e.getSource()==Normal_Seviye){
           frame.setVisible(false);
           NormalSeviye ort=new NormalSeviye();
              ort. mayinolusturma();
    
        }
       
        
       if(e.getSource()==Başlangıç_Seviyesi){
          frame.setVisible(false);
          OyunEkrani oyun = new OyunEkrani();
          oyun.mayinolusturma();
       }
        if(e.getSource()==Çıkış){
           System.exit(0);
        }
    
    if (e.getSource().equals(reset)) {
          UzmanSeviyesi uzman = new UzmanSeviyesi();
          uzman.mayinolusturma();
          frame.setVisible(false);
          zamansayacı.cancel();
        } 
    else {
            for (int i = 0; i < buton.length; i++) {
                for (int j = 0; j < buton[0].length; j++) {
                    if (e.getSource().equals(buton[i][j])) {
                          if (sayım[i][j] == 0) {
                           buton[i][j].setBackground(Color.pink);
                         }
                         if (sayım[i][j] == 1) {
                           buton[i][j].setBackground(Color.YELLOW);
                }               
                              if (sayım[i][j] == 2) {
                           buton[i][j].setBackground(Color.GREEN);
                }               
                                 if (sayım[i][j] == 3) {
                           buton[i][j].setBackground(Color.ORANGE);
                }               
                                    if (sayım[i][j] >3) {
                           buton[i][j].setBackground(Color.CYAN);
                }               
                        if (sayım[i][j] == mayın) {
                            ImageIcon mayınicon = new ImageIcon(getClass().getResource("mayın.GIF"));
                        buton[i][j].setIcon(mayınicon);
                         zamansayacı.cancel();
                            Oyun_Bitişi();
                        } else if (sayım[i][j] == 0) {
                            buton[i][j].setText(sayım[i][j] + "");
                            buton[i][j].setEnabled(false);
                            ArrayList<Integer> temizle = new ArrayList<Integer>();
                            temizle.add(i * 100 + j);                    
                            sıfırgöster(temizle);
                            oyunkontrol();
                        } else {
                            buton[i][j].setText(sayım[i][j] + "");                          
                            buton[i][j].setEnabled(false);
                            oyunkontrol();
                        }
                    }
                }
            }
           zamansayacı.schedule(say, 0, 1000);
        }
    }   
}
