package ast.fit.bstu.oop3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;


public class MainPage extends AppCompatActivity implements TextWatcher {

    private ImageView imageView;
    private final int Pick_image = 1;
    private String name= null;
    private String profile = null;
    private SQLiteDatabase db;
    EditText n,s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        /*
// Create a Uri from an intent string. Use the result to create an Intent.
        Uri gmmIntentUri = Uri.parse("google.streetview:cbll=46.414382,10.013988");

// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
// Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

// Attempt to start an activity that can handle the Intent
        startActivity(mapIntent); */
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            finish();
        }
        else {
            profile= super.getFilesDir()+"lab4-main.json";
            name= super.getFilesDir()+"lab4-main1.json";
            imageView = (ImageView) findViewById(R.id.imageView);
            Button but = (Button) findViewById(R.id.button1);
            n= findViewById(R.id.username);s= findViewById(R.id.usersurname);
            n.addTextChangedListener(this);s.addTextChangedListener( this);
            try {
                String img = new String(Files.readAllBytes(Paths.get(profile)));
                Uri imageUri = Uri.parse(img);
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);
                String nameands = new String(Files.readAllBytes(Paths.get(name)));
                //todo
                String[] temps = nameands.split("\\s*([-]|[;])\\s*");
                n.setText(temps[0]); s.setText(temps[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            LinearLayout layout = findViewById(R.id.box);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            //File file = new File(tickets);
            //FileReader fr = null;
            //fr = new FileReader(file);
            //BufferedReader reader = new BufferedReader(fr);
            //String line = reader.readLine();
            //int i=0;
            db = new DbHelper(getApplicationContext()).getReadableDatabase();
            Cursor cursor = DbTalon.getAll(db);
            int i=0; String specialist = "";
            while (cursor.moveToNext()) {
                i++;
                String spec,time;
                spec= cursor.getString(cursor.getColumnIndexOrThrow("iddoc"));
                Cursor cursor1= db.rawQuery("select speciality from " + DbHelper.DOCTOR_TABLE +
                        " Where iddoc = ?" , new String[]{spec});
                if (cursor1.moveToFirst()) {
                    specialist = cursor1.getString(0);
                }
                time= cursor.getString(cursor.getColumnIndexOrThrow("time"));
                Button btnNew = new Button(MainPage.this);
                btnNew.setText("Талон к " + specialist +"у на "+time+".00");
                btnNew.setLayoutParams(param);
                btnNew.setId(i);
                layout.addView(btnNew, param);
                btnNew.setOnClickListener(getTalonClick(btnNew));
            }
            but.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainPage.this, MainActivity.class);
                    EditText n= findViewById(R.id.username);
                    EditText s= findViewById(R.id.usersurname);
                    intent.putExtra("surname",s.getText().toString());
                    intent.putExtra("name", n.getText().toString());
                    startActivity(intent);
                }
            });
            Button PickImage = (Button) findViewById(R.id.button);
            //это загрузка картинки я хз почему кнопка называется просто баттон
            PickImage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, Pick_image);
                }
            });
        }
    }
    View.OnClickListener getTalonClick(final Button button)  {
        return new View.OnClickListener() {
            public void onClick(View v) {
               Intent intent1 =new Intent(MainPage.this, Talon.class);
               intent1.putExtra("linenumber", button.getId());
               startActivity(intent1);
            }
        };
    }
/*
    public char[] read( String path) throws IOException {

        try {
            File json = new File(path);
            FileReader filereader = new FileReader(json);
            char[] str = new char[0];
            filereader.read(str);
            filereader.close();
            return str;
        }
        catch(IOException ex)
        {
            Log.d("",""+ex.getMessage());
        }
        return new char[0];
    }
*/
    public void viewimage(View view) throws IOException {
        Intent intentquick = new Intent(Intent.ACTION_VIEW);
        String img = new String(Files.readAllBytes(Paths.get(profile)));
        intentquick.setDataAndType(Uri.parse(img),"image/*");

        intentquick.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentquick);
    }
    public void createSave(String str,String path) throws IOException {

        try {
            File json = new File(path);
            FileWriter filewriter = new FileWriter(json);
            filewriter.write(str);
            filewriter.close();
        }
        catch(IOException ex)
        {
            Log.d("",""+ex.getMessage());
        }
    }

    private Object getImageUri(Context applicationContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(MainPage.this.getContentResolver(), inImage, UUID.randomUUID().toString() + ".png", "drawing");
        return Uri.parse(path);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case Pick_image:
                if (resultCode == RESULT_OK) {
                    try {

                        //Получает URI изображения, преобразуем его в Bitmap
                        //объект и отображаем в элементе ImageView нашего интерфейса:
                        Log.d("ф", String.valueOf(imageReturnedIntent.getData()));
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imageView.setImageBitmap(selectedImage);
                        createSave(imageReturnedIntent.getData().toString(), profile);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
    public void post(View view)
    {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.setType("message/rfc822");
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"faewrr@gmail.com"});


        startActivity(Intent.createChooser(email, "Выберите email клиент :"));
    }
    public void vk(View view)
    {
        Uri uri = Uri.parse("https://vk.com/semashko2");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    public void call(View view)
    {
        Uri number = Uri.parse("tel:+375336349330");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }
    public void cSave(String str,String path) {
        try {
            PrintWriter prWr = new PrintWriter(new BufferedWriter(new FileWriter( path, true)));
            prWr.println(str);
            prWr.close();
        }
        catch(IOException ex)
        {
            Log.d("",""+ex.getMessage());
        }
    }
    @Override
    public void afterTextChanged(Editable r) {
        File json = new File(name);
        json.delete();
        try {
            json.createNewFile();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        cSave(String.format( n.getText()+"-"+ s.getText()+";" ),name);

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence sp, int start, int before, int count) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}