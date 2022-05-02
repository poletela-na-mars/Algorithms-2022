package lesson7;

import kotlin.NotImplementedError;

import java.util.*;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     *
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    public static String longestCommonSubSequence(String first, String second) {
        //T = O(длина первой строки * длина второй строки)
        //R = O(длина первой строки * длина второй строки)

        int lengthFirst = first.length();
        int lengthSecond = second.length();

        if (lengthFirst == 0 || lengthSecond == 0) return "";
        if (first.equals(second)) return first;

        int[][] longest = new int[lengthFirst + 1][lengthSecond + 1];
        for (int i = 1; i < lengthFirst + 1; i++) {
            for (int j = 1; j < lengthSecond + 1; j++) {
                if (first.charAt(i - 1) == second.charAt(j - 1)) longest[i][j] = longest[i - 1][j - 1] + 1;
                else  longest[i][j] = Math.max(longest[i - 1][j],longest[i][j - 1]);
            }
        }

        StringBuilder result = new StringBuilder();
        int i = lengthFirst;
        int j = lengthSecond;

        while (i != 0 && j != 0) {
            if (first.charAt(i - 1) == second.charAt(j - 1)) {
                result.append(first.charAt(i - 1));
                i--;
                j--;
            }
            else if (longest[i - 1][j] == longest[i][j]) i--;
            else j--;
        }

        result.reverse();
        return String.valueOf(result);
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     *
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */
    //getting index of first element, which is greater than list.get(i)
    public static int doBinarySearch(int [] array, int el) {
        int middle;
        int left = 0;
        int right = array.length - 1;
        while (left < right) {
            middle = (left + right) / 2;
            if (array[middle] < el) right = middle;
            else left = middle + 1;
        }
        return left;
    }

    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        //T = O(N * log(N))
        //R = O(N)

        int size = list.size();
        if (size == 0) return Collections.emptyList();

        List<Integer> longest = new ArrayList<>(size);

        //array for the smallest last element for a subsequence of length idx
        int[] arrayForLastElems = new int[size + 1];
        //position store the idx of element stored at i-th position in arrayForLastElems
        int[] position = new int[size + 1];
        position[0] = Integer.MIN_VALUE;
        int[] previous = new int[size];

        int length  = 0;

        Arrays.fill(arrayForLastElems, Integer.MIN_VALUE);
        arrayForLastElems[0] = Integer.MAX_VALUE;

        int j;
        for (int i = size - 1; i >= 0; i--) {
            j = doBinarySearch(arrayForLastElems, list.get(i));
            if (arrayForLastElems[j - 1] > list.get(i) && arrayForLastElems[j] < list.get(i)) {
                arrayForLastElems[j] = list.get(i);
                position[j] = i;
                previous[i] = position[j - 1];
                length = Math.max(j, length);
            }
        }

        int currentPos = position[length];
        //restore answer
        while (currentPos != Integer.MIN_VALUE) {
            longest.add(list.get(currentPos));
            currentPos = previous[currentPos];
        }

        return longest;
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     *
     * В файле с именем inputName задано прямоугольное поле:
     *
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     *
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     *
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    public static int shortestPathOnField(String inputName) {
        throw new NotImplementedError();
    }
}
