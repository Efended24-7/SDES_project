import java.util.Scanner;

public class DES {

    static int key[] = new int[10];
    //static int key[] = {1,0,0,0,1,0,1,1,1,0};

    int key_1[] = new int[8];
    int key_2[] = new int[8];

    static int raw_key_2[] = new int[10];
    //static int raw_key_2[] = {0,1,1,0,1,0,1,1,1,0};

    int key_3[] = new int[8];
    int key_4[] = new int[8];

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

        //.out.println("Print P10 (rawkey_gen1): ");
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
        //System.out.println("\nPrint k1 (rawkey_gen1): ");
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
        //System.out.println("\nPrint k2 (rawkey_gen1): ");
        for (int i = 0; i < 8; i++) {
            key_2[i] = key_temp[P8[i] - 1];
            //System.out.print(key_2[i] + " ");
        }
    }


    void raw_key_2_generation()
    {
        int key_temp[] = new int[10];

        //System.out.println("\nPrint P10 (rawkey_gen2): ");
        for (int i = 0; i < 10; i++) {
            key_temp[i] = raw_key_2[P10[i] - 1];
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
        //System.out.println("\nPrint k1 (rawkey_gen2): ");
        for (int i = 0; i < 8; i++) {
            key_3[i] = key_temp[P8[i] - 1];
            //System.out.print(key_3[i] + " ");
        }

        //shift both sides by 1 to get K1
        int[] Ls_2 = shift(Ls,2);
        int[] Rs_2 = shift(Rs,2);

        for (int i = 0; i < 5; i++) {
            key_temp[i] = Ls_2[i];
            key_temp[i + 5] = Rs_2[i];
        }

        //use the table to get final K2
        //System.out.println("\nPrint k2 (rawkey_gen2): ");
        for (int i = 0; i < 8; i++) {
            key_4[i] = key_temp[P8[i] - 1];
            //System.out.print(key_4[i] + " ");
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

        //System.out.println("\nPrint IP: ");
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

    int[] encryption_with_raw_key_2(int[] plaintext)
    {
        int[] temp = new int[8];

        //System.out.println("\nPrint IP: ");
        for (int i = 0; i < 8; i++) {
            temp[i] = plaintext[IP[i] - 1];
            //System.out.print(temp[i] + " ");
        }

        int[] temp_1 = function_XOR(temp, key_3);

        int[] after_swap = swap(temp_1, temp_1.length / 2);

        int[] temp_2 = function_XOR(after_swap, key_4);

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

    int[] decryption_with_raw_key_2(int[] ciphertext)
    {
        int[] temp = new int[8];

        for (int i = 0; i < 8; i++) {
            temp[i] = ciphertext[IP[i] - 1];
        }

        int[] temp_1 = function_XOR(temp, key_4);

        int[] after_swap = swap(temp_1, temp_1.length / 2);

        int[] temp_2 = function_XOR(after_swap, key_3);

        int[] decrypted = new int[8];

        for (int i = 0; i < 8; i++) {
            decrypted[i] = temp_2[IP_inv[i] - 1];
        }

        return decrypted;
    }



    // Helper method to accept user input
    public int[] acceptInput(String input) {
        int[] data = new int[input.length()];
        for (int i = 0; i < input.length(); i++) {
            data[i] = Integer.parseInt(String.valueOf(input.charAt(i)));
        }
        return data;
    }
   
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Input rawkey from the user
        System.out.print("Enter the 1st rawkey (10 bits): ");
        String first_raw_key_Str = scanner.nextLine();

        if (first_raw_key_Str.length() == 10) {
            for (int i = 0; i < 10; i++) {
                key[i] = Integer.parseInt(String.valueOf(first_raw_key_Str.charAt(i)));
            }
        } 
		else {
            System.out.println("Raw Key must be 10 bits long.");
            scanner.close();
            return;
        }

        System.out.print("Do you want to encrypt or decrypt? (e/d): ");
        String choice = scanner.nextLine();

        DES obj = new DES();
        obj.key_generation();     

        if (choice.equalsIgnoreCase("e")) {
            // Input plaintext from the user
            System.out.print("\nEnter the plaintext (8 bits): ");
            String plaintextStr = scanner.nextLine();
            int[] plaintext = obj.acceptInput(plaintextStr);

            System.out.println("\nYour Plain Text is:");
            for (int i = 0; i < 8; i++) {
                System.out.print(plaintext[i] + " ");
            }

            int[] encrypt_1 = obj.encryption(plaintext);

            System.out.print("\nEnter the 2nd rawkey (10 bits): ");
            String second_raw_key_Str = scanner.nextLine();

            if (second_raw_key_Str.length() == 10) {
                for (int i = 0; i < 10; i++) {
                    raw_key_2[i] = Integer.parseInt(String.valueOf(second_raw_key_Str.charAt(i)));
                }
            } 
            else {
                System.out.println("Raw Key must be 10 bits long.");
                scanner.close();
                return;
            }

            obj.raw_key_2_generation();

            System.out.println("\nShow 1st Dec:");
            int[] decrypted = obj.decryption_with_raw_key_2(encrypt_1);

            int[] ciphertext = obj.encryption(decrypted);
            System.out.println("\nYour Cipher Text (Encrypted Text) is:");
            for (int i = 0; i < 8; i++) {
                System.out.print(ciphertext[i] + " ");
            }
        } else if (choice.equalsIgnoreCase("d")) {
            // Input ciphertext from the user
            System.out.print("\nEnter the ciphertext (8 bits): ");
            String ciphertextStr = scanner.nextLine();
            int[] ciphertext = obj.acceptInput(ciphertextStr);

            System.out.println("\nYour Cipher Text is:");
            for (int i = 0; i < 8; i++) {
                System.out.print(ciphertext[i] + " ");
            }

            int[] decrypted_1 = obj.decryption(ciphertext);

            System.out.print("\nEnter the 2nd rawkey (10 bits): ");
            String second_raw_key_Str = scanner.nextLine();

            if (second_raw_key_Str.length() == 10) {
                for (int i = 0; i < 10; i++) {
                    raw_key_2[i] = Integer.parseInt(String.valueOf(second_raw_key_Str.charAt(i)));
                }
            } 
            else {
                System.out.println("Raw Key must be 10 bits long.");
                scanner.close();
                return;
            }

            obj.raw_key_2_generation();

            System.out.println("\nShow 1st Enc:");
            int[] encrypted = obj.encryption_with_raw_key_2(decrypted_1);

            int[] plaintext = obj.decryption(encrypted);
            System.out.println("\nYour Decrypted Text (Plain Text) is:");
            for (int i = 0; i < 8; i++) {
                System.out.print(plaintext[i] + " ");
            }
        } else {
            System.out.println("Invalid choice. Use 'e' for encryption or 'd' for decryption.");
        }
        scanner.close();   
    }
}