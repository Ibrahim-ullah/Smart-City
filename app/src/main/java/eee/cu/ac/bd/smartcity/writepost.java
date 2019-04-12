package eee.cu.ac.bd.smartcity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;


import in.mayanknagwanshi.imagepicker.imageCompression.ImageCompressionListener;
import in.mayanknagwanshi.imagepicker.imagePicker.ImagePicker;

import static android.content.ContentValues.TAG;


public class writepost extends AppCompatActivity {

    EditText name,  issue,decription;
    public static Bitmap bitmap;
    public  Bitmap dstBmp;
    public String test;

    String getName, getLocation, getIssue, getLatitude, getLongitude, getDescription;
    TextView locationName,mLatitude,mLongitude;
    Button buttonSubmit,buttonImage;
    public String bits;
    public ImagePicker imagePicker;
    public ImageView im;
    public Bitmap image_bit;
    public String encodedImage;




    dbBitmapUtility imdb= new dbBitmapUtility();

    Bitmap SelectedImage;
    int PLACE_PICKER_REQUEST = 1;
    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writepost);

        name = (EditText) findViewById(R.id.name);
        locationName=(TextView) findViewById(R.id.location);
        mLatitude=(TextView) findViewById(R.id.setlats);
        mLongitude=(TextView) findViewById(R.id.setlongs);
        issue = (EditText) findViewById(R.id.title);
        decription=(EditText) findViewById(R.id.problem);
        buttonSubmit = (Button) findViewById(R.id.submit);
        buttonImage= (Button) findViewById(R.id.image);

        im = (ImageView) findViewById(R.id.im);
        final AlertDialog.Builder builder=null;



        //hooking onclick listener of button
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getName = name.getText().toString();
                getLocation=locationName.getText().toString();
                getLatitude=mLatitude.getText().toString();
                getLongitude=mLongitude.getText().toString();
                getIssue = issue.getText().toString();
                getDescription=decription.getText().toString();







                RequestQueue queue = Volley.newRequestQueue(writepost.this);
                String URL = "https://roomy.pythonanywhere.com/rumysecuritytest/default/smartcity";

                final ProgressDialog loading = ProgressDialog.show(writepost.this,"Uploading...","Please wait...",false,false);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Do something with the response
                                Toast.makeText(writepost.this,"Success",Toast.LENGTH_LONG).show();
                                loading.dismiss();
                                Log.v(TAG,"Response: "+response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Handle error
                                loading.dismiss();

                                Toast.makeText(writepost.this,"An unexpected error occurred",Toast.LENGTH_LONG).show();
                                String body;
                                //get status code here
                                String statusCode = String.valueOf(error.networkResponse.statusCode);
                                //get response body and parse with appropriate encoding
                                if(error.networkResponse.data!=null) {
                                    try {
                                        body = new String(error.networkResponse.data,"UTF-8");
                                        Log.i("Error Body",body);
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                }


                            }
                        }){
                    //adding parameters to the request
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("name", getName);
                        params.put("location", getLocation);
                        params.put("issue", getIssue);
                        params.put("description",getDescription);
                        params.put("latitude", getLatitude);
                        params.put("longitude", getLongitude);
                        params.put("imgData",encodedImage);
                        return params;
                    }


                };

                MySingleton.getInstance(writepost.this).addTorequestrue(stringRequest);








            }
        });}




    public void uploader(View view){
        imagePicker = new ImagePicker();
        imagePicker.withActivity(this) //calling from activity
                .chooseFromGallery(true) //default is true
                .chooseFromCamera(true) //default is true
                .withCompression(true) //default is true
                .start();
    }



    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {

        try {

            switch (requestCode) {
                case (1): {
                    if (resultCode == RESULT_OK) {
                        Place place = PlacePicker.getPlace(data, this);
                        String locate = String.format("%s %s", place.getName(), place.getAddress());
                        LatLng ltlng = place.getLatLng();
                        double latit = ltlng.latitude;
                        String latitude = String.format("%s", latit);
                        double longit = ltlng.longitude;
                        String longitude = String.format("%s", longit);

                        TextView locationName = (TextView) findViewById(R.id.locationName);
                        TextView location = (TextView) findViewById(R.id.location);
                        locationName.setText("Location Name: ");
                        location.setText(locate);

                        TextView lats = (TextView) findViewById(R.id.lats);
                        TextView setLat = (TextView) findViewById(R.id.setlats);
                        lats.setText("Latitude:");
                        setLat.setText(latitude);

                        TextView longs = (TextView) findViewById(R.id.longs);
                        TextView setlongs = (TextView) findViewById(R.id.setlongs);
                        longs.setText("Logitude:");
                        setlongs.setText(longitude);

                        Button mapButton = (Button) findViewById(R.id.mapButton);
                        mapButton.setText("Edit Location");
                        Log.i(TAG, "Lat: " + latitude + " Lon: " + longitude);
                    }
                }

                break;
                case (ImagePicker.SELECT_IMAGE): {

                    if (resultCode == Activity.RESULT_OK) {
                        //Add compression listener if withCompression is set to true
                        imagePicker.addOnCompressListener(new ImageCompressionListener() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onCompressed(String filePath) {//filePath of the compressed image
                                //convert to bitmap easily


                                Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
                                int bitWidth = selectedImage.getWidth();
                                int outWidth = 200;
                                float rate = bitWidth / outWidth;
                                int bitHeight = selectedImage.getHeight() / (int) rate;
                                Bitmap resized = Bitmap.createScaledBitmap(selectedImage, outWidth, bitHeight, true);
                                image_bit = resized;
                                im.setImageBitmap(resized);
                                int width = resized.getWidth();
                                int height = resized.getHeight();
                                int sizeBytes = getSizeFromBitmap(resized);
                                byte[] img_ready = processImage.convert(resized);
                                String img_to_string = Base64.encodeToString(img_ready, Base64.DEFAULT);
                                String out = "The image height is " + Integer.toString(height) + " and width is " + Integer.toString(width) +
                                        " and the size " + Integer.toString(sizeBytes);
                                Log.v("Image size", out);


                            }
                        });
                    }
                    //call the method 'getImageFilePath(Intent data)' even if compression is set to false
                    //String filePath = imagePicker.getImageFilePath(data);
                    Uri selectedImageUri = data.getData();
                    // Get the path from the Uri
                    final String filePath = getPathFromURI(selectedImageUri);
                    if (filePath != null) {//filePath will return null if compression is set to true
                        Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
                        int bitWidth = selectedImage.getWidth();
                        int outWidth = 200;
                        float rate = bitWidth / outWidth;
                        int bitHeight = selectedImage.getHeight() / (int) rate;
                        Bitmap resized = Bitmap.createScaledBitmap(selectedImage, outWidth, bitHeight, true);
                        image_bit = resized;
                        im.setImageBitmap(resized);
                        bits = getStringImage(resized);
                        Log.e("encodedimage",bits);

                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Somethig went embarassing",Toast.LENGTH_LONG).show();
        }
    }


    public void mappicker(View view){
        try {
            startActivityForResult(builder.build(this), 1);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }




    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
       Log.i("encodedImage",encodedImage);
        return encodedImage;
    }



    public static int getSizeFromBitmap(Bitmap bitmap) {
        int pixels = bitmap.getHeight() * bitmap.getWidth();
        int bytesPerPixel = 0;
        switch (bitmap.getConfig()) {
            case ARGB_8888:
                bytesPerPixel = 4;
                break;
            case RGB_565:
                bytesPerPixel = 2;
                break;
            case ARGB_4444:
                bytesPerPixel = 2;
                break;
            case ALPHA_8:
                bytesPerPixel = 1;
                break;
        }
        return pixels * bytesPerPixel;
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
}





