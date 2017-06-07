package com.example.admin.gangwon.MapViewEvent;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.example.admin.gangwon.CallNumber;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

/**
 * Created by admin on 2016-07-07.
 */
public class POIItemEventListener implements MapView.POIItemEventListener {

    private static final String TAG = POIItemEventListener.class.getSimpleName();

    private Context fcontext;
    private int intentNum;
    private CallNumber callnum;


    public POIItemEventListener(Context context, int num){
        this.fcontext = context;
        this.intentNum = num;

    }


    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

        Log.v("intentNum :",Integer.toString(intentNum));


        if (intentNum == 1) {

            String tel = "tel:";
            String temp = null;
            String hName = mapPOIItem.getItemName();

            callnum = new CallNumber();
            temp = callnum.getNum(hName);
            tel = tel + temp;

            Log.d("tel : ",tel);

            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(tel));
            fcontext.startActivity(intent);

        }
        else if(intentNum ==2){

        }
        else if (intentNum ==3){

        }


    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }
}
