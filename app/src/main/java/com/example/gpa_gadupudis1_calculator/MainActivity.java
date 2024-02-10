package com.example.gpa_gadupudis1_calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText[] coursePoints;
    Button computeButton;
    TextView gpaText;

    boolean isGpaComputed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize EditText fields
        coursePoints = new EditText[]{
                findViewById(R.id.course1),
                findViewById(R.id.course2),
                findViewById(R.id.course3),
                findViewById(R.id.course4),
                findViewById(R.id.course5)
        };

        // Initialize Compute GPA button
        computeButton = findViewById(R.id.btn_computeGPA);
        computeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                computeGPA();
            }
        });

        // Initialize GPA TextView
        gpaText = findViewById(R.id.GPA);

        // Add TextChangedListener to each EditText field
        for (EditText coursePoint : coursePoints) {
            coursePoint.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (isGpaComputed) {
                        computeButton.setText("Compute GPA");
                        isGpaComputed = false;
                    }
                }
            });
        }
    }

    private void computeGPA() {
        boolean isValidInput = true;
        boolean anyFieldEmpty = false;

        // Check for empty fields and validate input
        for (EditText coursePoint : coursePoints) {
            String pointString = coursePoint.getText().toString().trim();
            if (TextUtils.isEmpty(pointString)) {
                coursePoint.setBackgroundColor(Color.RED);
                anyFieldEmpty = true;
            } else {
                try {
                    double points = Double.parseDouble(pointString);
                    if (points < 0 || points > 100) {
                        coursePoint.setBackgroundColor(Color.RED);
                        isValidInput = false;
                    } else {
                        coursePoint.setBackgroundColor(Color.TRANSPARENT);
                    }
                } catch (NumberFormatException e) {
                    coursePoint.setBackgroundColor(Color.RED);
                    isValidInput = false;
                }
            }
        }

        if (anyFieldEmpty) {
            return;
        }

        if (!isValidInput) {
            return;
        }

        // Compute GPA
        double totalPoints = 0;
        int totalCourses = 0;
        for (EditText coursePoint : coursePoints) {
            double points = Double.parseDouble(coursePoint.getText().toString().trim());
            totalPoints += points;
            totalCourses++;
        }
        double gpa = totalPoints / totalCourses;

        // Display GPA
        gpaText.setText(String.format("GPA: %.2f", gpa));

        // Change background color based on GPA range
        if (gpa < 60) {
            gpaText.setBackgroundColor(Color.RED);
        } else if (gpa >= 60 && gpa <= 79) {
            gpaText.setBackgroundColor(Color.YELLOW);
        } else {
            gpaText.setBackgroundColor(Color.GREEN);
        }

        // Change button text to "Clear Form"
        computeButton.setText("Clear Form");
        isGpaComputed = true;
    }

    public void clearForm(View view) {
        // Clear EditText fields
        for (EditText coursePoint : coursePoints) {
            coursePoint.setText("");
            coursePoint.setBackgroundColor(Color.TRANSPARENT);
        }
        // Reset GPA display
        gpaText.setText("GPA");
        gpaText.setBackgroundColor(Color.TRANSPARENT);
        // Change button text back to "Compute GPA"
        computeButton.setText("Compute GPA");
        isGpaComputed = false;
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Handle configuration changes here
        // No action needed for this example
    }
}
