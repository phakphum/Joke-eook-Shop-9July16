package eco.phakphum.jokeebookshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by kawphod on 10/7/2559.
 */
public class MyAdapter extends BaseAdapter{

    // Explicit
    private Context context;
    private String[] bookStrings,priceStrings, iconStrings;

    // สร้าง Contructor ทำหน้าที่เป็น Setter
    // Alt + Insert เลือก Contructor
    public MyAdapter(Context context,
                     String[] bookStrings,
                     String[] priceStrings,
                     String[] iconStrings) {
        this.context = context;
        this.bookStrings = bookStrings;
        this.priceStrings = priceStrings;
        this.iconStrings = iconStrings;
    }

    // เกิดจากการ Implement (Alt + Enter) ที่ extend BaseAdapter ที่ Main Class (MyAdapter)
    @Override
    public int getCount() {

        //return 0;
        return bookStrings.length;  //นับเอง
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // สร้าง Laypout เสมือน (Service เสมือน สำหรั่วยสร้าง ListView
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.my_listview, parent, false);
        // Bind Widget
        // นำข้อมูลมาใส่ใน ListView ชื่อตัวแปร view
        TextView bookTextView = (TextView) view.findViewById(R.id.textView8);
        TextView priceTextView = (TextView) view.findViewById(R.id.textView9);
        ImageView iconImageView = (ImageView) view.findViewById(R.id.imageView2);

        // นำข้อมูลแต่ละ position มาใส่ลงใน ListView แต่ละแถว
        bookTextView.setText(bookStrings[position]);
        priceTextView.setText(priceStrings[position]);
        // ใช้งาน Libary ใหม่ หน้าที่ทำการนำ url ของรูบจาก JSON มาแสดงได้เลย ใน 1 บรรทัด
        Picasso.with(context).load(iconStrings[position]).resize(150, 180).into(iconImageView);



        return view;
    }
}   // Main Class
