package cat.uvic.teknos.m08.pol.sanejove.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private TextView textView;
    private ArrayList<User> users;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email =findViewById(R.id.email);
        password =findViewById(R.id.password);
        textView=findViewById(R.id.textView);
        String jsonData;
        if(!internalFileExists("loginUsers.txt")){
            users=new ArrayList<>();
            saveArrayListToJSON(users);
            System.out.println("file doesnt exist");
        }else{
            jsonData=readFromFile("loginUsers.txt");
            Gson gson=new Gson();
            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            users = gson.fromJson(jsonData, userListType);
            System.out.println("print arraylist");
            System.out.println(users.size());
            for(int i=0;i<users.size();++i){
                System.out.println(users.get(i));
            }
            textView.setText(users.toString());
        }

    }
    public void writeToFile(String fileName,String content){
        File path=getApplicationContext().getFilesDir();
        try{
            FileOutputStream fileOutputStream=new FileOutputStream(new File(path,fileName));
            fileOutputStream.write(content.getBytes(StandardCharsets.UTF_8));
            fileOutputStream.close();
            Toast.makeText(getApplicationContext(),"Wrote to file: "+content,Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public String readFromFile(String fileName){
        File path=getApplicationContext().getFilesDir();
        File fileToReadFrom=new File(path,fileName);
        byte[] content=new byte[(int) fileToReadFrom.length()];
        try{
            FileInputStream fileInputStream=new FileInputStream(fileToReadFrom);
            fileInputStream.read(content);
            return new String(content);
        }catch (Exception e){
            e.printStackTrace();
            return e.toString();
        }
    }

    public void saveUser(View view) {
        User user=new User(email.getText().toString(),password.getText().toString());
        users.add(user);
        saveArrayListToJSON(users);
        textView.setText(users.toString());

    }
    private void saveArrayListToJSON(ArrayList<User> arrayList){
        String jsonData=new Gson().toJson(arrayList);
        writeToFile("loginUsers.txt",jsonData);
    }
    private boolean internalFileExists(String fileName) {
        File path=getApplicationContext().getFilesDir();
        File file=new File(path,fileName);
        return file.exists();
    }
}