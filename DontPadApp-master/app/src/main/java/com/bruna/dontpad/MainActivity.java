package com.bruna.dontpad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    //Declaração dos campos de texto
    EditText etTag;
    EditText etText;

    FirebaseDatabase database;
    DatabaseReference myRef;

    //Declaração de uma string para ser usada posteriormente
    String myLastText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Buscando os campos de texto tag e texto
        etTag   = findViewById(R.id.etTag);
        etText  = findViewById(R.id.etText);

        //Um listener no campo de texto da tag
        etTag.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Antes de digitar não faz nada
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Durante a digitação não faz nada
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Após o usuário digitar a tag aloca ela em uma string tag
                String tag  = etTag.getText().toString();
                //Esvazia o campo de texto
                etText.setText("");
                //Chama o método que busca no Firebase, e passa a tag como parâmetro
                getFromDatabase(tag);

            }
        });

        //Um listener no campo do texto
        etText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Novamente não faz nada
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Faz nada
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Após o usuário digitar o texto, aloca a tag e o texto em strings
                String tag  = etTag.getText().toString();
                String text = etText.getText().toString();

                //Checa se a tag tem conteúdo
                if (! tag.equals("")) {
                    //Se tiver, checa se o texto tem conteúdo
                    if (! text.equals("")) {
                        //Se tiver, chama o método que salva no Firebase e passa a tag e texto como parâmetros
                        saveToDatabase(tag, text);
                    }
                }
            }
        });

    }

    //Um método para buscar no Firebase, recebe a tag como parâmetro
    public void getFromDatabase(String tag) {

        //Checa se a tag está vazia, se estiver, nada faz
        if (tag.equals("")) {
            return;
        }

        //Atualiza referência do myref
        database = FirebaseDatabase.getInstance();
        //Usa a tag como referiência para busca
        myRef = database.getReference(tag);

        //Um listener na linha do Firebase
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            //Sempre que o Texto [o dado retornado após passar a tag como referência de busca] do Firebase for alterado
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Quando o Texto for alterado, ou carregado, cria uma string value alocando o texto
                String value = dataSnapshot.getValue(String.class);

                //Checa se a linha no Firebase está vazia, se estiver, a string auxiliar último texto digitado fica vazia
                //E a string value, com Texto da linha no Firebase também fica vazio
                if (value == null) {
                    myLastText = "";
                    value = "";
                }

                //Se o último texto digitado tiver conteúdo diferente do salvo no Firebase previamente
                if (! myLastText.equals(value)) {
                    //O último texto digitado passa a ser o que vai para a linha do Firebase
                    myLastText = value;
                    //E aloca o Texto no campo de texto
                    etText.setText(value);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                //Nada faz
            }
        });


    }

    //Método para salvar no Firebase após alterações
    public void saveToDatabase(String tag, String text) {
        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(tag);

        //Checa se o texto alterado é igual a versão do Firebase e nada faz
        if (myLastText.equals(text)) {
            return;
        }

        //O último texto digitado é alocado na string text
        myLastText = text;
        //Passa o novo texto para a linha do Firebase
        myRef.setValue(text);
    }

}
