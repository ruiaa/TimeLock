package com.ruiaa.timelock.common.utils;

/**
 * Created by ruiaa on 2016/10/1.
 */

public class Pair<A,B> {
    private A a;
    private B b;

    public Pair(A a,B b){
        this.a=a;
        this.b=b;
    }

    public A getA(){
        return a;
    }

    public B getB(){
        return b;
    }
}
