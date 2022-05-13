import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class Main {
    public static void main(String[] args) throws Exception {
        int sizeArray = 300_000_000;
        int minNumber = 1;
        int maxNumber = 100;
        CountsArray firstArray = new CountsArray(sizeArray, minNumber, maxNumber);
        ForkJoinCalc forkJoinCalc = new ForkJoinCalc(firstArray);
        // Запуск обычной функции
        firstArray.calculateSum();
        // Запуск однопоточной рекурсивной функции
//        firstArray.recursionCalculateSum();
        // Запуск многопоточной рекурсивной функции
        forkJoinCalc.startForkJoinSum();
    }
}
