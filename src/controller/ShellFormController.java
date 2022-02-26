package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class ShellFormController {

    public TextArea txtStatement;
    public TextArea txtOutPut;
    public Button btnExecute;
    private String host;
    private String port;
    private String username;
    private String password;

    public void btnExecute_OnAction(ActionEvent event) {



    }

    public void initData(String host, String port, String username, String password){
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }
}
