package com.company;
import java.lang.IllegalArgumentException;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try{

            Date first = new Date(1,12,2020);
            Date second = new Date(2,11,1996);
            Date third = new Date(10,4, 2015);
            Date fourth = new Date(11,12,1986);

            List<Date> dates = new LinkedList<>();
            dates.add(first);
            dates.add(second);
            dates.add(third);
            dates.add(fourth);

            for (Date date : dates) {
                System.out.println(date.getDate(Date.FORMAT_CLASSIC));
            }

            dates.sort(Date::compareTo);

            System.out.println("\n");

            for (Date date : dates) {
                System.out.println(date.getDate(Date.FORMAT_CLASSIC));
            }

        }catch (IllegalArgumentException exception){
            System.out.println(exception.getMessage());
        }

    }
}





