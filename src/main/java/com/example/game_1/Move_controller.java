package com.example.game_1;

import javafx.scene.shape.Circle;
import javafx.application.Platform;

public class Move_controller {

    private final Circle circ_L, circ_B;

    private Thread t;
    private double Start_coord_L,Start_coord_B;
    private  final double maxH=336;
   private boolean run = false;
    private boolean pause = false;

    public Move_controller(Circle circ_l, Circle circ_b)
    {  circ_L=circ_l;
       circ_B=circ_b;
       Start_coord_L= circ_l.getLayoutY();
       Start_coord_B= circ_b.getLayoutY();

    }
    public void pause(){
        pause=true;
    }

    public void Stop(){
        if(t==null){
            return;
        }
        circ_B.setLayoutY(Start_coord_B);
        circ_L.setLayoutY(Start_coord_L);
        t.interrupt();
        t=null;

    }
    synchronized void Cont(){
        notifyAll();
    }
    public void continue_play(){
        Cont();
    }
    public void start(){

        Target_move(circ_L,25);
        Target_move(circ_B,50);
    }
    public void Target_move(Circle circ, int Sleep){
      t= new Thread(
                ()-> {
                    run=true;
                    double move = 5;
                        while (run) {
                            double Radius = circ.getRadius();
                            double tmp = circ.getLayoutY();
                            double new_y=tmp+move;
                            double new_pos;
                            if(new_y+Radius>maxH || new_y-Radius<0){
                                move=-move;
                                new_y+=move;
                                new_pos=new_y;
                            }
                            else{
                                new_pos=new_y;
                            }
                            Platform.runLater(()->circ.setLayoutY(new_pos));


                            try {
                                if(pause){
                                    synchronized(this)
                                    {
                                        wait();
                                        pause = false;
                                    }
                                }

                                Thread.sleep(Sleep);
                            } catch (InterruptedException ex) {
                                    run=false;
                            }
                        }


                }
        );
        t.start();
    }


}
