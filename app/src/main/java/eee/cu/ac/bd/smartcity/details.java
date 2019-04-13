package eee.cu.ac.bd.smartcity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class details extends AppCompatActivity {

    public static String Name;
    public static String Location;
    public static double Latitude;
    public static double Longitude;
    public static String Description;
    public static String Title;
    public static String La;
    public static String Lo;
    public static String image_url;
    Context context;
    private GoogleMap mMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = details.this;
        setContentView(R.layout.activity_details);
        Bundle extra = getIntent().getExtras();
        final String Id = extra.getString("id");
        final int fint = Integer.parseInt(Id);

        final WebView wb=(WebView)findViewById(R.id.wb);
        final TextView name = (TextView) findViewById(R.id.dname);
        final TextView location = (TextView) findViewById(R.id.Dlo);
        final TextView description = (TextView) findViewById(R.id.ddescription);
        final TextView title = (TextView) findViewById(R.id.dtitle);
        final TextView latlong = (TextView) findViewById(R.id.dlatlong);
        final ImageView imageView = findViewById(R.id.imageView);


        String url = "https://roomy.pythonanywhere.com/rumysecuritytest/default/feeds.json?single="+fint;
        final ProgressDialog loading = ProgressDialog.show(details.this, "Loading Feeds", "Please wait", false, false);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, " ",
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray feedjson = response.getJSONArray("feeds");



                                        JSONObject feed = feedjson.getJSONObject(0);


                                            Name = "Problem posted by" +"       " + feed.getString("name");
                                            Location = "Location is" +"     " + feed.getString("location");
                                            Description = "Description:" + feed.getString("description");
                                            Latitude = + feed.getDouble("latitude");
                                            Longitude = feed.getDouble("longitude");
                                            La = feed.getString("latitude");
                                            Lo = feed.getString("longitude");
                                            Title = feed.getString("issue");
                                            image_url = feed.getString("img_url");
                                            image_url = "https://roomy.pythonanywhere.com" + image_url;
                                            Log.i("Image URL", image_url);

                                    Picasso.with(context).load(image_url).into(imageView);

                                            name.setText(Name);
                                            location.setText(Location);
                                            description.setText(Description);
                                            title.setText(Title);
                                            latlong.setText(La + ", " + Lo);

                                            wb.loadUrl("http://maps.googleapis.com/maps/api/staticmap?zoom=15&size=400x300&maptype=roadmap&markers=color:red|"+La+","+Lo);
                                            loading.dismiss();
                                            Log.v("Got Values", Name);






                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.v("Exception", Id);
                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        loading.dismiss();
                        Toast.makeText(details.this, "Json Loading error", Toast.LENGTH_LONG).show();

                    }
                });

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(details.this).addTorequestrue(jsObjRequest);


    }
    public void mapactivity(View view){
        Intent i=new Intent(this,MapsActivity.class);
        startActivity(i);
    }
}
