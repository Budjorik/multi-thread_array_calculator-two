import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinCalc extends RecursiveTask {
    public static final long THRESHOLD = 2;
    private CountsArray countsArray;
    private int firstPosition;
    private int pastPosition;

    public ForkJoinCalc(CountsArray countsArray) {
        this.countsArray = countsArray;
        this.firstPosition = 0;
        this.pastPosition = countsArray.getNumbersArray().length;
    }

    public ForkJoinCalc(CountsArray countsArray, int firstPosition, int pastPosition) {
        this.countsArray = countsArray;
        this.firstPosition = firstPosition;
        this.pastPosition = pastPosition;
    }

    public CountsArray getCountsArray() {
        return this.countsArray;
    }

    @Override
    protected Long compute() {
        final int diff = pastPosition - firstPosition;
        switch (diff) {
            case 0: return (long) 0;
            case 1: return countsArray.getNumbersArray()[firstPosition];
            case 2: return countsArray.getNumbersArray()[firstPosition] +
                    countsArray.getNumbersArray()[firstPosition + 1];
            default: return forkTasksAndGetResult();
        }
    }

    private long forkTasksAndGetResult() {
        int middle = (pastPosition - firstPosition) / 2 + firstPosition;
        ForkJoinCalc firstTask = new ForkJoinCalc(countsArray, firstPosition, middle);
        ForkJoinCalc secondTask = new ForkJoinCalc(countsArray, middle, pastPosition);
        invokeAll(firstTask, secondTask);
        Long firstTaskResult = (Long) firstTask.join();
        Long secondTaskResult = (Long) secondTask.join();
        return firstTaskResult + secondTaskResult;
    }

    public void startForkJoinSum() {
        long lStartTime = System.nanoTime();
        RecursiveTask<Long> task = new ForkJoinCalc(countsArray);
        long sumOfArray = new ForkJoinPool().invoke(task);
        long lEndTime = System.nanoTime();
        long output = lEndTime - lStartTime;
        System.out.printf("Результат работы многопоточной рекурсивной функции:\n" +
                        " - сумма значений массива = %d,\n" +
                        " - среднее значение = %d,\n" +
                        " - время выполнения = %d мс.\n",
                sumOfArray, (long) sumOfArray / countsArray.getNumbersArray().length, output / 1_000_000);
    }

}
