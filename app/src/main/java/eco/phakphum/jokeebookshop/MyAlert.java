package eco.phakphum.jokeebookshop;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

/**
 * Created by kawphod on 9/7/2559.
 */
public class MyAlert {

    // New Method
    // Method ต้องทำ Context เพื่อเป็นการติดต่อระหว่าง Activity กับ Activity หรือ ระหว่าง Class กับ Class
    // Context Ctrl+Spac -> Context context
    // เพิ่ม Arg รับค่าจาก Class ที่ส่งมา

    /*
        Class object = new Class(); //สร้าง Instant -> Ctrl + Space

     */
    public void myDialog(Context context,
                         String strTitle,
                         String strMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context); // การสร้าง Constance or Object
        builder.setCancelable(false);   // ทำให้ปุ่ม Back เป็นอัมพาธ
        builder.setIcon(R.drawable.bird48);
        builder.setTitle(strTitle);
        builder.setMessage(strMessage);
        // ปุ่มใน Dialog มีได้สูงสุด 3 ปุ่ม (positive ,Negative ,Neutral)
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();  // ทำให้ Dialog หายไป
            }
        });
        builder.show();

    }

}   // Main Class
