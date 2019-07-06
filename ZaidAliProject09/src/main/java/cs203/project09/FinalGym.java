package cs203.project09;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.sound.sampled.AudioInputStream;


import cs203.project07.ImageObjectdexEntryView;

import cs203.project08.FancyGUIConfiguredGym;
import cs203.project08.ImageObjectmon;

import java.util.Random;
import java.io.File;


import java.awt.Color;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton; 

import java.awt.event.ActionListener;

import java.awt.event.ActionEvent;

public class FinalGym extends FancyGUIConfiguredGym{

     private JSplitPane battlePane;
     private JPanel mainPanel;
     private JFrame fightGym;
     private int roundRightNow;

     public FinalGym() {
         super();
         roundRightNow=0;

         mainPanel=new JPanel(new BorderLayout());
         Random r = new Random();
         JLabel labelA =new JLabel("Team A");
         labelA.setBackground(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
         mainPanel.add(labelA, BorderLayout.LINE_START);
         JLabel labelB=new JLabel("Team B");
         labelB.setBackground(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
         mainPanel.add(labelB, BorderLayout.LINE_END);
         battlePane = new JSplitPane();
         battlePane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
         playMusic("Corruption.wav");
         
         


     }

     public void fight() {
        if (canTeamAFight()==false) {
            playSound("1_person_cheering-Jett_Rifkin-1851518140.wav");
            JOptionPane.showMessageDialog(fightGym,"Team "+getTeamB().getName() + " won!");
            System.exit(0);
        }

        else if (canTeamBFight()==false) {
            playSound("1_person_cheering-Jett_Rifkin-1851518140.wav");
            JOptionPane.showMessageDialog(fightGym,"Team "+ getTeamA().getName()+ " won!");
            System.exit(0);
        }

        else if (getCurrentRound()==getMaxRounds()) {
            playSound("Fake Applause-SoundBible.com-1541144825.wav");
            JOptionPane.showMessageDialog(fightGym,"It is a draw!");
            System.exit(0);

        }

        else {
            executeTurn();
        }

           
     }
     

     public void executeTurn() {
         try {
            Random r = new Random();
            ImageObjectmon omonA = (ImageObjectmon)getTeamA().nextObjectmon();
            if(omonA==null) { return;}
            ImageObjectdexEntryView viewA= omonA.getUpdatedObjectdexView();
            viewA.setBackground(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
            battlePane.setLeftComponent(viewA);

            ImageObjectmon omonB = (ImageObjectmon)getTeamB().nextObjectmon();
            if(omonB==null) {return;}
            ImageObjectdexEntryView viewB= omonB.getUpdatedObjectdexView();
            viewB.setBackground(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
            battlePane.setRightComponent(viewB);
            JButton fightButton=new JButton("Execute Turn");
            fightButton.addActionListener(new ActionListener(){
            
                public void actionPerformed(ActionEvent e) {

                    doRound();



                  
                }
            })
                
            ;

            
            mainPanel.add(fightButton,BorderLayout.PAGE_END);
            mainPanel.setBackground(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
            fightGym = new JFrame();
            fightGym.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainPanel.add(battlePane,BorderLayout.CENTER);
            fightGym.add(new JLabel("Round " +getCurrentRound()+"/"+getMaxRounds()),BorderLayout.PAGE_START);
            fightGym.add(mainPanel);
            fightGym.pack();
            fightGym.validate();
            fightGym.setSize(1200, 900);
            fightGym.setLocationRelativeTo(null);
    
            fightGym.setVisible(true);

             
         } catch (Exception ex) {
             JOptionPane.showMessageDialog(null, ex.toString());
         }
         



     }

     private void doRound() {
        ImageObjectmon omonA= (ImageObjectmon)getTeamA().nextObjectmon();
        ImageObjectmon omonB= (ImageObjectmon)getTeamB().nextObjectmon(); 
        tick();
        roundRightNow++;
        setCurrentRound(roundRightNow);
        JOptionPane.showMessageDialog(fightGym, "Round "+ (getCurrentRound())+ " out of "+ getMaxRounds() + " Begin fight!");
        int dmg =executeAttack(omonA,omonB);
        playSound("Realistic_Punch-Mark_DiAngelo-1609462330.wav");
        JOptionPane.showMessageDialog(fightGym,omonA.getName()+" dealt " +dmg+ " damage to " +omonB.getName());
        if (omonB.isFainted()) {
            playSound("Cat Meow-SoundBible.com-1977450526.wav");
            JOptionPane.showMessageDialog(fightGym,omonB.getName()+" fainted!");
            fight();
            return;
        }
        else{

        dmg=executeAttack(omonB,omonA);
        playSound("Slap-SoundMaster13-49669815.wav");
        JOptionPane.showMessageDialog(fightGym,omonB.getName()+" dealt " +dmg+ " damage to " +omonA.getName());
        if (omonA.isFainted()) {
            playSound("Cat Meow-SoundBible.com-1977450526.wav");
            JOptionPane.showMessageDialog(fightGym,omonA.getName()+" fainted!");
            
        }

        fight();

       }
     }

     private void playMusic(String path) {

        try {
            File file = new File(path);
            AudioInputStream stream=AudioSystem.getAudioInputStream(file);
            Clip clip =AudioSystem.getClip();
            clip.open(stream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(fightGym, e.toString());
        
        }


     }
     private void playSound(String path) {

        try {
            File file = new File(path);
            AudioInputStream stream=AudioSystem.getAudioInputStream(file);
            Clip clip =AudioSystem.getClip();
            clip.open(stream);
            clip.start();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(fightGym, e.toString());
        
        }


     }



      

    

 }
