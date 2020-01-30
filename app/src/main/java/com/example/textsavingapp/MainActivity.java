package com.example.textsavingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class MainActivity extends AppCompatActivity {

    Model theModel;
    TextView theQuestionView;
    EditText theCurrentAnswerEdit;
    Button theGoToPrevious;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        theQuestionView = findViewById(R.id.questionView);
        theCurrentAnswerEdit = findViewById(R.id.currentAnswerEdit);
        theGoToPrevious = findViewById(R.id.goToPrevious);

        theCurrentAnswerEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {


                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    // put current answer into Model and display next question
                    theQuestionView.setText(theModel.recordAnswerAndGetNext(theCurrentAnswerEdit.getText().toString()));

                    // clear answer textview
                    theCurrentAnswerEdit.setText(theModel.getCurrentAnswer());
                    System.out.println("enter key YES");
                    return true;
                }

                System.out.println("enter key NO");
                return false;

            }
        });
        initializeModel();

        theQuestionView.setText(theModel.getFirstQuestion());
    }

    private void initializeModel() {
        File saveModelFile = new File(getApplicationContext().getFilesDir(), "Q & A");
        if (saveModelFile.exists()) {
            try {
                FileInputStream saveModelFileStream = new FileInputStream(saveModelFile);
                ObjectInputStream in = new ObjectInputStream(saveModelFileStream);
                theModel = (Model)in.readObject();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (theModel == null) {
            theModel = new Model();
        }


    }

    public void prevPressed(View view) {
        System.out.println("prevPressed");
        String prevQuestion = theModel.getPrevQuestion();
        String prevAnswer = theModel.getCurrentAnswer();
        theQuestionView.setText(prevQuestion);
        theCurrentAnswerEdit.setText(prevAnswer);
    }

    protected void onStop() {
        this.saveModel();

        super.onStop();
    }

    private void saveModel() {

        try {
            File savedModelFile = new File(getApplicationContext().getFilesDir(), "Q & A");
            FileOutputStream savedModelFileStream = new FileOutputStream(savedModelFile);
            ObjectOutputStream out = new ObjectOutputStream(savedModelFileStream);
            out.writeObject(theModel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
