package com.example.admin.gangwon;

import android.util.Log;

/**
 * Created by admin on 2016-06-30.
 */
public class CallNumber {

    private static final String TAG = CallNumber.class.getSimpleName();

    Number[] phone;
    public CallNumber(){

        phone = new Number[8];
        phone[0] = new Number();
        phone[0].pName = "강원도 속초의료원";
        phone[0].pNumber = "0336306000";

        phone[1] = new Number();
        phone[1].pName = "의료법인 강릉동인병원";
        phone[1].pNumber = "0336516161";

        phone[2] = new Number();
        phone[2].pName = "강원대학교병원";
        phone[2].pNumber = "0332582000";

        phone[3] = new Number();
        phone[3].pName = "원주기독병원";
        phone[3].pNumber = "0337410114";

        phone[4] = new Number();
        phone[4].pName = "강원도삼척의료원";
        phone[4].pNumber = "0335821141";

        phone[5] = new Number();
        phone[5].pName = "춘천성심병원";
        phone[5].pNumber = "0332405000";

        phone[6] = new Number();
        phone[6].pName = "강릉동인병원";
        phone[6].pNumber = "0336516161";

        phone[7] = new Number();
        phone[7].pName = "강릉아산병원";
        phone[7].pNumber = "0336104111";

    }



    class Number{

        String pName;
        String pNumber;
    }

    public String getNum(String input){
        String tel=  null;

        for(int i =0;i<phone.length;i++){
            if(input.contains(phone[i].pName)==true){
                Log.d("pName :",phone[i].pName);
                Log.d("pNum :",phone[i].pNumber);
                tel = phone[i].pNumber;
            }
        }


        return tel;
    }



    }
