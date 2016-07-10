package eco.phakphum.jokeebookshop;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.w3c.dom.Text;

public class ServiceActivity extends AppCompatActivity {

    // Ecplicit
    private TextView textView;
    private ListView listView;
    private String nameString , surnameString , urlJSON;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        // Settup Constant เรียกใช้แบบกำหนดค่าคงที่
        MyConstant myConstant = new MyConstant();
        urlJSON = myConstant.getUrlJSONproduct();

        // Blid (Initial) Widget
        textView = (TextView) findViewById(R.id.textView7);
        listView = (ListView) findViewById(R.id.listView);

        // Show
        nameString = getIntent().getStringExtra("Name");    // รับค่าที่มีการ Intent มาจาก Activity ก่อนหน้า ชื่อต้องเหมือนกัน กับตัวแปรที่ส่งมา "Name"
        surnameString = getIntent().getStringExtra("Surname");
        textView.setText("Welcome " + nameString + " " + surnameString);

        // Syun And Create ListView
        SynProduct synProduct = new SynProduct(this, urlJSON, listView);
        synProduct.execute();


    }   // Main Method

    private class SynProduct extends AsyncTask<Void, Void, String> {

        // Explicit
        private Context context;
        private String myURL;
        private ListView myListView;

        // สร้าง Constructor Alt+Insert เลือก Constructor เลือกตัวแปร
        public SynProduct(Context context, String myURL, ListView myListView) {
            this.context = context;
            this.myURL = myURL;
            this.myListView = myListView;
        }

        @Override
        protected String doInBackground(Void... params) {

            // สร้าง try catch ใน doInBack ทุกครั้ง
            try {
                // ทำ Instant
                OkHttpClient okHttpClient = new OkHttpClient();
                // การจ่าหน้าซอง ว่ารับมาจากไหน
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(myURL).build();
                // Execute
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();


            } catch (Exception e) {
                Log.d("ShopV2", "e doInBack ==> ");
                return null;
            }

        }   // doInBack

        // ได้จากการ Alt+Insert Override -> onPostEx...
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ShopV2", "JSON ==> " + s);

        }
    }   //SynProduct Class


}   // Main Class
