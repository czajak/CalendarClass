package com.company;
import java.lang.IllegalArgumentException;


public class Main {
    public static void main(String[] args) {
        try{

            Date today = new Date(1,12,2020);
            System.out.println(today.getDate(Date.FORMAT_SHORT)+ "\n");
            System.out.println(today.getDate(Date.FORMAT_LONG)+ "\n");
            System.out.println(today.getDate(Date.FORMAT_ROMAN)+ "\n");
            System.out.println(today.getDate(Date.FORMAT_CLASSIC)+ "\n");
            System.out.println(today.getDate(1)+ "\n");

        }catch (IllegalArgumentException exception){
            System.out.println(exception.getMessage());
        }

    }
}
class Date {
    public static final int FORMAT_LONG = 911261712;
    public static final int FORMAT_CLASSIC = -26112566;
    public static final int FORMAT_ROMAN = 666217956;
    public static final int FORMAT_SHORT = -126899233;
    Months months = new Months();
    private int day;
    private Month month;
    private int year;
    private boolean leap_year;
    private int weekday;
    public Date(int day,int month,int year) throws IllegalArgumentException{
        if(month > 12 || month < 1)
        {
            throw new IllegalArgumentException("Month must be within range of 1 to 12");
        }
        this.month = months.getMonth(month);
        this.year = year;
        leap_year = this.getLeapYear();
        if((day > this.month.getHowManyDays() && !(day == 29 && leap_year)) || day < 1)
        {
            throw new IllegalArgumentException("Day is incorrect");
        }
        this.day = day;
        weekday = calcWeekday(this);
    }
    public Date(int days,int year){
        int days_passed=0;
        int i=1;
        while (days_passed<days)
        {
            days_passed += months.getMonth(i).getHowManyDays();
            i++;
        }
        this.month = months.getMonth(i-1);
        days = month.getHowManyDays() + (days - days_passed);
        this.day = days;
        this.year = year;
        leap_year = this.getLeapYear();
        weekday = calcWeekday(this);
    }
    public Date()
    {
        this.day = 1;
        this.month = months.getMonth(1);
        this.year = 1970;
        leap_year = this.getLeapYear();
        weekday = calcWeekday(this);
    }
    public void nextWeek()
    {
        int howManyDays = month.getHowManyDays();
        if(month.getNumber() == 2 && leap_year)
        {
            howManyDays++;
        }
        day += 7;
        if(day > howManyDays)
        {
            day = day - howManyDays;
            if(month.getNumber()+2 > 12)
            {
                year++;
                month = months.getMonth(1);
            }
            else
            {
                month = months.getMonth(month.getNumber()+1);
            }
        }
    }
    public void lastWeek(){
        int howManyDays = month.getHowManyDays();
        if(month.getNumber() == 2 && leap_year)
        {
            howManyDays++;
        }
        day -= 7;
        if(day < 0)
        {
            if(month.getNumber()-1 < 1)
            {
                year--;
                month = months.getMonth(12);
            }
            else
            {
                month = months.getMonth(month.getNumber()-1);
            }
            day = day + howManyDays;
        }
    }
    private int calcWeekday(Date date){
        int dayspassed=0;
        int yy = (date.year - 1) % 100;
        int c = (date.year - 1) - yy;
        int g = yy + (yy/4);
        int weekday = (((((c/100) % 4) * 5) + g) % 7);
        for(int i=1;i<date.month.getNumber();i++)
        {
            dayspassed += date.months.getMonth(i).getHowManyDays();
        }
        if(date.leap_year && dayspassed >= 60)
        {
            dayspassed++;
        }
        dayspassed += date.day;
        weekday += dayspassed - 1;
        weekday %= 7;
        return weekday;
    }
    public int calcWeekday2(){
        Date ref = new Date(1,1,2020);
        int diff = 0;
        if(compareTo(ref) == 1) {
            while(compareTo(ref) > 1){
                ref.lastWeek();
            }
            diff = (this.day - ref.day) % 7;
        }
        else if(compareTo(ref) == -1){
                while(compareTo(ref) < 1){
                    ref.nextWeek();
                }
                diff = (this.day - ref.day) % 7;
            }
        return (ref.weekday + diff) % 7;
    }
    public boolean getLeapYear()
    {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }
    public String getDate(int par)
    {
        String date="";
        switch(par)
        {
            case 911261712:
                date = this.getWeekday() + ", " + day + " " + month.getName() + " " + year;
                break;
            case -26112566:
                date = day + " " + month.getName() + " " + year;
                break;
            case 666217956:
                if(day<10) {
                    date = "0";
                }
                date = date + day + "." + month.getRoman() + "." + year;
                break;
            case -126899233:
                date = day + "-" + month.getName().substring(0,3) + "-" + year;
                break;
            default:
                throw new IllegalArgumentException("Date parameter must be a certain format");
        }
        return date;
    }
    public String getWeekday(){
        switch(this.weekday)
        {
            case 0:
                return "Poniedziałek";
            case 1:
                return "Wtorek";
            case 2:
                return "Środa";
            case 3:
                return "Czwartek";
            case 4:
                return "Piątek";
            case 5:
                return "Sobota";
            case 6:
                return "Niedziela";
            default:
                return "error";
        }
    }

    private int compareTo(Date date){
        if(this.year > date.year) {return -1;}
        else if(this.year < date.year) {return 1;}
        else if(this.month.getNumber() > date.month.getNumber()) {return -1;}
        else if(this.month.getNumber() < date.month.getNumber()) {return 1;}
        else if(this.day > date.day) {return -1;}
        else if(this.day < date.day) {return 1;}
        else {return 0;}
    }
}
class Months {
    private final Month[] months = {new Month(1,"I", "stycznia", 31),
            new Month(2,"II", "lutego", 28),
            new Month(3,"III", "marca", 31),
            new Month(4,"IV", "kwietnia", 30),
            new Month(5,"V", "maja", 31),
            new Month(6,"VI", "czerwca", 30),
            new Month(7,"VII", "lipca", 31),
            new Month(8,"VII", "sierpnia", 31),
            new Month(9,"IX", "września", 30),
            new Month(10,"X", "października", 31),
            new Month(11,"XI", "listopada", 30),
            new Month(12,"XII", "grudnia", 31)};

    public Month getMonth(int i) {
        return months[i - 1];
    }
}
class Month {
    private final int number;
    private String roman_number;
    private final String name;
    private int how_many_days;
    public Month(int number,String roman_number, String name, int how_many_days) {
        this.number = number;
        this.roman_number = roman_number;
        this.name = name;
        this.how_many_days = how_many_days;
    }

    public int getNumber() {return number;}
    public String getRoman() {return roman_number;}
    public int getHowManyDays(){return how_many_days;}
    public String getName(){return name;}
}
