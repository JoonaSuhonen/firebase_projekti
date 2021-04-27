package testi.firestoreprojekti;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button msaveButton;
    private Button mloadButton;
    private EditText mnimiTeksti;
    private TextView mtervehdysTeksti;
    private FirebaseFirestore mFirestore;
    private CollectionReference mCollection;


//https://www.youtube.com/watch?v=CQ5qcJetYAI Tässä linkki miten lisätä kuvia, kun sen aika on


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirestore = FirebaseFirestore.getInstance();
        mnimiTeksti = (EditText) findViewById(R.id.nimiTeksti);
        msaveButton = (Button) findViewById(R.id.saveButton);
        mloadButton = (Button) findViewById(R.id.loadButton);
        mtervehdysTeksti = (TextView) findViewById(R.id.tervehdysTeksti);

        msaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mCollection = mFirestore.collection("users");
                String username = mnimiTeksti.getText().toString();
                String id = mnimiTeksti.getText().toString();
                Map<String, String> userMap = new HashMap<>();
                userMap.put("name", username);
                userMap.put("image", "image_link");

                mFirestore.collection("users").document(id).set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Lisätty käyttäjä " + username, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        mloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = mnimiTeksti.getText().toString();
                String id = mnimiTeksti.getText().toString();
                mFirestore.collection("users").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if(documentSnapshot.exists() && documentSnapshot !=null) {

                                String username = documentSnapshot.getString("name");
                                mtervehdysTeksti.setText("Tervetuloa " + username);
                                Toast.makeText(MainActivity.this,"Tervetuloa " + username, Toast.LENGTH_SHORT).show();
                            }

                        } else {

                            Toast.makeText(MainActivity.this,"Virhe haettaessa käyttäjää", Toast.LENGTH_SHORT).show();
                            mtervehdysTeksti.setText("Käyttäjää ei löytynyt");
                        }
                    }
                });

                mCollection = mFirestore.collection("users");
            }
        });
    }
}






