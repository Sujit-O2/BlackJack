import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BlackJack implements ActionListener{
    ArrayList<String> aa;
    String Message;
//dealer
    String hiddenCard;
    ArrayList<String> dHand;
    int dSum;
    int dAce;
    int cardWid=110;
    int cardHei=150;
//Player
    ArrayList<String> PHand;
    int PSum;
    int PAce;
//Graphics
JFrame ff=new JFrame("Black Jack");
JPanel game=new JPanel(){
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        try{
            Image HiddenImage=new ImageIcon(getClass().getResource("./cards/BACK.png")).getImage();
            if(!stay.isEnabled()){
                    HiddenImage=new ImageIcon(getClass().getResource("./cards/"+hiddenCard+".png")).getImage();
            }
            g.drawImage(HiddenImage, 20, 20, cardWid, cardHei, null);
            int j=0;
            for(String i:dHand){
                Image img=new ImageIcon(getClass().getResource("/cards/"+i+".png")).getImage();
                
                g.drawImage(img,cardWid+25+(cardWid+5)*j,20,cardWid,cardHei,null);
                j++;
            }
            j=0;
            for(String i:PHand){
                Image img=new ImageIcon(getClass().getResource("/cards/"+i+".png")).getImage();
                g.drawImage(img,20+(cardWid+5)*j,410,cardWid,cardHei,null);
                j++;
            }
            if(!stay.isEnabled()){
                result();
                g.setFont(new Font("Arial",Font.PLAIN,30));
                g.setColor(Color.white);
                g.drawString(Message,300,300);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
};
JPanel bPanel;
JButton hit=new JButton("HIT");
JButton stay=new JButton("STAY");

    BlackJack(){
        startGame();
        ff.setSize(700,700);
        ff.setLocationRelativeTo(null);
        ff.setVisible(true);
        ff.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ff.setResizable(false);
        game.setBackground(Color.darkGray);
        ff.add(game);
        bPanel=new JPanel();

        hit.setFocusable(false);
        bPanel.add(hit);
        stay.setFocusable(false);
        bPanel.add(stay);

        ff.add(bPanel,BorderLayout.SOUTH);
        hit.addActionListener(this);
        stay.addActionListener(this);
        game.repaint();
        }
        public void startGame(){
            Cards();
            Suffal();
// dealer
            dSum=0;
            dAce=0;
            dHand =new ArrayList<String>();
            hiddenCard=aa.remove(aa.size()-1);
            dSum+=getValue(hiddenCard);
            dAce+=isAce(hiddenCard)?1:0;
            String showC=aa.remove(aa.size()-1);
            dSum+=getValue(showC);
            dAce+=isAce(showC)?1:0;
            dHand.add(showC);
            System.out.println(dSum);
            System.out.println(dAce);
            System.out.println(hiddenCard);
            System.out.println(dHand);
//Player
            PSum=0;
            PAce=0;
            PHand=new ArrayList<String>();
            for(int i=0;i<2;i++){
            showC=aa.remove(aa.size()-1);
            PSum+=getValue(showC);
            PAce+=isAce(showC)?1:0;
            PHand.add(showC);
            }
            hitting();
            System.out.println(PSum);
            System.out.println(dAce);
            System.out.println(PHand);
            
        }
        private boolean isAce( String showC1) {
            return showC1.equals("A");
                    }
                    private int getValue(String hiddenCard2) {
            String[] HC=hiddenCard2.split("-");
            if("AJQK".contains(HC[0])){
                    if(HC[0].equals("A")){
                        return 11;
                            }
                    if(HC[0].equals("J")){
                        return 10;
                            }
                    if(HC[0].equals("Q")){
                        return 10;
                            }
                    if(HC[0].equals("K")){
                        return 10;
                            }
                        }
                            return Integer.parseInt(HC[0]);
                    }
                    public void Cards() {
            String[] Val = {"H", "D", "C", "S"};
            String[] Num = {"A", "2", "3", "4", "5","6","7","8","9","10","J","K","Q"};
            aa=new ArrayList<>();
        for(String i : Val){
            for(String j : Num){
                aa.add(j+"-"+i);
            }
        }
        System.out.println(aa);
    }
    public  void Suffal(){
    for(int i=0;i<aa.size();i++){
        Random rr=new Random();
        int j=rr.nextInt(aa.size());
        String curr=aa.get(i);
        String init=aa.get(j);
        aa.set(i,init);
        aa.set(j,curr);
    }
    System.out.println("\n"+aa);
    }
    
    public void hitting(){
        if(redusePAce()>21){
                    hit.setEnabled(false);
                }
            }
            private int redusePAce() {
                while(PSum>21&&PAce>0){
                    PSum-=10;
                    PAce-=1;
                }
                return PSum;
            }
                private int reduseDAce() {
                    while(dSum>21&&dAce>0){
                        dSum-=10;
                        dAce-=1;
                    }
                    return dSum;
            }

            @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==hit){
            String newCard=aa.remove(aa.size()-1);
            PSum+=getValue(newCard);
            PAce+=isAce(newCard)?1:0;
            System.out.println(PSum);
            PHand.add(newCard);
            hitting();
            game.repaint();
        }
        if(e.getSource()==stay){
            hit.setEnabled(false);
            stay.setEnabled(false);
            while(dSum<21){
                String card=aa.remove(aa.size()-1);
                dSum+=getValue(card);
                dAce+=isAce(card)?1:0;
                dHand.add(card);
                System.out.println(dHand);
                reduseDAce();
            }
            game.repaint();
        }
    }
    public void result(){
        if(PSum>21 ){
            Message="You Lose !";
        }
        else if(dSum>21){
            Message="You Win !";
        }
        else if(PSum==dSum){
            Message="Draw Match !";
        }
        else if(PSum>dSum){
            Message="You Win !";
        }
        else{
            Message="You Lose !";
        }
    }
}
