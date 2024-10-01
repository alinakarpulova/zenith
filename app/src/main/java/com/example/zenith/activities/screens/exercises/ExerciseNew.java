package com.example.zenith.activities.screens.exercises;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.zenith.R;
import com.example.zenith.components.GenericDialog;
import com.example.zenith.models.ExerciseBodyPart;
import com.example.zenith.models.ExerciseCategory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;

public class ExerciseNew extends AppCompatActivity {
    private Toolbar toolbar;
    private Button categoryDialogBtn;
    private Button bodypartDialogBtn;
    private EditText nameTextField;
    private ExerciseBodyPart selectedBodypart;
    private ExerciseCategory selectedCategory;
    private boolean canSubmit;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.exercise_new_activity);

        List<ExerciseCategory> categories = Arrays.asList(ExerciseCategory.values());
        List<ExerciseBodyPart> bodyParts = Arrays.asList(ExerciseBodyPart.values());

        nameTextField = findViewById(R.id.exercise_name_edit_txt);

        Resources res = ExerciseNew.this.getResources();
        GenericDialog<ExerciseCategory> categoriesDialog = new GenericDialog<>(res.getString(R.string.category_label), categories, ExerciseNew.this);
        GenericDialog<ExerciseBodyPart> bodyPartsDialog = new GenericDialog<>(res.getString(R.string.bodypart_label), bodyParts, ExerciseNew.this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        categoryDialogBtn = findViewById(R.id.category_dialog_btn);
        categoryDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoriesDialog.showDialog(selectedCategory);
            }
        });

        categoriesDialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (categoriesDialog.getSelectedItem() != null) {
                    selectedCategory = categoriesDialog.getSelectedItem();
                    categoryDialogBtn.setText(selectedCategory.toString());
                    enableSubmit();
                }
            }
        });

        bodyPartsDialog.getDialog().setOnDismissListener((dialogInterface) -> {
            if (bodyPartsDialog.getSelectedItem() != null) {
                selectedBodypart = bodyPartsDialog.getSelectedItem();
                bodypartDialogBtn.setText(selectedBodypart.toString());
                enableSubmit();
            }
        });

        bodypartDialogBtn = findViewById(R.id.bodypart_dialog_btn);
        bodypartDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bodyPartsDialog.showDialog(selectedBodypart);
            }
        });

        FloatingActionButton submitExerciseButton = findViewById(R.id.submit_exercise_btn);
        submitExerciseButton.setActivated(false);

        nameTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                enableSubmit();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        submitExerciseButton.setOnClickListener((view) -> {
            if (canSubmit) {
                Toast.makeText(ExerciseNew.this, "Exercise Created", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(ExerciseNew.this, "Please complete all fields", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void enableSubmit() {
        if (!nameTextField.getText().toString().isBlank() && selectedCategory != null && selectedBodypart != null) {
            canSubmit = true;
        }
    }

}


