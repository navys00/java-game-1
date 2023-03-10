package com.example.game_1;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Shoot_controller {

//        @FXML
//    public Label Shoots_label;
//
//    @FXML
//    public Label Hits_label;
    private int MaxW=495;
    private boolean run = false;
    private boolean pause = false;
    private final Circle circ_L, circ_B;

    @FXML
    private Line Arrow;

    private double Start_coord_X=125;
    public int shoots=0;
    public int hits=0;


    Thread t;

    private double Quad(double v){
        return v*v;
    }

   public Shoot_controller(Line arr, Circle circ_l, Circle circ_b,Label hits_score ){
        Arrow=arr;
        circ_L=circ_l;
        circ_B=circ_b;
    }

    public void pause(){
        pause=true;
    }
    synchronized void Cont(){
        notifyAll();
    }
    public void continue_play(){
        Cont();
    }
    public void Stop(){

        if(t==null){
            return;
        }
        Arrow.setLayoutX(Start_coord_X);
        t.interrupt();
        t=null;


    }
    public void start(){
        Arrow_move(circ_L,circ_B,Arrow,50);
    }
    public void Arrow_move(Circle Little_circ, Circle Big_circle, Line arr, int Sleep){
        shoots++;

        t= new Thread(
                ()-> {
                    run=true;
                    double move = 10;
                    while (run) {

                        double tmp=arr.getLayoutX();
                        double tmpY= arr.getLayoutY();
                        double new_x=tmp+move;

                        if(((Quad(Big_circle.getLayoutX()-(arr.getLayoutX())))+Quad((Big_circle.getLayoutY()-arr.getLayoutY())))<Quad(40)){
                            hits++;
                            Stop();

                        }

                        else if((Quad(Little_circ.getLayoutX()-arr.getLayoutX())+Quad(Little_circ.getLayoutY()-arr.getLayoutY()))<=Quad(25)){
                            hits+=2;
                            Stop();

                        }
                        else if(new_x+1>MaxW){
                            Stop();
                        }
                        else{
                            Platform.runLater(()->arr.setLayoutX(new_x));
                        }



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
