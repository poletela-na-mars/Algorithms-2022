package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.util.*;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     * <p>
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     * <p>
     * Пример:
     * <p>
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     * <p>
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     * <p>
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка адресов
     * <p>
     * Средняя
     * <p>
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     * <p>
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     * <p>
     * Людей в городе может быть до миллиона.
     * <p>
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     * <p>
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка температур
     * <p>
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     * <p>
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     * <p>
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     * <p>
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    static public void sortTemperatures(String inputName, String outputName) throws IOException {
        //T = O(N)
        //R = O(N), N - строки в файле
        int minTemp = -2730;
        int maxTemp = 5000;
        int[] arr = new int[7731]; //2730 + 5000 + 1

        File inputFile = new File(inputName);
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String str;
            double lineToDouble;
            int numberToInt;
            while ((str = reader.readLine()) != null) {
                lineToDouble = Double.parseDouble(str.trim());
                if (lineToDouble < -273.0 || lineToDouble > 500.0) {
                    throw new IllegalArgumentException();
                }
                numberToInt = (int) (lineToDouble * 10);
                arr[numberToInt + Math.abs(minTemp)]++;
            }
        }

        File outputFile = new File(outputName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[i]; j++) {
                    writer.write((double) (minTemp + i) / 10 + System.lineSeparator());
                }
            }
        }
    }


    /**
     * Сортировка последовательности
     * <p>
     * Средняя
     * (Задача взята с сайта acmp.ru)
     * <p>
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     * <p>
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     * <p>
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     * <p>
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) throws IOException {
        //T = O(N), ~ от количества чисел (строк) в файле
        //R = O(N), N - строки в файле
        String str;
        int max = 0; //число, повторяющееся наибольшее количество раз
        int maxOccur = 0; //число повторов
        List<Integer> numbers = new ArrayList<>();
        List<Integer> numbersToWrite = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>(); //число: сколько раз было записано

        File inputFile = new File(inputName);
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            int lineToInteger;
            while ((str = reader.readLine()) != null) {
                lineToInteger = Integer.parseInt(str.trim());
                numbers.add(lineToInteger);
                if (map.containsKey(lineToInteger)) {
                    map.put(lineToInteger, map.get(lineToInteger) + 1);
                } else {
                    map.put(lineToInteger, 1);
                }

                //поиск числа с максимальным количеством повторов, если несколько - берем меньшее
                if (maxOccur < map.get(lineToInteger)) {
                    maxOccur = map.get(lineToInteger);
                    max = lineToInteger;
                }
                if ((map.get(lineToInteger) == maxOccur) && (max > lineToInteger)) {
                    max = lineToInteger;
                }
            }

            for (Integer number : numbers) {
                if (number != max) {
                    numbersToWrite.add(number);
                }
            }

            for (int i = 0; i < maxOccur; i++) {
                numbersToWrite.add(max);
            }
        }

        File outputFile = new File(outputName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            for (Integer number : numbersToWrite) {
                writer.write(number + System.lineSeparator());
            }
        }
    }


    /**
     * Соединить два отсортированных массива в один
     * <p>
     * Простая
     * <p>
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     * <p>
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     * <p>
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        throw new NotImplementedError();
    }
}
