package com.shamrock.reework.receivers;

public class Greatersmall {
    int a=20;
     int b=20;
     public void Gs(){
         if(a==b){
             System.out.println("the numbers are equal");

         }else{
             System.out.println("b is greater");
         }

     }
     public  static  void main(String a[]){
         Greatersmall greatersmall_obj=new Greatersmall();
         greatersmall_obj.Gs();
     }
}
