package eco.phakphum.jokeebookshop;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    // Explicit
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;

    // สร้าง URL JSON
    //private static final String urlJSON = "http://swiftcodingthai.com/9july/get_user_joke.php";
    private String urlJSON; // วิธีกำหนดตัวแปร แบบค่าคงที่



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Blind Widget
        userEditText = (EditText) findViewById(R.id.editText5);
        passwordEditText = (EditText) findViewById(R.id.editText6);

        // เรียก ใช้ Constant จากวิธีแบบค่าคงที่ (MyConstant)
        MyConstant myConstant = new MyConstant();
        urlJSON = myConstant.getUrlJSONuser();

    }   // Main Method

    // ทำการสร้าง Trede Class ซ้อน Class
    // สรา้ง Sync ดึงข้อมูลจาก Table mที่ต้องการ
    // extends คือการ สืบทอดคลาส
    // extends AsyncTask<Void ,Void,String> ก่อนโหลด , ขณะโหลด , ชนิดข้อมูลที่ได้จากการโหลด (สำหรับดึงฐานข้อมูลจาก DB)
    // หน้าที่ ของทมันคือการ ดึง Data มาจาก TABLE ที่ต้องการ
    // Implement Method (Art+Enter) คือเรียกขอ Method ที่เกี่ยวข้อง
    private class SyncUserTABLE extends AsyncTask<Void, Void, String> {

        // Explicit
        // Context เป็นการ ทำการพ่น ข้อมูลระหว่าง Class กับ Class
        private Context context;    // ตัวแปรสำหรับการเชื่อมต่อ
        private String myURL,myUserString, myPasswordString ,
                truePassword ,loginNameString, loginSurnameString;
        private boolean statusABoolean = true;

        // Alt + Insert เลือก contructor Setter
        public SyncUserTABLE(Context context,
                             String myURL,
                             String myUserString,
                             String myPasswordString) {
            this.context = context;
            this.myURL = myURL;
            this.myUserString = myUserString;
            this.myPasswordString = myPasswordString;
        }




        @Override
        protected String doInBackground(Void... params) {

            try {
                // Load JSON จาก URL ที่กำหนด (get_user_joke.php) จาก Contructor Setter ที่ สร้างไว้
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(myURL).build();
                Response response = okHttpClient.newCall(request).execute();
                // เลือกในส่วน Body ไม่เอา Header จาก ตัวแปร request
                return response.body().string();

            } catch (Exception e) { // Exception e คือการ Error ที่ยออมรับได้ เช่น ต่อ Net ไม่ได้ ให้แสดงบน Logcat
                Log.d("ShopV1", "e doInBack ==> " + e.toString());
                return null;

            }

            //return null;
        }   // DoInBack (เกิดจากการ Implement Method Class AsyncTask)

        @Override   // Override คือการ ดึง Method มาอยู่ใน Class ต้นฉบับ (Alt+Insert) ภายใต้ Class นอกทุก Method
        // onPostExecute จะทำงาน ภายหลีง doInBack ทำงาน เสร็จ
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ShopV1", "JSON=> " + s); // แสดงข้อมูล JSON ใน Logcat เพื่อตรวจสอบก่อน

            try {
                // JSONArray = Array ข้อความที่รับมาในรูปแบบ JSON
                // [{"id":"1","Name":"\u0e40\u0e17\u0e2a","Surname":"joke","User":"joke","Password":"joke"}]
                // JSON Object:JSON Array
                JSONArray jsonArray = new JSONArray(s);
                for (int i=0;i<jsonArray.length();i+=1) {
                    // Loop ค่า JSON
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    if (myUserString.equals(jsonObject.getString("User"))) { // User คือ ชื่อ Coloumn ทำการ Check User
                        statusABoolean = false; // ค้นหา User ใน Table ถ้าเจอให้เป็น False (default เป็น True)
                        truePassword = jsonObject.getString("Password");    // ดึงข้อมูล Password ออกมาจาก Table
                        loginNameString = jsonObject.getString("Name");
                        loginSurnameString = jsonObject.getString("Surname");

                    }
                }   // for

                if (statusABoolean) {
                    MyAlert myAlert = new MyAlert();
                    myAlert.myDialog(context, "ไม่มี User นี้",
                            "ไม่มี "+myUserString+" ในฐานข้อมูลของเรา");
                } else if (myPasswordString.equals(truePassword)) {

                    Toast.makeText(context, "Welcome "+ loginNameString +" "+loginSurnameString,
                            Toast.LENGTH_SHORT).show();
                    // ทำการ Intent ข้อมูลเพื่อส่งได้ยังหน้า ServiceActivity
                    Intent intent = new Intent(MainActivity.this, ServiceActivity.class);
                    intent.putExtra("Name", loginNameString);   // นำข้อมูลที่ต้องการส่งมาใส่ในตัวแปร Name เพื่อเตรียม intent ไปยีง Activity ServiceActivity
                    intent.putExtra("Surname", loginSurnameString);
                    startActivity(intent);
                    finish();   // ตัด Session Time ของ Activity ต้องทำการ Login ใหม่ทุกครั้ง

                } else {
                    MyAlert myAlert = new MyAlert();
                    myAlert.myDialog(context, "Password False",
                            "Please Try Again Password False");
                }

            } catch (Exception e) {
                Log.d("ShopV1", "e onPost ==> " + e.toString());
            }
        }
    }   // SynUser


    public void clickSignIn(View view) {

        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        // Check Space
        if (userString.equals("") || passwordString.equals("")) {
            //is True Have Space
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this,"Have Space","Please Fill All Every Blank");

        } else {
            //is False No Space

            // ทำการ Sync ตารางที่ต้องการดึงข้อมูล
            SyncUserTABLE syncUserTABLE = new SyncUserTABLE(this,urlJSON,userString,passwordString); // Ctrl+p
            syncUserTABLE.execute();
        }


    }   // ClickSignIn Method

    public void clickSignUpMain(View view) {
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
    }


}   // Main Class
