package lesson4;

import java.util.*;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Префиксное дерево для строк
 */
public class Trie extends AbstractSet<String> implements Set<String> {

    private static class Node {
        SortedMap<Character, Node> children = new TreeMap<>();
    }

    private final Node root = new Node();

    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root.children.clear();
        size = 0;
    }

    private String withZero(String initial) {
        return initial + (char) 0;
    }

    @Nullable
    private Node findNode(String element) {
        Node current = root;
        for (char character : element.toCharArray()) {
            if (current == null) return null;
            current = current.children.get(character);
        }
        return current;
    }

    @Override
    public boolean contains(Object o) {
        String element = (String) o;
        return findNode(withZero(element)) != null;
    }

    @Override
    public boolean add(String element) {
        Node current = root;
        boolean modified = false;
        for (char character : withZero(element).toCharArray()) {
            Node child = current.children.get(character);
            if (child != null) {
                current = child;
            } else {
                modified = true;
                Node newChild = new Node();
                current.children.put(character, newChild);
                current = newChild;
            }
        }
        if (modified) {
            size++;
        }
        return modified;
    }

    @Override
    public boolean remove(Object o) {
        String element = (String) o;
        Node current = findNode(element);
        if (current == null) return false;
        if (current.children.remove((char) 0) != null) {
            size--;
            return true;
        }
        return false;
    }

    /**
     * Итератор для префиксного дерева
     * <p>
     * Спецификация: {@link Iterator} (Ctrl+Click по Iterator)
     * <p>
     * Сложная
     */
    @NotNull
    @Override
    public Iterator<String> iterator() {
        return new TrieIterator();
    }

    public class TrieIterator implements Iterator<String> {
        ArrayDeque<String> wordsDeque = new ArrayDeque<>();
        String current;

        void addWordsInDeque(Node node, String word) {
            for (var child : node.children.entrySet()) {
                if (child.getKey() != (char) 0) {
                    addWordsInDeque(child.getValue(), word + child.getKey());
                } else {
                    wordsDeque.add(word);
                }
            }
        }

        TrieIterator() {
            addWordsInDeque(root, "");
        }

        //T = O(1)
        //R = O(1)
        @Override
        public boolean hasNext() {
            return !wordsDeque.isEmpty();
        }

        //T = O(N)
        //R = O(N)
        @Override
        public String next() {
            if (hasNext()) {
                return current = wordsDeque.pop();
            } else throw new NoSuchElementException();
        }

        //T = O(N)
        //R = O(N)
        @Override
        public void remove() {
            if (current == null) {
                throw new IllegalStateException();
            }
            else {
                Trie.this.remove(current);
                current = null;
            }
        }
    }
}
