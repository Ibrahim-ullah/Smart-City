package eee.cu.ac.bd.smartcity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private ArrayList<contacts> dataList;

    public ContactAdapter(ArrayList<contacts> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        holder.txtName.setText(dataList.get(position).getName());
        holder.txtPhone.setText(dataList.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtPhone;

        ContactViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txt_name);
            txtPhone = (TextView) itemView.findViewById(R.id.txt_phone);
        }
    }

    public void updateList (ArrayList<contacts> newList){

        dataList = new ArrayList<contacts>();
        dataList.addAll(newList);
        notifyDataSetChanged();

    }
}
