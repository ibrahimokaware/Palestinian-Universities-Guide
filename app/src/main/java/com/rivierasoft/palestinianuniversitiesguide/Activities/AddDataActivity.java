package com.rivierasoft.palestinianuniversitiesguide.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rivierasoft.palestinianuniversitiesguide.databinding.ActivityAddDataBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddDataActivity extends AppCompatActivity {

    private ActivityAddDataBinding binding;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference facultiesRef = db.collection("faculties");
    private CollectionReference programsRef = db.collection("programs");
    private Map<String, Object> data;

    private int programCount = 0, facultyCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddDataBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //updateDocument();

//        programsRef
//                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        DocumentReference docRef = programsRef.document(document.getId());
//                        docRef.update("price", "")
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        //Toast.makeText(getActivity(), "سيتم الحفظ عند توفر الإنترنت.", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                    }
//                } else {
//                    Toast.makeText(getApplicationContext(), "Document does not exist", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        binding.addProgramBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                programCount++;
                data = new HashMap<>();
                data.put("university_id", Integer.parseInt(binding.universityIdEt.getText().toString()));
                data.put("cover", binding.coverEt.getText().toString());
                data.put("logo", binding.logoEt.getText().toString());
                data.put("degree", binding.degreeSp.getSelectedItem().toString());
                data.put("duration", binding.durationSp.getSelectedItem().toString());
                data.put("program_type", binding.programTypeSp.getSelectedItem().toString());
                data.put("faculty_id", Integer.parseInt(binding.facultyIdEt.getText().toString()));
                data.put("department", binding.departmentEt.getText().toString());
                data.put("id", programCount);
                data.put("name", "");
                data.put("link", "");
                data.put("plan", "");
                data.put("price", "");
                if (!binding.programTypeSp.getSelectedItem().toString().equals("3")) {
                    data.put("rate", "");
                    if (binding.literaryRb.isChecked()) {
                        data.put("literary", "1");
                        data.put("average_l", 0);
                        data.put("average_s", 0);
                    } else if (binding.scientificRb.isChecked()) {
                        data.put("literary", "0");
                        data.put("average_s", 0);
                    }
                }

                programsRef
                        .add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(), String.valueOf(programCount), Toast.LENGTH_SHORT).show();
                                //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Error adding document", Toast.LENGTH_SHORT).show();
                                //Log.w(TAG, "Error adding document", e);
                            }
                        });
            }
        });

        binding.addFacultyBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facultyCount++;
                data = new HashMap<>();
                data.put("university_id", Integer.parseInt(binding.universityIdEt.getText().toString()));
                data.put("id", facultyCount);
                data.put("program_type", binding.programTypeSp.getSelectedItem().toString());
                data.put("name", "");
                data.put("no_programs", "");

                facultiesRef
                        .add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(), String.valueOf(facultyCount), Toast.LENGTH_SHORT).show();
                                //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Error adding document", Toast.LENGTH_SHORT).show();
                                //Log.w(TAG, "Error adding document", e);
                            }
                        });
            }
        });

        binding.resetBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facultyCount = 0;
                programCount = 0;
            }
        });
    }

    public void updateDocument() {
        programsRef
                .whereEqualTo("university_id", 7)
                .whereEqualTo("department", "كلية الطب وعلوم الصحة")
                //.whereEqualTo("cover", "https://firebasestorage.googleapis.com/v0/b/palestinian-universities-guide.appspot.com/o/Logo%2Fpoletechnic.png?alt=media&token=3eae2e7e-89b7-4f57-9490-d92f7ca95de0")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        DocumentReference docRef = programsRef.document(document.getId());
                        docRef
                                .update("department", "كلية الطب وعلوم الصحةة")
                                //.update("cover", "https://firebasestorage.googleapis.com/v0/b/palestinian-universities-guide.appspot.com/o/Cover%2Fpole_title.jpg?alt=media&token=d8199b81-fb4b-4c2b-9207-01b6dda84cb5")
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        //Toast.makeText(getActivity(), "Ø³ÙŠØªÙ… Ø§Ù„Ø­ÙØ¸ Ø¹Ù†Ø¯ ØªÙˆÙØ± Ø§Ù„Ø¥Ù†ØªØ±Ù†Øª.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Document does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
