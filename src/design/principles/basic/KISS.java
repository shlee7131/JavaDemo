package design.principles.basic;

public class KISS {
    // Simple Code 로 작성하기
    // Readable, Maintainable, Self-Descriptive
    public static void main(String[] args) {
        System.out.println(monthCheckComplicated(2));
        System.out.println(monthCheckSimply(2));
    }

    public static String monthCheckSimply(int month){
        String[] name = {
                "January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December"
        };
        return name[month-1];
    }

    public static String monthCheckComplicated(int month){
        switch(month) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return "null";
        }
    }

}
