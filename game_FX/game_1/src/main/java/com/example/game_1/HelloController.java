package com.example.game_1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class HelloController {
    @FXML
    public Label Shoots_label;

    @FXML
    public Label Hits_label;

    @FXML
    private Circle Big_circle;

    @FXML
    private Circle Little_circle;
    @FXML
    private Line Arrow;

     private Move_controller targetController;

     private Shoot_controller Arrow_controller;

    @FXML
    public void initialize(){

            targetController=new Move_controller(Little_circle, Big_circle);
            Arrow_controller=new Shoot_controller(Arrow,Little_circle, Big_circle,Hits_label);
            Shoots_label.setText("Кол-во выстрелов: "+0);
            Hits_label.setText("Кол-во попаданий: "+0);
    }

    @FXML
    protected void onHelloButtonClick() {
        targetController.start();
    }

    @FXML
    protected void Stop_game_action(){targetController.Stop();

        Arrow_controller.Stop();
    }

    @FXML
    protected void continue_game_action(){targetController.continue_play();

        Arrow_controller.continue_play();
    }

    @FXML
    protected void Pause_game_action(){ targetController.pause();
        Arrow_controller.pause();

    }
    @FXML
    protected void Shoot_action(){
        Arrow_controller.start();
        Shoots_label.setText("Кол-во выстрелов: "+String.valueOf(Arrow_controller.shoots));
        Hits_label.setText("Кол-во попаданий: "+String.valueOf(Arrow_controller.hits));

    }


}