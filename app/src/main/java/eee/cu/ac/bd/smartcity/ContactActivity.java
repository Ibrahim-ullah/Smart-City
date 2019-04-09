package eee.cu.ac.bd.smartcity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {


    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private ArrayList<contacts> contactsArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);


        contactsArrayList = new ArrayList<>();
        contactsArrayList.add(new contacts("postogolar fire station,Dhaka","01730002216"));
        contactsArrayList.add(new contacts("Lalbagh fire station,Dhaka", "01730002218"));
        contactsArrayList.add(new contacts("Polashi Bank fire station,Dhaka","01730002219"));
        contactsArrayList.add(new contacts("Khilgaon fire station,Dhaka", "01730002225"));
        contactsArrayList.add(new contacts("Tejgaon fire station,Dhaka", "01730002226"));
        contactsArrayList.add(new contacts("Mohammadpur fire station,Dhaka", "01730002227"));
        contactsArrayList.add(new contacts("Mirpur fire station,Dhaka","01730002229"));
        contactsArrayList.add(new contacts("Kurmitola Fire Station","01730002232"));
        contactsArrayList.add(new contacts("DEPZ fire station,Dhaka","01730002231"));
        contactsArrayList.add(new contacts("Baridhara Fire Station,Dhaka", "01730000245"));
        contactsArrayList.add(new contacts("Demra Fire staion,Dhaka","01730002250"));
        contactsArrayList.add(new contacts("Savar fire station,Dhaka","01730002250"));
        contactsArrayList.add(new contacts("Dhamrai fire station,Dhaka","02776666"));
        contactsArrayList.add(new contacts("Keranigonj Fire Staion,Dhaka","01730002247"));
        contactsArrayList.add(new contacts("Hajaribug fire station,Dhaka","0258616222"));
        contactsArrayList.add(new contacts("EPZ fire station,Chittagong",  "01730002425"));
        contactsArrayList.add(new contacts("Bayezid Fire station,Chittagong",  "01713002917"));
        contactsArrayList.add(new contacts("Chittagong Diabetic General Hospital",  "+880316594357"));
        contactsArrayList.add(new contacts("Chittagong medical college",  "+88031619400"));
        contactsArrayList.add(new contacts("Delta health care Chittagong limited","+880312550005"));
        contactsArrayList.add(new contacts("medical center, Chittagong", "+880312501261"));
        contactsArrayList.add(new contacts("Chittagong Maa-O-Shishu Hospital Medical College", "+880312520063"));
        contactsArrayList.add(new contacts("RAB-7 - Chittagong, Feni, Khagrachari, Rangamati, Bandarban & Cox's Bazar District","01777710799"));

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        adapter = new ContactAdapter((ArrayList<contacts>) contactsArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu,menu);

        MenuItem menuItem =  menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        String userInput = newText.toLowerCase();
        ArrayList<contacts> newList = new ArrayList<>();

        for(contacts name: contactsArrayList)
        {
            if (name.getName().toLowerCase().contains(userInput))
            {
                newList.add(name);
            }
        }

        adapter.updateList(newList);


        return true;
    }
}

