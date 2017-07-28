package com.example.sxs10540.uiadapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sxs10540.uibean.Person;
import com.example.sxs10540.uiwigettest.R;

import java.util.List;

/**
 * Created by sxs10540 on 2017/7/26.
 */

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    private List<Person> personList;


    public PersonAdapter(List<Person> personList) {
        this.personList = personList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.person_item,parent,false
        );
        //设置宽度
        view.getLayoutParams().height = 100;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Person person = personList.get(position);
        holder.personText.setText("name:"+person.getName()+"\n" +
                "phone:"+person.getPhone());
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView personText;

        public ViewHolder(View itemView) {

            super(itemView);
            personText = (TextView) itemView.findViewById(R.id.contact_view);
        }
    }
}
