package df.base.internal.stuff;

public class FFT {

    private int numberOfSamples = 128; // Кількість зразків
    private double samplingFrequency = 16000.0; // Частота вибірки в Гц (16 кГц)
    private double[] inputSignal; // Вхідний сигнал
    private double[] amplitudes; // Амплітуди частотних компонент
    private double[] frequencies; // Частоти, що відповідають індексам FFT

    public FFT() {
        this.inputSignal = new double[numberOfSamples];
        this.amplitudes = new double[numberOfSamples / 2];
        this.frequencies = new double[numberOfSamples / 2];

        calculateFrequencies();
    }

    // Метод для встановлення вхідного сигналу
    public void setInputSignal(double[] signal) {
        if (signal.length != numberOfSamples) {
            throw new IllegalArgumentException("Signal size does not match number of samples.");
        }
        System.arraycopy(signal, 0, inputSignal, 0, numberOfSamples);
    }

    // Метод для виконання FFT з обчисленням тільки амплітуди
    public void performFFT() {
        for (int k = 0; k < numberOfSamples / 2; k++) {
            double realSum = 0;
            double imaginarySum = 0;

            for (int n = 0; n < numberOfSamples; n++) {
                double angle = 2 * Math.PI * k * n / numberOfSamples;
                realSum += inputSignal[n] * Math.cos(angle);
                imaginarySum -= inputSignal[n] * Math.sin(angle);
            }

            // Обчислення амплітуди
            amplitudes[k] = Math.sqrt(realSum * realSum + imaginarySum * imaginarySum) / numberOfSamples;
        }
    }

    // Метод для отримання амплітуди для певного індексу k
    public double getAmplitude(int index) {
        if (index < 0 || index >= numberOfSamples / 2) {
            throw new IllegalArgumentException("Index out of bounds.");
        }
        return amplitudes[index];
    }

    // Метод для отримання частоти для певного індексу k
    public double getFrequency(int index) {
        if (index < 0 || index >= numberOfSamples / 2) {
            throw new IllegalArgumentException("Index out of bounds.");
        }
        return frequencies[index];
    }

    // Метод для виведення результатів FFT
    public void printResults() {
        for (int k = 0; k < numberOfSamples / 2; k++) { // Виводимо тільки до numberOfSamples/2, так як результати симетричні
            System.out.println("\n---\nFrequency bin " + k + ": \n"
                    + "\tFrequency = " + getFrequency(k) + " Hz, \n"
                    + "\tAmplitude = " + getAmplitude(k) + ", \n"
                    + "\tMapped = " + map(getAmplitude(k), 0, 0.5 / 2, 0, 1024));
        }
    }

    // Метод для обчислення частот, що відповідають індексам FFT
    private void calculateFrequencies() {
        for (int k = 0; k < numberOfSamples / 2; k++) {
            frequencies[k] = (double) k * samplingFrequency / numberOfSamples;
        }
    }

    public static void main(String[] args) {
        // Створюємо об'єкт FFT з параметрами за замовчуванням
        FFT fft = new FFT();

        // Приклад змішаного сигналу з частотами від 100 Гц до 15000 Гц
        double[] signal = new double[128];
        for (int i = 0; i < 128; i++) {
            // Додавання декількох частот
            signal[i] = 0.5 * Math.sin(2 * Math.PI * 125 * i / 16000.0) + // 100 Гц
                    0.5 * Math.sin(2 * Math.PI * 512 * i / 16000.0) + // 500 Гц
                    0.5 * Math.sin(2 * Math.PI * 1330 * i / 16000.0) + // 1000 Гц
                    0.5 * Math.sin(2 * Math.PI * 5433 * i / 16000.0) + // 5000 Гц
                    0.5 * Math.sin(2 * Math.PI * 7299 * i / 16000.0); // 15000 Гц
        }

        // Встановлюємо вхідний сигнал
        fft.setInputSignal(signal);

        // Виконуємо FFT
        fft.performFFT();

        // Виводимо результати
        fft.printResults();

        System.out.println(
                map(7895, 0, 10000, 0, 1024)
        );
    }

    public static double map(double value, double startA, double endA, double startB, double endB) {
        assert startA < endA;
        assert startB < endB;

        return (((endB - startB) / (endA - startA)) * value);
    }

}

