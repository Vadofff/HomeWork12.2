import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FizzBuzzMultithreaded {

    private int n;
    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private final Object lock = new Object();
    private int currentNumber = 1;

    public FizzBuzzMultithreaded(int n) {
        this.n = n;
    }

    public void fizz() throws InterruptedException {
        while (currentNumber <= n) {
            synchronized (lock) {
                if (currentNumber % 3 == 0 && currentNumber % 5 != 0) {
                    queue.put("fizz");
                    currentNumber++;
                    lock.notifyAll();
                } else {
                    lock.wait();
                }
            }
        }
    }

    public void buzz() throws InterruptedException {
        while (currentNumber <= n) {
            synchronized (lock) {
                if (currentNumber % 3 != 0 && currentNumber % 5 == 0) {
                    queue.put("buzz");
                    currentNumber++;
                    lock.notifyAll();
                } else {
                    lock.wait();
                }
            }
        }
    }

    public void fizzbuzz() throws InterruptedException {
        while (currentNumber <= n) {
            synchronized (lock) {
                if (currentNumber % 3 == 0 && currentNumber % 5 == 0) {
                    queue.put("fizzbuzz");
                    currentNumber++;
                    lock.notifyAll();
                } else {
                    lock.wait();
                }
            }
        }
    }

    public void number() throws InterruptedException {
        while (currentNumber <= n) {
            synchronized (lock) {
                if (currentNumber % 3 != 0 && currentNumber % 5 != 0) {
                    queue.put(String.valueOf(currentNumber));
                    currentNumber++;
                    lock.notifyAll();
                } else {
                    lock.wait();
                }
            }
        }
    }

    public static void main(String[] args) {
        FizzBuzzMultithreaded fizzBuzz = new FizzBuzzMultithreaded(100);

        Thread threadA = new Thread(() -> {
            try {
                fizzBuzz.fizz();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                fizzBuzz.buzz();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadC = new Thread(() -> {
            try {
                fizzBuzz.fizzbuzz();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadD = new Thread(() -> {
            try {
                fizzBuzz.number();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();

        try {
            threadA.join();
            threadB.join();
            threadC.join();
            threadD.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Виведення результату
        while (!fizzBuzz.queue.isEmpty()) {
            System.out.println(fizzBuzz.queue.poll());
        }
    }
}
