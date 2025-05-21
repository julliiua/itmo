package main.java;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        // задание 1
        String file = "file";
        try {
        new ByteArrayOutputStream().flush();
        //new FileInputStream("file").flush();
        new CharArrayWriter().flush();
        new BufferedWriter(new FileWriter("file")).flush();
        new FileOutputStream("file").flush();
        //new BufferedReader(new FileReader("file")).flush();
        //new CharArrayReader(new char[1]).flush();
        //new ByteArrayInputStream(new byte[1]).flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //задание 2
        Set<Integer> set = new TreeSet<>();
        set.add(5);
        set.add(4);
        set.add(3);
        set.add(2);
        set.add(4);
        set.add(1);
        set.remove(3);
        System.out.println(set);

        List<Integer> list = new ArrayList<>();
        list.add(5); list.add(2); list.add(3); list.add(0,4); list.add(2);
        list.remove(1);
        System.out.println(list);

        //задание 3
        Stream.of("january", "february", "march", "april", "may", "june")
                .filter(s -> s.length() <= 7)
                .map(s -> s + s.substring(s.length() - 1)) // dupLast(): дублируем последний символ
                .skip(2)
                .sorted()
                .forEachOrdered(System.out::print);

        System.out.print("\n");

        Stream.of("january", "february", "march", "april", "may", "june")
                .filter(s -> s.length() != 5)
                .map(s -> s + s.substring(s.length() - 1)) // dupLast(): дублируем последний символ
                .skip(2)
                .sorted()
                .forEachOrdered(System.out::print);


        //задание 9


    }
}