package esskeetit.com.memeville;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity;
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.IOException;

public class create_meme extends AppCompatActivity {
CircularImageView circularImageView, createdMeme;
Button select;
Uri imageUri;
final int PICK_IMAGE_REQUEST = 71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meme);

        select = findViewById(R.id.select_image);
        circularImageView = findViewById(R.id.meme_image);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browse();
            }
        });

    }

    public void browse(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();

            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                circularImageView.setImageBitmap(bitmap);
                editor();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case 200:
                    Uri outputUri = data.getData();
                    createdMeme = findViewById(R.id.created_meme);
                    createdMeme.setImageURI(outputUri);

            }

        }
    }
public void editor(){
        Intent intent = new Intent(this, DsPhotoEditorActivity.class);
        intent.setData(imageUri);
        intent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "memes");
        startActivityForResult(intent, 200);
}

}

