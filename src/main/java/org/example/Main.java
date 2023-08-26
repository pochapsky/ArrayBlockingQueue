package org.example;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static BlockingQueue<String> queue1 = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> queue2 = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> queue3 = new ArrayBlockingQueue<>(100);

    public static void main(String[] args) throws InterruptedException {

        Thread threadAdd = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                queue1.add(generateText("abc", 100_000));
                queue2.add(generateText("abc", 100_000));
                queue3.add(generateText("abc", 100_000));
            }
        });

        threadAdd.start();
        threadAdd.join();

        Thread thread_A = new Thread(() -> {
            System.out.println("\n\nСтрока с максимальным количеством символов - a: " + maxCharStringQueue(queue1,
                    'a'));
        });

        Thread thread_B = new Thread(() -> {
            System.out.println("\n\nСтрока с максимальным количеством символов - b: " + maxCharStringQueue(queue2,
                    'b'));
        });

        Thread thread_C = new Thread(() -> {
            System.out.println("\n\nСтрока с максимальным количеством символов - c: " + maxCharStringQueue(queue3,
                    'c'));
        });
        thread_A.start();
        thread_B.start();
        thread_C.start();

        thread_C.join();
        thread_B.join();
        thread_A.join();
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static String maxCharStringQueue(BlockingQueue<String> queue, char targetChar) {

        String maxCharString = "";
        int maxCharCount = 0;

        for (String str : queue) {
            int charCount = countChar(str, targetChar);

            if (charCount > maxCharCount) {
                maxCharCount = charCount;
                maxCharString = str;
            }
        }
        return maxCharString;
    }

    public static int countChar(String str, char targetChar) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == targetChar) {
                count++;
            }
        }
        return count;
    }

}