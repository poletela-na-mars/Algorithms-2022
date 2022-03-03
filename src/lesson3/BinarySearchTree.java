package lesson3;

import java.util.*;
import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// attention: Comparable is supported but Comparator is not
public class BinarySearchTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        final T value;
        Node<T> left = null;
        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    /**
     * Добавление элемента в дерево
     *
     * Если элемента нет в множестве, функция добавляет его в дерево и возвращает true.
     * В ином случае функция оставляет множество нетронутым и возвращает false.
     *
     * Спецификация: {@link Set#add(Object)} (Ctrl+Click по add)
     *
     * Пример
     */
    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        }
        else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        }
        else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    /**
     * Удаление элемента из дерева
     *
     * Если элемент есть в множестве, функция удаляет его из дерева и возвращает true.
     * В ином случае функция оставляет множество нетронутым и возвращает false.
     * Высота дерева не должна увеличиться в результате удаления.
     *
     * Спецификация: {@link Set#remove(Object)} (Ctrl+Click по remove)
     *
     * Средняя
     */
    @Override
    //T = O(высота дерева); O(logN) в среднем случае, N - количество узлов. В худшем O(N)
    //R = O(1)
    public boolean remove(Object o) {
        Node<T> currentNode = root;
        Node<T> parentNode = root;

        T removeValue = (T) o; //значение удаляемого узла
        if (root == null || removeValue == null) return false;
        boolean isLeftChild = true;

        while (currentNode.value != removeValue) {
            parentNode = currentNode;
            if (removeValue.compareTo(currentNode.value) < 0) {  //если меньше текущего - движение влево
                currentNode = currentNode.left;
                isLeftChild = true;
            } else {                                      //если больше - движение вправо
                isLeftChild = false;
                currentNode = currentNode.right;
            }
            if (currentNode == null) {  //узла нет
                return false;
            }
        }

        //Ситуация 1: у удаляемого узла нет потомков
        if (currentNode.left == null && currentNode.right == null) {
            change(currentNode, parentNode, null, isLeftChild);
        }

        //Ситуация 2: у удаляемого узла 1 потомок
        else if (currentNode.right == null) {   //Ситуация 2.1: есть левый потомок
            change(currentNode, parentNode, currentNode.left, isLeftChild);
        } else if (currentNode.left == null) {   //Ситуация 2.2: есть правый потомок
            change(currentNode,parentNode, currentNode.right, isLeftChild);
        }

        //Ситуация 3: у удаляемого узла 2 потомка, в этом случае место удаляемого узла занимает самый левый лист
        //из правого поддерева от удаляемого узла.
        //То есть на месте удаляемого узла - наименьшее значение (левый лист) в правом поддереве
        else {
            Node<T> nParent = currentNode;
            Node<T> n = currentNode.right; //идем в правое поддерево

            while (n.left != null) {  //движемся к левому листу -> след.элементу по значению после удаляемого
                nParent = n;
                n = n.left;
            }
            if (n != currentNode.right) { //если преемник не является правым потомком
                nParent.left = n.right;
                n.right = currentNode.right;
            }
            n.left = currentNode.left;
            change(currentNode, parentNode, n, isLeftChild);
        }
        size--;
        return true;
    }

    public void change(Node<T> currentNode, Node<T> parentNode, Node<T> change, boolean isLeftChild) {
        if (currentNode == root) {
            root = change;
        } else if (isLeftChild) {
            parentNode.left = change;
        } else {
            parentNode.right = change;
        }
    }

    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinarySearchTreeIterator();
    }

    public class BinarySearchTreeIterator implements Iterator<T> {
        private Node<T> node;
        private final Stack<Node<T>> stack = new Stack<>();
        private int count = 0;
        private int nextCount = 0;

        private void pushLeft(Node<T> node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }

        private BinarySearchTreeIterator() {
            pushLeft(root);
        }

        /**
         * Проверка наличия следующего элемента
         * <p>
         * Функция возвращает true, если итерация по множеству ещё не окончена (то есть, если вызов next() вернёт
         * следующий элемент множества, а не бросит исключение); иначе возвращает false.
         * <p>
         * Спецификация: {@link Iterator#hasNext()} (Ctrl+Click по hasNext)
         * <p>
         * Средняя
         */
        @Override
        //T = O(1)
        //R = O(1)
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        /**
         * Получение следующего элемента
         * <p>
         * Функция возвращает следующий элемент множества.
         * Так как BinarySearchTree реализует интерфейс SortedSet, последовательные
         * вызовы next() должны возвращать элементы в порядке возрастания.
         * <p>
         * Бросает NoSuchElementException, если все элементы уже были возвращены.
         * <p>
         * Спецификация: {@link Iterator#next()} (Ctrl+Click по next)
         * <p>
         * Средняя
         */
        @Override
        //T = O(1)
        //R = O(1)
        public T next() {
            nextCount++;
            count = 0;
            if (hasNext()) {
                node = stack.pop();
                pushLeft(node.right);
            } else throw new NoSuchElementException();
            if (node == null) throw new NoSuchElementException(); // если все элементы были возвращены
            return node.value;
        }

        /**
         * Удаление предыдущего элемента
         * <p>
         * Функция удаляет из множества элемент, возвращённый крайним вызовом функции next().
         * <p>
         * Бросает IllegalStateException, если функция была вызвана до первого вызова next() или же была вызвана
         * более одного раза после любого вызова next().
         * <p>
         * Спецификация: {@link Iterator#remove()} (Ctrl+Click по remove)
         * <p>
         * Сложная
         */
        @Override
        //T = O(высота дерева). O(logN) в среднем случае, N - количество узлов. В худшем O(N)
        //R = O(1)
        public void remove() {
            if (node == null) {
                throw new IllegalStateException();
            }
            if (count == 0 && nextCount != 0) {
                BinarySearchTree.this.remove(node.value);
                count++;
                node = null;
            }
        }
    }

    /**
     * Подмножество всех элементов в диапазоне [fromElement, toElement)
     *
     * Функция возвращает множество, содержащее в себе все элементы дерева, которые
     * больше или равны fromElement и строго меньше toElement.
     * При равенстве fromElement и toElement возвращается пустое множество.
     * Изменения в дереве должны отображаться в полученном подмножестве, и наоборот.
     *
     * При попытке добавить в подмножество элемент за пределами указанного диапазона
     * должен быть брошен IllegalArgumentException.
     *
     * Спецификация: {@link SortedSet#subSet(Object, Object)} (Ctrl+Click по subSet)
     * (настоятельно рекомендуется прочитать и понять спецификацию перед выполнением задачи)
     *
     * Очень сложная (в том случае, если спецификация реализуется в полном объёме)
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Подмножество всех элементов строго меньше заданного
     *
     * Функция возвращает множество, содержащее в себе все элементы дерева строго меньше toElement.
     * Изменения в дереве должны отображаться в полученном подмножестве, и наоборот.
     *
     * При попытке добавить в подмножество элемент за пределами указанного диапазона
     * должен быть брошен IllegalArgumentException.
     *
     * Спецификация: {@link SortedSet#headSet(Object)} (Ctrl+Click по headSet)
     * (настоятельно рекомендуется прочитать и понять спецификацию перед выполнением задачи)
     *
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Подмножество всех элементов нестрого больше заданного
     *
     * Функция возвращает множество, содержащее в себе все элементы дерева нестрого больше toElement.
     * Изменения в дереве должны отображаться в полученном подмножестве, и наоборот.
     *
     * При попытке добавить в подмножество элемент за пределами указанного диапазона
     * должен быть брошен IllegalArgumentException.
     *
     * Спецификация: {@link SortedSet#tailSet(Object)} (Ctrl+Click по tailSet)
     * (настоятельно рекомендуется прочитать и понять спецификацию перед выполнением задачи)
     *
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        // TODO
        throw new NotImplementedError();
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }

    public int height() {
        return height(root);
    }

    private int height(Node<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

}