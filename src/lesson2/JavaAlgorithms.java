package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;

import java.util.Arrays;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     * <p>
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     * <p>
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     * <p>
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) {
        throw new NotImplementedError();
    }

    /**
     * Задача Иосифа Флафия.
     * Простая
     * <p>
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 5
     * <p>
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 х
     * <p>
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 х 3
     * 8   4
     * 7 6 Х
     * <p>
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     * <p>
     * 1 Х 3
     * х   4
     * 7 6 Х
     * <p>
     * 1 Х 3
     * Х   4
     * х 6 Х
     * <p>
     * х Х 3
     * Х   4
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   х
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   Х
     * Х х Х
     * <p>
     * Общий комментарий: решение из Википедии для этой задачи принимается,
     * но приветствуется попытка решить её самостоятельно.
     */
    static public int josephTask(int menNumber, int choiceInterval) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     * <p>
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     */
    static public String longestCommonSubstring(String firs, String second) {
        // T = O(длина_первой * длина_второй)
        // R = O((длина_первой + 1) * (длина_второй + 1))
        int[][] sub = new int[firs.length() + 1][second.length() + 1];
        int maxSubstringLength = 0;
        int lastIdxOfMaxSubstring = 0;
        String maxSubstring = "";

        if (firs.equals("") || second.equals("")) return maxSubstring;
        if (firs.equals(second)) return firs;

        for (int i = 1; i < firs.length() + 1; i++) {
            for (int j = 1; j < second.length() + 1; j++) {
                if (firs.charAt(i - 1) == second.charAt(j - 1)) {
                    sub[i][j] = sub[i - 1][j - 1] + 1;
                    int subLength = sub[i][j];
                    if (subLength > maxSubstringLength) {
                        maxSubstringLength = subLength;
                        lastIdxOfMaxSubstring = i;
                    }
                }
            }
        }

        maxSubstring = (String) firs.subSequence(lastIdxOfMaxSubstring - maxSubstringLength, lastIdxOfMaxSubstring);
        return maxSubstring;
    }

    /**
     * Число простых чисел в интервале
     * Простая
     * <p>
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     * <p>
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     */
    static public int calcPrimesNumber(int limit) {
        //T = O(log(log(n)))
        //R = O(N), ~ limit
        if (limit <= 1) return 0;
        Boolean[] arr = new Boolean[limit + 1];
        for (int i = 2; i < arr.length; i++) {
            arr[i] = true;
        }
        arr[0] = false;
        arr[1] = false;
        int maxDiv = (int) Math.sqrt(limit);
        for (int i = 2; i <= maxDiv; i++) {
            if (arr[i]) {
                for (int j = i * i; j <= limit; j += i) {
                    if (arr[j]) {
                        arr[j] = false;
                    }
                }
            }
        }
        return (int) Arrays.stream(arr).filter(c -> c).count();
    }
}
