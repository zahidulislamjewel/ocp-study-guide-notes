import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockExample {
    public static void printHello(Lock lock) {
        // Acquiring the lock before entering the critical section
        lock.lock();
        try {
            System.out.println("Hello, World!");
        } finally {
            lock.unlock();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> printHello(lock)).start();
        }

        // tryLock attempts to acquire the lock immediately, without waiting if it's held by another thread it returns false
        if(lock.tryLock()) {
            try {
                System.out.println("Main thread acquired the lock (1st Time). Entering the critical section.");
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("Main thread could not acquire the lock. Exiting. (1st Time)");
        }

        // tryLock waits for 5 seconds to acquire the lock even if it's held by another thread
        if(lock.tryLock(5, TimeUnit.SECONDS)) {
            try {
                System.out.println("Main thread acquired the lock again (with delay) (2nd Time). Entering the critical section.");
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("Main thread could not acquire the lock. Exiting. (2nd Time)");
        }
    }
}
