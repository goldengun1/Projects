package test1;


import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int x = 5;
        System.out.println(x);

        List list = new ArrayList(10);

        System.out.println(list instanceof ArrayList);
    }
}
