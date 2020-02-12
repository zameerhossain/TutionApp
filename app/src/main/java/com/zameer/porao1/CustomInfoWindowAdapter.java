package com.zameer.porao1;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Activity context;

    public CustomInfoWindowAdapter(Activity context){
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(final Marker marker) {
        View view = context.getLayoutInflater().inflate(R.layout.custominfowindow, null);

        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView tvSubTitle = (TextView) view.findViewById(R.id.tv_subtitle);
        TextView tvContact = (TextView) view.findViewById(R.id.tv_contact);
        //Button btn = (Button) view.findViewById(R.id.btn);

        TuitionRequest temp = MainActivity.allTuitionList.get(MainActivity.allMarker.get(marker));
        tvTitle.setText(temp.getCourseTitle());
        tvSubTitle.setText(temp.getProblemDescription());
        tvContact.setText(temp.getContact());

        /*btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = MainActivity.allMarker.remove(marker);
                int counterid = MainActivity.allTuitionList.get(index).getCounterid();
                MainActivity.allTuitionList.remove(index);

                DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                db.child("markers").child(Integer.toString(counterid)).removeValue();
            }
        });*/

        return view;
    }
}