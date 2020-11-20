package featureTest;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MouseEventTest extends JFrame{
 JPanel panel = new JPanel();
 JLabel la;
 
 MouseEventTest(){
  setTitle("마우스 이벤트 처리");
  setSize(300,300);
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 

  setContentPane(panel);//일반 pane이 아닌 직접생성한 panel로 Pane셋
  
  la = new JLabel("No Mouse Event");
  panel.add(la);
  panel.addMouseMotionListener(new MyMouseEvent());
  panel.addMouseListener(new MyMouseEvent());
  setVisible(true);
 }
 
 class MyMouseEvent implements MouseListener, MouseMotionListener
 {

  @Override
  public void mouseDragged(MouseEvent e) {
   la.setText("드래그중...."+e.getX()+","+e.getY());//좌표출력가능
   
  }

  @Override
  public void mouseMoved(MouseEvent e) {
   la.setText("움직이는중...."+e.getX()+","+e.getY());
   
  }

  @Override
  public void mouseClicked(MouseEvent e) {
   la.setText("마우스클릭...."+e.getX()+","+e.getY());
   
  }

  @Override
  public void mouseEntered(MouseEvent e) {
   la.setText("마우스들어옴...."+e.getX()+","+e.getY());
   panel.setBackground(Color.YELLOW);
   
  }

  @Override
  public void mouseExited(MouseEvent e) {
   la.setText("마우스나감...."+e.getX()+","+e.getY());
   panel.setBackground(Color.CYAN);
   
  }

  @Override
  public void mousePressed(MouseEvent e) {
   // TODO Auto-generated method stub
   
  }

  @Override
  public void mouseReleased(MouseEvent e) {
   la.setText("마우스땐곳...."+e.getX()+","+e.getY());
   //드래그중 멈출시 보임
   
  }
  
 }
 
 public static void main(String[] ar)
 {
  new MouseEventTest();
 }

}