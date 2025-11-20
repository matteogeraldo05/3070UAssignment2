import java.util.Arrays;

public class radix {
    // function which determines the value of an arbitrary digit
    private static int getDigit(int num, int digit) {
        int working = num / (int) Math.pow(10, digit-1);
        return working % 10;
    }

    public static void countingSort(int[] array, int digitPosition){
        int n = array.length;
        int[] B = new int[n];
        int[] C = new int[10];

        for (int i = 0; i <= 9; i++){
            C[i] = 0;
        }
        for (int j = 0; j < n; j++){
            int d = getDigit(array[j], digitPosition);
            C[d] = C[d] + 1;
        }
        for (int i = 1; i <= 9; i++){
            C[i] = C[i] + C[i - 1];
        }
        for  (int j = n - 1; j >= 0; j--){
            int d = getDigit(array[j], digitPosition);
            B[C[d] - 1] = array[j];
            C[d] = C[d] - 1;
        }
        System.arraycopy(B, 0, array, 0, n);
    }

    public static void radixSort(int[] array){
        // sorting numbers between 0 (inclusive) and 1,000,000 (exclusive) so 6 digits max
        for(int digit = 1; digit <= 6; digit++){
            countingSort(array, digit);
        }
    }

    // testing
    public static void main(String[] args) {
        int[] array = {54673, 287415, 754599, 433820, 20, 94883, 941408, 671281, 88254, 48413};
        radixSort(array);
        System.out.println(Arrays.toString(array));
    }
}
