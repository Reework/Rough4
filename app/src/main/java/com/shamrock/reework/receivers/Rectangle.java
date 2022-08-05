package com.shamrock.reework.receivers;

public class Rectangle {
    int l = 20;
    int b = 30;
    double c = 3.14;
    int r = 10;
    String Str = "sonam";
    String Str1 = "sonam";
    String str2 = "swiha";

    public void rectarea() {
        System.out.println("area of rectangle" + (l * b));
    }

    public void circlearea() {
        System.out.println("area of circle" + (2 * c * r));
    }

    public void snm() {
        System.out.println(Str);
    }

    public void sc() {
        if (Str.equalsIgnoreCase(Str1)) {
            System.out.println("strings are equal");
        } else{

            System.out.println("strings are not equal");
        }
    }


     public static  void main( String a[]){
         Rectangle Rectangle_obj=new Rectangle();
         Rectangle_obj.rectarea();
         Rectangle_obj.circlearea();
         Rectangle_obj.snm();
         Rectangle_obj.sc();
     }

}
