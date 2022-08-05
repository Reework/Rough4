package com.shamrock.reework.receivers;

public class Evenodd {
    int a=11;
    private void Evenodd(){
        if(a%2==0){
            System.out.println("number is even");
        }
        else{
            System.out.println("number is odd");
        }
    }
    public static void main(String a[]){

           Evenodd Evenodd_obj=new Evenodd();

           Evenodd_obj.Evenodd();

    }
}
