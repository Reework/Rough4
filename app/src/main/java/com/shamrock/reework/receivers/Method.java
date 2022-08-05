package com.shamrock.reework.receivers;

public class Method {
    int a = 20;
    int b = 10;
    public void mtd_add() {
        System.out.println("Addition" + (a + b));
    }
    public void mtd_sub(){
        System.out.println("Substraction"+(a-b));
    }//substravtion
    public void mtd_mul(){
        System.out.println("Multiplication"+(a*b));
    }
    public void mtd_div(){
        System.out.println("Division"+(a/b));
    }


    //multipliocation

    public static void main(String a[]) {
        Method Method_obj = new Method();
        Method_obj.mtd_add();
        Method_obj.mtd_sub();
        Method_obj.mtd_mul();
        Method_obj.mtd_div();


    }

}
