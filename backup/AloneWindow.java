package alonewindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;


/**
 * Created by IntelliJ IDEA.
 * User: Виталий
 * Date: 14.05.12
 * Time: 1:40
 * To change this template use File | Settings | File Templates.
 */
public class AloneWindow {
    JFrame frame;
    
    // Переменные для системного трея
    private SystemTray systemTray = SystemTray.getSystemTray();
    private TrayIcon trayIcon;
    
    DrawPanel drawPanel;

    public static void main(String[] args) throws IOException {
        AloneWindow gui = new AloneWindow();
        gui.go();
    }

    private void go() throws IOException {
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        trayIcon = new TrayIcon(ImageIO.read(new File("images/icon_tray.gif")), "");
        trayIcon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (frame.getExtendedState()) {
                    case JFrame.ICONIFIED:
                        frame.setExtendedState(JFrame.NORMAL);
                        frame.setVisible(true);
                        break;
                    case JFrame.NORMAL:
                        frame.setExtendedState(JFrame.ICONIFIED);
                        frame.setVisible(false);
                }                    
        }
        });

        final PopupMenu popupMenu = new PopupMenu();
        MenuItem itemOpen = new MenuItem("Открыть изображение");
        MenuItem itemMinimize = new MenuItem("Свернуть");
        MenuItem itemNormal = new MenuItem("Развернуть");
        MenuItem itemSize1 = new MenuItem("160x120");
        MenuItem itemSize2 = new MenuItem("200x150");
        MenuItem itemSize3 = new MenuItem("320x240");
        MenuItem itemExit = new MenuItem("Выход");
        
        Menu subMenu = new Menu("Разрешение");
        subMenu.add(itemSize1);
        subMenu.add(itemSize2);
        subMenu.add(itemSize3);
        
        createListenerTrayMenu(itemOpen, itemMinimize, itemNormal, itemSize1,itemSize2, itemSize3, itemExit);
        
        popupMenu.add(itemOpen);
        popupMenu.addSeparator();
        popupMenu.add(itemMinimize);
        popupMenu.add(itemNormal);
        popupMenu.addSeparator();
        popupMenu.add(subMenu);
        popupMenu.addSeparator();
        popupMenu.add(itemExit);
                
        trayIcon.setPopupMenu(popupMenu);
        addTrayIcon();
 
        final MoveMouseListener mml = new MoveMouseListener(frame);

        frame.addMouseListener(mml);
        frame.addMouseMotionListener(mml);
        
        drawPanel = new DrawPanel();
        
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        frame.setAlwaysOnTop(true);
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.setSize(320, 240);       
        frame.setVisible(true);
        frame.setExtendedState(JFrame.NORMAL);
        
        setUIStyle();
                
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowIconified(WindowEvent e) {
                frame.setVisible(false);
            }
        });
    }
    
    private void createListenerTrayMenu(
            MenuItem itemOpen,
            MenuItem itemMinimize, 
            MenuItem itemNormal, 
            MenuItem itemSize1,
            MenuItem itemSize2,
            MenuItem itemSize3,
            MenuItem itemExit) {
        
        itemOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setState(JFrame.ICONIFIED);
                
                JFileChooser fileOpen = new JFileChooser();
                
                if(fileOpen.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    File file = fileOpen.getSelectedFile();
                    drawPanel.openImage(file);
                }
                
                frame.setVisible(true);
                frame.setExtendedState(JFrame.NORMAL);
                //frame.setLocationRelativeTo(null);
            }
        });
        
        itemMinimize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setState(JFrame.ICONIFIED); 
            }
        });
        
        itemNormal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setExtendedState(JFrame.NORMAL); 
                frame.setVisible(true);
            }
        });
        
        itemSize1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { 
                frame.setSize(160, 120);
                drawPanel.setSizeImage(160, 120);
                frame.repaint();
            }
        });
        
        itemSize2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { 
                frame.setSize(200, 150);
                drawPanel.setSizeImage(200, 150);
                frame.repaint();
            }
        });
        
        itemSize3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setSize(320, 240);
                drawPanel.setSizeImage(320, 240);
                frame.repaint();
            }
        });
        
        itemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                System.exit(0);
            }
        });
    }
    
    private void addTrayIcon() {
        try {
            systemTray.add(trayIcon);
        }
        catch(AWTException ex) { }
    }
    
    private void setUIStyle() {
        String styleName = "Windows";
        javax.swing.UIManager.LookAndFeelInfo infos[] = UIManager.getInstalledLookAndFeels();
        javax.swing.UIManager.LookAndFeelInfo alookandfeelinfo[];
        int j = (alookandfeelinfo = infos).length;
        for(int i = 0; i < j; i++) {
            javax.swing.UIManager.LookAndFeelInfo info = alookandfeelinfo[i];
            if(info.getName().equals(styleName)) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                    SwingUtilities.updateComponentTreeUI(frame);
                } catch(Exception e) { }
            }
        }
        //if no style was found - java style as default (Metall)
}
  
 }
