
import java.util.Arrays;
import java.util.Random;

public class Main {

    // Сортировка пузырьком
    static void bubbleSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < a.length - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                    swapped = true;
                }
            }

            if (!swapped) break;
        }
    }

    // Сортировка выбором
    static void selectionSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int min = i;

            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[min]) {
                    min = j;
                }
            }

            int temp = a[i];
            a[i] = a[min];
            a[min] = temp;
        }
    }

    // Сортировка вставками
    static void insertionSort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int key = a[i];
            int j = i - 1;

            while (j >= 0 && a[j] > key) {
                a[j + 1] = a[j];
                j--;
            }

            a[j + 1] = key;
        }
    }

    // Быстрая сортировка
    static void quickSort(int[] a) {
        quickSort(a, 0, a.length - 1);
    }

    static void quickSort(int[] a, int low, int high) {
        if (low >= high) return;

        int pivot = a[low + (high - low) / 2];
        int i = low;
        int j = high;

        while (i <= j) {
            while (a[i] < pivot) i++;
            while (a[j] > pivot) j--;

            if (i <= j) {
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;
                i++;
                j--;
            }
        }

        if (low < j) quickSort(a, low, j);
        if (i < high) quickSort(a, i, high);
    }

    interface Sorter {
        void sort(int[] a);
    }

    static long median(long[] times) {
        Arrays.sort(times);
        return times[times.length / 2];
    }

    static long measure(Sorter sorter, int[] original) {
        int[] copy = original.clone();

        long start = System.nanoTime();
        sorter.sort(copy);
        long finish = System.nanoTime();

        if (!isSorted(copy)) {
            throw new RuntimeException("Ошибка сортировки!");
        }

        int[] expected = original.clone();
        Arrays.sort(expected);

        if (!Arrays.equals(copy, expected)) {
            throw new RuntimeException("Изменились значения массива!");
        }

        return finish - start;
    }

    static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i - 1] > a[i]) return false;
        }
        return true;
    }

    static void additionalTask() {
        int n = 10000;
        int[] sorted = new int[n];
        int[] reversed = new int[n];

        for (int i = 0; i < n; i++) {
            sorted[i] = i;
            reversed[i] = n - i;
        }

        System.out.println("\nДополнительное задание");
        System.out.println("Сортировка вставками, размер 10000");

        for (int test = 0; test < 3; test++) {
            long t1 = measure(Main::insertionSort, sorted);
            long t2 = measure(Main::insertionSort, reversed);

            System.out.println("Повтор " + (test + 1)
                    + ": отсортированный = " + t1
                    + " нс; обратный = " + t2 + " нс");
        }
    }

    public static void main(String[] args) {
        int[] sizes = {10, 100, 1000, 10000};
        String[] names = {
            "Bubble Sort", "Selection Sort",
            "Insertion Sort", "Quick Sort"
        };

        Sorter[] sorters = {
            Main::bubbleSort,
            Main::selectionSort,
            Main::insertionSort,
            Main::quickSort
        };

        Random random = new Random(12345);

        System.out.println("Лабораторная работа №9");
        System.out.println("Сложность сортировки в Java");
        System.out.println("Время указано в наносекундах.");
        System.out.println("Медиана пяти измерений.\n");

        System.out.printf("%-18s %8s %18s%n",
                "Алгоритм", "Размер", "Время, нс");

        for (int n : sizes) {
            int[] original = new int[n];

            for (int i = 0; i < n; i++) {
                original[i] = random.nextInt(200001) - 100000;
            }

            for (int a = 0; a < sorters.length; a++) {
                // Прогревочные запуски
                for (int w = 0; w < 2; w++) {
                    int[] copy = original.clone();
                    sorters[a].sort(copy);
                }

                long[] times = new long[5];

                for (int r = 0; r < times.length; r++) {
                    times[r] = measure(sorters[a], original);
                }

                long result = median(times);

                System.out.printf("%-18s %8d %18d%n",
                        names[a], n, result);
            }
        }

        additionalTask();

        System.out.println("\nВсе проверки выполнены успешно.");
        System.out.println("Скопируйте таблицу результатов");
        System.out.println("для отчета и постройте график.");
    }
}
