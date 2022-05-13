public class CountsArray {
    private int sizeArray;
    private int minNumber;
    private int maxNumber;
    private long[] numbersArray;

    public CountsArray(int sizeArray, int minNumber, int maxNumber) {
        this.sizeArray = sizeArray;
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        this.numbersArray = createArray(sizeArray, minNumber, maxNumber);
    }

    public long[] getNumbersArray() {
        return this.numbersArray;
    }

    private long[] createArray(int sizeArray, int minNumber, int maxNumber) {
        long[] newArray = new long[sizeArray];
        for (int i = 0 ; i < newArray.length ; i++) {
            maxNumber -= minNumber;
            newArray[i] = (long) (Math.random() * ++maxNumber) + minNumber;
        }
        return newArray;
    }

    public void calculateSum() {
        long lStartTime = System.nanoTime();
        long sumOfArray = forCalculateSum();
        long lEndTime = System.nanoTime();
        long output = lEndTime - lStartTime;
        System.out.printf("Результат работы обычной функции:\n" +
                        " - сумма значений массива = %d,\n" +
                        " - среднее значение = %d,\n" +
                        " - время выполнения = %d мс.\n",
                sumOfArray, (long) sumOfArray / numbersArray.length, output / 1_000_000);
    }

    private long forCalculateSum() {
        long sumOfArray = 0;
        for (long number : numbersArray) {
            sumOfArray += number;
        }
        return sumOfArray;
    }

    public void recursionCalculateSum() {
        long lStartTime = System.nanoTime();
        int currentPosition = 0;
        long sumOfArray = recursionCalculate(numbersArray, currentPosition);
        long lEndTime = System.nanoTime();
        long output = lEndTime - lStartTime;
        System.out.printf("Результат работы однопоточной рекурсивной функции:\n" +
                        " - сумма значений массива = %d,\n" +
                        " - среднее значение = %d,\n" +
                        " - время выполнения = %d мс.\n",
                sumOfArray, (long) sumOfArray / numbersArray.length, output / 1_000_000 );
    }

    private long recursionCalculate(long[] numbersArray, int currentPosition) {
        if (currentPosition == (numbersArray.length - 1)) {
            return numbersArray[currentPosition];
        } else {
            return numbersArray[currentPosition] + recursionCalculate(numbersArray, currentPosition + 1);
        }
    }

    public void showArray() {
        for (long number : numbersArray) {
            System.out.print(number + ", ");
        }
        System.out.println();
    }

}

