package com.teachapp.teachapp;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class UserRegisterActivity extends BaseActivity {

    private String[] listItems;
    private boolean[] checkedItems;
    private ArrayList<Integer> mUserItems = new ArrayList<>();

    private AutoCompleteTextView aName;
    private AutoCompleteTextView aLastName;
    private AutoCompleteTextView aEmail;
    private AutoCompleteTextView aBirthdate,aPhone;
    private EditText ePassword,eAreas;
    private Spinner aUniversity;
    private FirebaseAuth mAuth;
    private TextInputLayout tiAreas;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        Animation fromBottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        Animation fromTop = AnimationUtils.loadAnimation(this,R.anim.fromtop);
        TextView titleRegister = findViewById(R.id.titleRegister);
        titleRegister.setAnimation(fromBottom);

        aName = (AutoCompleteTextView) findViewById(R.id.autName);
        aLastName = (AutoCompleteTextView) findViewById(R.id.autLastName);
        aEmail = (AutoCompleteTextView) findViewById(R.id.autEmail);
        aBirthdate = (AutoCompleteTextView) findViewById(R.id.autBirthDate);
        aPhone = (AutoCompleteTextView) findViewById(R.id.autPhone);
        aUniversity = (Spinner) findViewById(R.id.spinnerU);
        ePassword = (EditText) findViewById(R.id.txtPasswordR);
        eAreas = (EditText) findViewById(R.id.txtAreas);
        tiAreas = (TextInputLayout)  findViewById(R.id.tiAreas);

        Button btnRegisterUser = (Button) findViewById(R.id.btn_register);

        llenarSpinnerUniversities();
        llenarAreas();
        asignarCalendario();

        btnRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                registeUser();
            }
        });

        mAuth = FirebaseAuth.getInstance();
    }

    private void asignarCalendario() {
        tiAreas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendar();
            }
        });
        aBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendar();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String y = String.valueOf(year);
                String m = String.valueOf(month < 10 ? "0"+month : month);
                String d = String.valueOf(dayOfMonth < 10 ? "0"+dayOfMonth:dayOfMonth);
                aBirthdate.setText(d+"/"+m+"/"+y);
            }
        };
    }

    public void openCalendar(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                UserRegisterActivity.this,
                android.R.style.Theme_DeviceDefault_Light_Dialog,
                mDateSetListener,
                year,month,day
        );
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.show();
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            Toast.makeText(UserRegisterActivity.this, "Ya estoy.",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void llenarSpinnerUniversities() {
        final ArrayList<String> comboU = new ArrayList<>();
        comboU.add("-Seleccione-");
        FireDatabase.getInstance().child("Utilities").child("Universities").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Utilities universitie = snapshot.getValue(Utilities.class);
                    comboU.add(universitie.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,comboU);
        aUniversity.setAdapter(adapter);
        aUniversity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("onItemSelected",""+position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("onNothingSelected",""+parent.toString());
            }
        });
    }

    private void llenarAreas() {
        final ArrayList<Area> comboA = new ArrayList<>();
        FireDatabase.getInstance().child("Utilities").child("Areas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Area area = snapshot.getValue(Area.class);
                    comboA.add(area);
                }
                listItems = new String[comboA.size()];
                for (int i = 0; i<listItems.length;i++) {
                    listItems[i] = comboA.get(i).getName();
                }
                checkedItems = new boolean[listItems.length];
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("onCancelled",""+databaseError.getDetails());
            }
        });
    }

    private void registeUser(){

        if (!validateForm()) {
            return;
        }
            showProgressDialog();
            // [START create_user_with_email]
            mAuth.createUserWithEmailAndPassword(aEmail.getText().toString(), ePassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            validateUser(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(UserRegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_LONG).show();
                        }
                        hideProgressDialog();
                    }
                });

    }

    private void validateUser(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            Toast.makeText(UserRegisterActivity.this, "Registro "+user.getEmail(),
                    Toast.LENGTH_LONG).show();
            registerUserFirebase();
            Intent intent = new Intent(UserRegisterActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(UserRegisterActivity.this, "Registro incompleto.",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void registerUserFirebase(){
        String[] areas = eAreas.getText().toString().split(",");
        ArrayList<Area> list = new ArrayList();
        for (int i = 0; i<areas.length;i++){
            list.add(new Area(areas[i]));
        }

        User user = new User(
                aEmail.getText().toString(),
                aName.getText().toString(),
                aLastName.getText().toString(),
                aPhone.getText().toString(),
                "Bogotá D.C.",
                aUniversity.getSelectedItem().toString(),
                list
        );
        FireDatabase.getInstance().child("User").push().setValue(user);
        Toast.makeText(UserRegisterActivity.this,"Usuario registrado",Toast.LENGTH_LONG).show();
    }

    private boolean validateForm() {
        boolean valid = true;

        String name = aName.getText().toString();;
        String lastname = aLastName.getText().toString();
        String phone = aPhone.getText().toString();
        String email = aEmail.getText().toString();
        String dateBirth = aBirthdate.getText().toString();
        String college = aUniversity.getSelectedItem().toString();
        String password = ePassword.getText().toString();
        String areas = eAreas.getText().toString();

        //Name
        if (TextUtils.isEmpty(name)) {
            aName.setError("Required.");
            valid = false;
        } else {
            aName.setError(null);
        }

        //LastName
        if (TextUtils.isEmpty(lastname)) {
            aLastName.setError("Required.");
            valid = false;
        } else {
            aLastName.setError(null);
        }

        //Phone
        if (TextUtils.isEmpty(phone)) {
            aPhone.setError("Required.");
            valid = false;
        } else if (phone.length() < 10){
            aPhone.setError("Phone is too short.");
            valid = false;
        }else {
            aPhone.setError(null);
        }

       //Email
        if (TextUtils.isEmpty(email)) {
            aEmail.setError("Required.");
            valid = false;
        } else if (!email.contains("@")){
            aEmail.setError("This email address is invalid.");
            valid = false;
        }else {
            aEmail.setError(null);
        }

        //date birthday
        if(TextUtils.isEmpty(dateBirth)){
            aBirthdate.setError("Required.");
            valid = false;
        } else {
            aBirthdate.setError(null);
        }

        //University
        if (college.equals("-Seleccione-")) {
            Toast.makeText(UserRegisterActivity.this,"Select a university",Toast.LENGTH_LONG).show();
            valid = false;
        }

        //date birthday
        if(TextUtils.isEmpty(areas)){
            eAreas.setError("Required.");
            valid = false;
        } else {
            eAreas.setError(null);
        }

        //Password
        if (TextUtils.isEmpty(password)) {
            ePassword.setError("Required.");
            valid = false;
        } else {
            ePassword.setError(null);
        }

        return valid;
    }

    /*
    private void sendEmailVerification() {

        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(UserRegisterActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UserRegisterActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }
*/
    public void openDialog(View view) {
        hideKeyboard(view);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(UserRegisterActivity.this);
        mBuilder.setTitle("Areas");
        mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                if(isChecked){
                    mUserItems.add(position);
                }else{
                    mUserItems.remove((Integer.valueOf(position)));
                }
            }
        });

        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String item = "";
                for (int i = 0; i < mUserItems.size(); i++) {
                    item = item + listItems[mUserItems.get(i)];
                    if (i != mUserItems.size() - 1) {
                        item = item + ", ";
                    }
                }
                eAreas.setText(item);
            }
        });
        mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                for (int i = 0; i < checkedItems.length; i++) {
                    checkedItems[i] = false;
                    mUserItems.clear();
                    eAreas.setText("");
                }
            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }
}