import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class QueueExample {
    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(4);
        queue.offer(9);
        System.out.println(queue);
        System.out.println(queue.size());
        System.out.println(queue.element());
        System.out.println(queue.peek());
        System.out.println(queue.remove());
        System.out.println(queue.poll());
        System.out.println(queue.isEmpty());
        System.out.println(queue);
        System.out.println("=".repeat(100));

        Deque<Integer> deque = new ArrayDeque<>();
        deque.add(4);
        deque.offer(9);
        deque.addFirst(2);
        deque.offerFirst(3);
        deque.addLast(5);
        deque.offerLast(7);
        System.out.println(deque);
        System.out.println(deque.size());
        System.out.println(deque.element());
        System.out.println(deque.peek());
        System.out.println(deque.getFirst());
        System.out.println(deque.peekFirst());
        System.out.println(deque.getLast());
        System.out.println(deque.peekLast());
        System.out.println(deque.remove());
        System.out.println(deque.poll());
        System.out.println(deque.removeFirst());
        System.out.println(deque.pollFirst());
        System.out.println(deque.removeLast());
        System.out.println(deque.pollLast());
        System.out.println(deque.isEmpty());
        System.out.println(deque);
        System.out.println("=".repeat(100));

        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(11);
        stack.push(13);
        stack.push(17);
        stack.push(19);
        System.out.println(stack);
        System.out.println(stack.size());
        System.out.println(stack.peek());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.isEmpty());
        System.out.println(stack);
    }
}
