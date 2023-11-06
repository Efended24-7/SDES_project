import java.util.Scanner;

public class SDESpt2 {

    static int key[] = new int[10];
    int key_1[] = new int[8];
    int key_2[] = new int[8];

    int P10[] = { 3, 5, 2, 7, 4, 10, 1, 9, 8, 6 };
    int P8[] = { 6, 3, 7, 4, 8, 5, 10, 9 };
    int[] IP = { 2, 6, 3, 1, 4, 8, 5, 7 };
    int[] EP = { 4, 1, 2, 3, 2, 3, 4, 1 };
    int[] P4 = { 2, 4, 3, 1 };
    int[] IP_inv = { 4, 1, 3, 5, 7, 2, 8, 6 };
    int[][] S0 = { { 1, 0, 3, 2 }, { 3, 2, 1, 0 }, { 0, 2, 1, 3 }, { 3, 1, 3, 2 } };
    int[][] S1 = { { 0, 1, 2, 3 }, { 2, 0, 1, 3 }, { 3, 0, 1, 0 }, { 2, 1, 0, 3 } };

    void key_generation()
    {
        int key_temp[] = new int[10];

        //System.out.println("Print P10: ");
        for (int i = 0; i < 10; i++) {
            key_temp[i] = key[P10[i] - 1];
            //System.out.print(key_temp[i] + " ");
        }

        //split in half
        int Ls[] = new int[5];
        int Rs[] = new int[5];

        for (int i = 0; i < 5; i++) {
            Ls[i] = key_temp[i];
            Rs[i] = key_temp[i + 5];
        }

        //shift both sides by 1 to get K1
        int[] Ls_1 = shift(Ls,1);
        int[] Rs_1 = shift(Rs,1);

        for (int i = 0; i < 5; i++) {
            key_temp[i] = Ls_1[i];
            key_temp[i + 5] = Rs_1[i];
        }

        //use the table to get final K1
        //System.out.println("\nPrint k1: ");
        for (int i = 0; i < 8; i++) {
            key_1[i] = key_temp[P8[i] - 1];
            //System.out.print(key_1[i] + " ");
        }

        //shift both sides by 1 to get K1
        int[] Ls_2 = shift(Ls,2);
        int[] Rs_2 = shift(Rs,2);

        for (int i = 0; i < 5; i++) {
            key_temp[i] = Ls_2[i];
            key_temp[i + 5] = Rs_2[i];
        }

        //use the table to get final K2
        //System.out.println("\nPrint k2: ");
        for (int i = 0; i < 8; i++) {
            key_2[i] = key_temp[P8[i] - 1];
            //System.out.print(key_2[i] + " ");
        }
    }


    //this function is used to shift the array by the amount of number.
    int[] shift(int[] key, int n)
    {
        while (n > 0) {
            int temp = key[0];
            for (int i = 0; i < key.length - 1; i++) {
                key[i] = key[i + 1];
            }
            key[key.length - 1] = temp;
            n--;
        }
        return key;
    }


    //This is function for S-DES function fK.
    int[] function_XOR(int[] ar, int[] kye_inv)
    {
        int[] left = new int[4];
        int[] right = new int[4];

        for (int i = 0; i < 4; i++) {
            left[i] = ar[i];
            right[i] = ar[i + 4];
        }

        int[] ep = new int[8];

        for (int i = 0; i < 8; i++) {
            ep[i] = right[EP[i] - 1];
        }

        for (int i = 0; i < 8; i++) {
            ar[i] = kye_inv[i] ^ ep[i];
        }

        int[] left_1 = new int[4];
        int[] right_1 = new int[4];

        for (int i = 0; i < 4; i++) {
            left_1[i] = ar[i];
            right_1[i] = ar[i + 4];
        }

        int row, col, val;

        row = Integer.parseInt("" + left_1[0] + left_1[3], 2);
        col = Integer.parseInt("" + left_1[1] + left_1[2], 2);
        val = S0[row][col];
        String str_l = binary_exchange(val);

        row = Integer.parseInt("" + right_1[0] + right_1[3], 2);
        col = Integer.parseInt("" + right_1[1] + right_1[2], 2);
        val = S1[row][col];
        String str_r = binary_exchange(val);

        int[] right_inv = new int[4];
        for (int i = 0; i < 2; i++) {
            char c_1 = str_l.charAt(i);
            char c_2 = str_r.charAt(i);
            right_inv[i] = Character.getNumericValue(c_1);
            right_inv[i + 2] = Character.getNumericValue(c_2);
        }
        int[] right_p4 = new int[4];
        for (int i = 0; i < 4; i++) {
            right_p4[i] = right_inv[P4[i] - 1];
        }

        for (int i = 0; i < 4; i++) {
            left[i] = left[i] ^ right_p4[i];
        }

        int[] output = new int[8];
        for (int i = 0; i < 4; i++) {
            output[i] = left[i];
            output[i + 4] = right[i];
        }
        return output;
    }


    // this function swaps the nibble of size n(4)
    int[] swap(int[] array, int n)
    {
        int[] left = new int[n];
        int[] right = new int[n];

        for (int i = 0; i < n; i++) {
            left[i] = array[i];
            right[i] = array[i + n];
        }

        int[] after_swaps = new int[2 * n];
        for (int i = 0; i < n; i++) {
            after_swaps[i] = right[i];
            after_swaps[i + n] = left[i];
        }

        return after_swaps;
    }


    // decimal to binary string 0-3
    String binary_exchange(int val)
    {
        if (val == 0)
            return "00";
        else if (val == 1)
            return "01";
        else if (val == 2)
            return "10";
        else
            return "11";
    }


    //this function is used to encryption the plaintext and it is the main function
    int[] encryption(int[] plaintext)
    {
        int[] temp = new int[8];

        //System.out.println("\nPrint 1st_temp: ");
        for (int i = 0; i < 8; i++) {
            temp[i] = plaintext[IP[i] - 1];
            //System.out.print(temp[i] + " ");
        }

        int[] temp_1 = function_XOR(temp, key_1);

        int[] after_swap = swap(temp_1, temp_1.length / 2);

        int[] temp_2 = function_XOR(after_swap, key_2);

        int[] ciphertext = new int[8];

        for (int i = 0; i < 8; i++) {
            ciphertext[i] = temp_2[IP_inv[i] - 1];
        }

        return ciphertext;
    }


    int[] decryption(int[] ciphertext)
    {
        int[] temp = new int[8];

        for (int i = 0; i < 8; i++) {
            temp[i] = ciphertext[IP[i] - 1];
        }

        int[] temp_1 = function_XOR(temp, key_2);

        int[] after_swap = swap(temp_1, temp_1.length / 2);

        int[] temp_2 = function_XOR(after_swap, key_1);

        int[] decrypted = new int[8];

        for (int i = 0; i < 8; i++) {
            decrypted[i] = temp_2[IP_inv[i] - 1];
        }

        return decrypted;
    }

    public static int[] textToBinary(String input) {
        int[] binaryValues = new int[input.length() * 8];

        int index = 0;
        for (int i = 0; i < input.length(); i++) {
            char character = input.charAt(i);
            int asciiValue = (int) character; // Convert character to its ASCII value

            for (int j = 7; j >= 0; j--) {
                // Extract each bit of the ASCII value
                int bit = (asciiValue >> j) & 1;
                binaryValues[index++] = bit;
            }
        }

        return binaryValues;
    }

    public static int[][] splitBinaryIntoBlocks(int[] binaryData, int blockSize) {
        if (binaryData.length % blockSize != 0) {
            throw new IllegalArgumentException("Binary data length is not a multiple of the block size.");
        }

        int numBlocks = binaryData.length / blockSize;
        int[][] blocks = new int[numBlocks][blockSize];

        for (int i = 0; i < numBlocks; i++) {
            for (int j = 0; j < blockSize; j++) {
                blocks[i][j] = binaryData[i * blockSize + j];
            }
        }

        return blocks;
    }

    // Helper method to accept either plaintext or ciphertext
    public int[] acceptInput(String input) {
        int[] data = new int[input.length()];
        for (int i = 0; i < input.length(); i++) {
            data[i] = Integer.parseInt(String.valueOf(input.charAt(i)));
        }
        return data;
    }
   
    public static void main(String[] args) {
        String inputText = "CRYPTOGRAPHY";
        int[] binaryRepresentation = textToBinary(inputText);

        // Print the binary representation as an array of integers
        System.out.println("Actual PlainText:");
        for (int bit : binaryRepresentation) {
            System.out.print(bit);
        }
/** */
        Scanner scanner = new Scanner(System.in);
        
        // Input rawkey from the user
        System.out.print("\nEnter the rawkey (10 bits): ");
        String keyStr = scanner.nextLine();

        if (keyStr.length() == 10) {
            for (int i = 0; i < 10; i++) {
                key[i] = Integer.parseInt(String.valueOf(keyStr.charAt(i)));
            }
        } 
		else {
            System.out.println("Raw Key must be 10 bits long.");
            scanner.close();
            return;
        }

        System.out.print("Do you want to encrypt or decrypt? (e/d): ");
        String choice = scanner.nextLine();

        SDES obj = new SDES();
        obj.key_generation();
        System.out.println();        

        if (choice.equalsIgnoreCase("e")) {
            // Input plaintext from the user
            System.out.println("Your Plain Text is:");
            for (int bit : binaryRepresentation) {
                System.out.print(bit + " ");
            }

            int[] ciphertext = obj.encryption(binaryRepresentation);
            System.out.println("\nYour Cipher Text (Encrypted Text) is:");
            for (int cipBit : ciphertext) {
                System.out.print(cipBit + " ");
            }

            // Split the ciphertext into 12 separate 8-bit blocks
            int[][] binaryBlocks = splitBinaryIntoBlocks(binaryRepresentation, 8);

            System.out.println("\nCipher Text in 8 blocks:");

            for (int i = 0; i < 12; i++) {
                for (int j = 0; j < 8; j++) {
                    System.out.print(binaryBlocks[i][j] + " ");
                }
                System.out.println();
            }
        } else if (choice.equalsIgnoreCase("d")) {
            // Input ciphertext from the user
            System.out.print("Enter the ciphertext (8 bits): ");
            String inputStr = scanner.nextLine();
            int[] input = obj.acceptInput(inputStr);

            System.out.println("Your Cipher Text is:");
            for (int i = 0; i < 8; i++) {
                System.out.print(input[i] + " ");
            }

            int[] decrypted = obj.decryption(input);
            System.out.println("\nYour Decrypted Text (Plain Text) is:");
            for (int i = 0; i < 8; i++) {
                System.out.print(decrypted[i] + " ");
            }
        } else {
            System.out.println("Invalid choice. Use 'e' for encryption or 'd' for decryption.");
        }
        scanner.close();      
    }
}