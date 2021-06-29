package com;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static HashMap<String,Integer> intValues = new HashMap<>();
    public static HashMap<String,Float> floatValues = new HashMap<>();
    public static String outputPath = "output.txt";
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Window window = new Window();
                window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                window.setVisible(true);
                window.toFront();
                window.setBackground(Color.PINK);
            }
        });
    }

    public static void read_compute(File file) throws IOException, IllegalArgumentException, ArithmeticException{
        FileReader fileReader = new FileReader(file);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] splited = line.trim().split("[\\s]+");
//            System.out.println(Arrays.toString(splited));
            if (line.trim().equals("%%"))
                break;
            boolean isEmpty = line.isBlank();
            if (!isEmpty && intValues.containsKey(splited[1]) || floatValues.containsKey(splited[1]))
                throw new IllegalArgumentException("Duplicated variable");
            if (!isEmpty && splited[0].equals("int")) {
                if (splited.length == 4){
                    if (intValues.containsKey(splited[3]))
                        intValues.put(splited[1], intValues.get(splited[3]));
                    else if (!intValues.containsKey(splited[3]))
                        intValues.put(splited[1], Integer.parseInt(splited[3]));
                }else if (splited.length==2)
                    intValues.put(splited[1],0);
            } else if (!isEmpty && splited[0].equals("float")) {
                if (splited.length == 4){
                    if (floatValues.containsKey(splited[3]))
                        floatValues.put(splited[1], floatValues.get(splited[3]));
                    else if (!floatValues.containsKey(splited[3]))
                        floatValues.put(splited[1], Float.parseFloat(splited[3]));
                }else if (splited.length==2)
                    floatValues.put(splited[1],0.0f);
            }
        }
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] splited = line.trim().split("[\\s]+");
//            System.out.println(Arrays.toString(splited));
            compute(scanner, splited);
        }
    }

    public static void compute(Scanner scanner, String[] splited) throws IOException {
        if(splited[0].equals("for")){
            ArrayList<String> forLines = new ArrayList<>();
            int i=0;
            while (true){
                forLines.add(scanner.nextLine().trim());
                if (forLines.get(i).trim().equals("end for")){
                    forLines.remove(i);
                    break;
                }
                i++;
            }
            for (int j = 0; j < Integer.parseInt(splited[1]); j++) {
                for (String str : forLines) {
                    compute(scanner,str.trim().split("[\\s]+"));
                }
            }
        }else if(splited[0].equals("print")){
            FilePrinter filePrinter = new FilePrinter(splited,outputPath);
        } else if (splited[3].equals("+")){
            Sum sum = new Sum(splited);
        } else if (splited[3].equals("-")){
            Subtraction subtraction = new Subtraction(splited);
        } else if (splited[3].equals("*")){
            Multiple multiple = new Multiple(splited);
        } else if (splited[3].equals("/")){

        }
    }
}
