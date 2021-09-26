package design.principles.basic;

import java.util.ArrayList;

public class SLAP {
    // Single Level of Abstraction Principle
    // 메소드는 하나의 의도만을 가져야 한다.

    public static int[] data = new int[]{1,2,3,4,5,6,7,8,9};
    public static ArrayList<Integer> dataListWithoutSLAP = new ArrayList<>();
    public static ArrayList<Integer> dataListWithSLAP = new ArrayList<>();

    // 간단한 ETL 예제
    // Extract, Transform, Load 부분을 나누어서 작성
        // 각 기능에 대한 의도 파악 용이
        // 기능별로 나누어서 유지 보수 용이 -> One reason to change
    public static void main(String[] args) {
        processDataWithoutSLAP(2,"+",3);
        processDataWithSLAP(2,"+",3);
        System.out.println(dataListWithoutSLAP.toString());
        System.out.println(dataListWithSLAP.toString());

    }

    public static void processDataWithoutSLAP(int extract, String symbols, int operator){
        ArrayList<Integer> temp = new ArrayList<>();
        // Extract Data
        for(int i = 0 ; i < data.length ; i++) {
            if(i % extract == 0) temp.add(data[i]);
        }

        // Transform Data
        switch (symbols){
            case "+":
                for(int i = 0 ; i < temp.size() ; i++){
                    temp.set(i,temp.get(i) + operator);
                }
                break;
            case "-":
                for(int i = 0 ; i < temp.size() ; i++){
                    temp.set(i,temp.get(i) - operator);
                }
                break;
            case "*":
                for(int i = 0 ; i < temp.size() ; i++){
                    temp.set(i,temp.get(i) * operator);
                }
                break;
            case "/":
                for(int i = 0 ; i < temp.size() ; i++){
                    temp.set(i,temp.get(i) / operator);
                }
                break;
            }

        // Load Data
        dataListWithoutSLAP.addAll(temp);
    }

    public static void processDataWithSLAP(int extract, String symbols, int operator) {
        loadData(transformData(extractData(extract),symbols,operator));
    }

    public static ArrayList<Integer> extractData(int extract){
        ArrayList<Integer> temp = new ArrayList<>();
        // Extract Data
        for(int i = 0 ; i < data.length ; i++) {
            if(i % extract == 0) temp.add(data[i]);
        }
        return temp;
    }

    public static ArrayList<Integer> transformData(ArrayList<Integer> temp, String symbols, int operator){
        switch (symbols){
            case "+":
                calculateAdd(temp,operator);
                break;
            case "-":
                calculateSub(temp,operator);
                break;
            case "*":
                calculateMul(temp,operator);
                break;
            case "/":
                calculateDiv(temp,operator);
                break;
        }
        return temp;
    }

    public static void loadData(ArrayList<Integer> transformed){
        dataListWithSLAP.addAll(transformed);
    }

    public static void calculateAdd(ArrayList<Integer>temp, int operator){
        for(int i = 0 ; i < temp.size() ; i++){
            temp.set(i,temp.get(i) + operator);
        }
    }

    public static void calculateSub(ArrayList<Integer>temp, int operator){
        for(int i = 0 ; i < temp.size() ; i++){
            temp.set(i,temp.get(i) - operator);
        }
    }

    public static void calculateMul(ArrayList<Integer>temp, int operator){
        for(int i = 0 ; i < temp.size() ; i++){
            temp.set(i,temp.get(i) * operator);
        }
    }

    public static void calculateDiv(ArrayList<Integer>temp, int operator){
        for(int i = 0 ; i < temp.size() ; i++){
            temp.set(i,temp.get(i) / operator);
        }
    }

}
