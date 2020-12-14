package com.company;

/**
 * <code>class Months</code><br>
 * auxiliary class for Date, used to hold months array
 */
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

    /**
     * <code>public Month getMonth(int i)</code>
     * method used for getting certain Month objects to work with in Date class
     * @param i month index, must be within range 1 to 12
     * @return class Month object, from months array
     * @exception ArrayIndexOutOfBoundsException
     * If index is lower than 1 or greater than 12
     * */
    public Month getMonth(int i) throws ArrayIndexOutOfBoundsException {
        if(i<1 || i>12)
        {
            throw new ArrayIndexOutOfBoundsException("Array index is out of bounds");
        }
        return months[i - 1];
    }
}

/**
 * <code>class Month</code><br>
 * auxiliary class for Date, defines each Month in {@link Months} class
 */
class Month {
    private final int number;
    private String roman_number;
    private final String name;
    private int how_many_days;

    /**
     * <code>public Month(int number, String roman_number, String name, int how_many_days)</code>
     * Constructor for Month class
     * @param number number representation of month
     * @param roman_number roman number representation of month
     * @param name String name of month
     * @param how_many_days defines how many days are in each month in <b>non leap year</b>
     */
    public Month(int number,String roman_number, String name, int how_many_days) {
        this.number = number;
        this.roman_number = roman_number;
        this.name = name;
        this.how_many_days = how_many_days;
    }

    /**
     * <code>public int getNumber()</code>
     * Getter for number parameter.
     * @return {@link #number}
     */
    public int getNumber() {return number;}
    /**
     * <code>public String getRoman()</code>
     * Getter for roman_number parameter.
     * @return {@link #roman_number}
     */
    public String getRoman() {return roman_number;}
    /**
     * <code>public int getHowManyDays()</code>
     * Getter for how_many_days parameter.
     * @return {@link #how_many_days}
     */
    public int getHowManyDays(){return how_many_days;}
    /**
     * <code>public String getName()</code>
     * Getter for name parameter.
     * @return {@link #name}
     */
    public String getName(){return name;}
}