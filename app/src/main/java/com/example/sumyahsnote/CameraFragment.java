package com.example.sumyahsnote;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CameraFragment extends Fragment {
    ImageView imageView;
    Button btn, btn2;
    private final int Request_Image_Capture = 1;
    private final int Request_Gallery_Capture = 2;

    public CameraFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        imageView = view.findViewById(R.id.image);
        btn = view.findViewById(R.id.btn);
        btn2 = view.findViewById(R.id.btn2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, Request_Image_Capture);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, Request_Gallery_Capture);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == Request_Image_Capture && data != null) {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(image);
            } else if (requestCode == Request_Gallery_Capture && data != null) {
                imageView.setImageURI(data.getData());
            }
        }
    }
}
