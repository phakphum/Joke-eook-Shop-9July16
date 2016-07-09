package eco.phakphum.jokeebookshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {

    // Explicit #1
    // Edit -> รอแนะนำ
    // ตั้งชื่อ name -> ctrl+space
    private EditText nameEditText, surnameEditText ,userEditText , passwordEditText;
    private String nameString,surnameString,userString, passwordString;
    private static final String urlPHP = "http://swiftcodingthai.com/9july/add_user_joke.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Bind or Initial Widget คือก่ารผูกตัวแปร กับ Widget บน Activity
        // Alt + Enter ใต้เส้นสีแดง เพื่อทำการ Cast to เพื่อผูกตัวแปรกับ Widget
        nameEditText = (EditText) findViewById(R.id.editText);
        surnameEditText = (EditText) findViewById(R.id.editText2);
        userEditText = (EditText) findViewById(R.id.editText3);
        passwordEditText = (EditText) findViewById(R.id.editText4);

    }   // Main Method

    // สร้าง New Method
    // ตั้งชื่อ ให้ตรง Action + ชื่อปุ่ม
    // Ctrl+Space -> เรียก view
    // Active Method -> ไปที่ XML เลือก OnClick เลือก Method ที่ต้องการผูก (จากสีเทา เปลี่ยนไปเป็นสีดำ)
    public void clickSignUpSign(View view) {

        // Get Value from Edit Text
        nameString = nameEditText.getText().toString().trim();
        surnameString = surnameEditText.getText().toString().trim();
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        // Check Space ตรวจสอบการกรอกข้อมูล ต้องไม่ว่าง
        // สร้าง New Class เพื่อใช้เป็น การ Alert แบบ Resued (Atl + Insert ที่ Package) -> MyAlert.java
        if (checkSpace()) { // สร้าง New Method โดยที่ยังไม่ได้สร้างรอไว้ ให้ตั้งชื่อ แล้วกด Alt + Enter
            //True Have Space
            MyAlert myAlert = new MyAlert(); //สร้าง Instant
            myAlert.myDialog(this,"มีช่องว่าง", "กรุณากรอกทุกช่องด้วย ค่ะ");

        } else {
            //False No Space

            updateNewUserToServer();

        }   // if


    }   // clickSign

    private void updateNewUserToServer() {

        // Okhttpclient มาจากการ Add Libary
        // จึงสามารถ ใช้งาน Class OkHttpClient ได้
        OkHttpClient okHttpClient = new OkHttpClient();

        // สร้าง Package เพื่อ POST to PHP
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("Name", nameString)
                .add("Surname", surnameString)
                .add("User", userString)
                .add("Password", passwordString)
                .build();
        // สร้างเป้าหมายในการส่งข้อมูล
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(urlPHP).post(requestBody).build();
        // ทำการโยนข้อมูล
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) { //โยนไม่ได้

            }

            @Override
            public void onResponse(Response response) throws IOException {  //โยนได้
                finish();
            }
        });

    }   // update

    private boolean checkSpace() {

        boolean status = false; //กำหนดค่า status เริ่มต้น

        //
        status = nameString.equals("") || surnameString.equals("") ||
                userString.equals("") || passwordString.equals("");

        return status;
    }


}   // Main Class
