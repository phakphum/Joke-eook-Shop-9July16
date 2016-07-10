package eco.phakphum.jokeebookshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    // Explicit
    private TextView bookTextView, priceTextView;
    private ImageView imageView;
    private String nameLoginString ,surnameLoginString,
    bookString , priceString, iconString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Bind Widget
        bookTextView = (TextView) findViewById(R.id.textView10);
        priceTextView = (TextView) findViewById(R.id.textView11);
        imageView = (ImageView) findViewById(R.id.imageView3);

        // Show View
        // เป้ฯการดึงค่ามาจาก หน้า Activity ที่ Intent มาให้
        nameLoginString = getIntent().getStringExtra("NameLogin");
        surnameLoginString = getIntent().getStringExtra("SurnameLogin");
        bookString = getIntent().getStringExtra("Book");
        priceString = getIntent().getStringExtra("Price");
        iconString = getIntent().getStringExtra("Icon");

        bookTextView.setText(bookString);
        priceTextView.setText(priceString + "THB.");
        Picasso.with(this).load(iconString).resize(250, 300).into(imageView);



    }   // Main Method

    public void clickBack(View view) {
        finish();
    }   //clickBack

    public void clickOrder(View view) {

    }   // clickOrder

}   // Main Class
