package vu.pham.luufilevoifirebase;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private String uriHinh="";
    private ArrayList<HinhAnh>hinhAnhArrayList;
    private HinhAnhAdapter adapter;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private DatabaseReference vuDatabase;
    private ImageView imgHinh;
    private EditText edtTenHinh;
    private Button btnSaveImage;
    private ListView lsvHinh;
    private static final int REQUEST_CODE_HINH=123;
    ActivityResultLauncher<Intent> mActivityResultLauncher=registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data=result.getData();
                    if(data==null){
                        return;
                    }
                    //lấy đường dẫn Uri hình ảnh trong điện thoại
                    Uri uri=data.getData();

                    //chuyển thành dạng bitmap
                    try {
                        Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        imgHinh.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final StorageReference storageRef = storage.getReferenceFromUrl("gs://luu-hinh-voi-firebase.appspot.com");
        vuDatabase= FirebaseDatabase.getInstance().getReference();

        imgHinh=(ImageView)findViewById(R.id.imageViewHinh);
        btnSaveImage=(Button)findViewById(R.id.buttonSaveImage);
        lsvHinh=(ListView)findViewById(R.id.listViewHinh);
        edtTenHinh=(EditText)findViewById(R.id.editTextTenHinh);

        hinhAnhArrayList=new ArrayList<>();
        adapter=new HinhAnhAdapter(MainActivity.this, R.layout.row_hinh_anh, hinhAnhArrayList);
        lsvHinh.setAdapter(adapter);
        loadData();

       imgHinh.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onClickRequestPermission();
           }
       });

       btnSaveImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Calendar calendar=Calendar.getInstance();
               StorageReference mountainsRef = storageRef.child("image"+calendar.getTimeInMillis()+".png");
               // Get the data from an ImageView as bytes
               imgHinh.setDrawingCacheEnabled(true);
               imgHinh.buildDrawingCache();
               Bitmap bitmap = ((BitmapDrawable) imgHinh.getDrawable()).getBitmap();
               ByteArrayOutputStream baos = new ByteArrayOutputStream();
               bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
               byte[] data = baos.toByteArray();

               UploadTask uploadTask = mountainsRef.putBytes(data);
               uploadTask.addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception exception) {
                       Toast.makeText(MainActivity.this, "Lỗi rồi !", Toast.LENGTH_SHORT).show();
                   }
               }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                   @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                       uploadTask.ge
                       Toast.makeText(MainActivity.this, "Thành công !", Toast.LENGTH_SHORT).show();
                       storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                           @Override
                           public void onSuccess(Uri uri) {
                               uriHinh=uri.toString();
                           }
    