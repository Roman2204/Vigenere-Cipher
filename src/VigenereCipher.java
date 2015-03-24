public class VigenereCipher {
    private char[][] tabula_recta;
    private int start_ascii_code, end_ascii_code;

    VigenereCipher(int start_ascii_code, int end_ascii_code)
    {
        this.start_ascii_code = start_ascii_code;
        this.end_ascii_code = end_ascii_code;

        TabulaRectaInit(start_ascii_code, end_ascii_code);   // Dec 97 -> ASCII 'a'; Dec 122 -> ASCII 'z'
        //TabulaRectaPrint();   // may be need for debug
    }

    private int TabulaRectaInit(int start_ascii_code, int end_ascii_code)
    {
        /*
        Dec 48 -> '0'; Dec 57 -> '9'
        Dec 65 -> 'A'; Dec 90 -> 'Z'
        Dec 97 -> ASCII 'a'; Dec 122 -> ASCII 'z'
        Dec 32 -> ASCII ' ' (MIN printable char); Dec 127 -> ASCII '~' (MAX printable char)
         */
        int tabula_recta_size_side = end_ascii_code - start_ascii_code + 1;
        if ( tabula_recta_size_side <= 1)
            return -1;
        tabula_recta = new char[tabula_recta_size_side][tabula_recta_size_side];

        /* Init first line (= real english alphabet) */
        for (int i = 0; i < tabula_recta_size_side; i ++)
        {
            tabula_recta[0][i] = (char)(i + start_ascii_code);
        }

        /* Init each other line with right offset 1 mod tabula_recta_size_side */
        for (int i = 1; i < tabula_recta_size_side; i++)
            for (int j = 0; j < tabula_recta_size_side; j++)
                tabula_recta[i][j] = tabula_recta[i - 1][(j + 1)%tabula_recta_size_side];

        return 0;
    }

    private void TabulaRectaPrint()
    {
        /*
        Print out tabula recta
        * */
        int tabula_recta_size_side = tabula_recta[0].length;
        System.out.print(" | ");
        for (int i = 0; i < tabula_recta_size_side; i++)
            System.out.print(tabula_recta[0][i] + " ");
        System.out.println();
        System.out.print("-|-");
        for (int i = 0; i < tabula_recta_size_side; i++)
            System.out.print("--");
        System.out.println();
        for (int i = 0; i < tabula_recta_size_side; i++)
        {
            System.out.print(tabula_recta[i][0] + "| ");
            for (int j = 0; j < tabula_recta_size_side; j++)
                System.out.print(tabula_recta[i][j] + " ");
            System.out.println();
        }
    }

    public String Encryption (String plain_text, String key)
    {
        /*
        Encryption plain_text by Vigenere Cipher, using key
         */
        char[] cipher_text = new char[ plain_text.length() ];

        /* Plain character + Key character MOD tabula_recta_size_side (using tabula recta) */
        for ( int i = 0, j = 0; i < plain_text.length(); i ++, j = (j + 1) % key.length() )
        {
            if ( ((int)plain_text.charAt(i) >= start_ascii_code) && ((int)plain_text.charAt(i) <= end_ascii_code) )
                cipher_text[i] = tabula_recta[(int) plain_text.charAt(i) - start_ascii_code][(int) key.charAt(j) - start_ascii_code];
            else
            {   /* For characters in plain text, which not include in range */
                cipher_text[i] = plain_text.charAt(i);
                j -= 1;
            }
        }

        return String.valueOf(cipher_text);
    }

    public static void main(String[] args)
    {
        VigenereCipher cipher = new VigenereCipher(65, 90);
        /*
        Dec 48 -> '0'; Dec 57 -> '9'
        Dec 65 -> 'A'; Dec 90 -> 'Z'
        Dec 97 -> ASCII 'a'; Dec 122 -> ASCII 'z'
        Dec 32 -> ASCII ' ' (MIN printable char); Dec 127 -> ASCII '~' (MAX printable char)
         */

        /*
        // Test set 1
        String plain_text = new String("ATTACKATDAWN");
        String key = new String("LEMON");
        // Ciphertext:	LXFOPVEFRNHR
        */
        /*
        // Test set 2
        String plain_text = new String("CRYPTO IS SHORT FOR CRYPTOGRAPHY");
        String key = new String("ABCDEF");
        // Ciphertext: CSASXT IT UKSWT GQU GWYQVRKWAQJB
        */

        // Test set 3
        String plain_text = new String("CRYPTO IS SHORT FOR CRYPTOGRAPHY");
        String key = new String("ABCD");
        // Ciphertext: CSASTP KV SIQUT GQU CSASTPIUAQJB

        System.out.println( "Исходный текст:\t\t\t" + plain_text +"\nКлюч:\t\t\t\t\t" + key + "\nЗашифрованный текст:\t" + cipher.Encryption(plain_text, key) );    // русский
        //System.out.println( "Plaintext:\t" + plain_text +"\nKey:\t\t" + key + "\nCiphertext:\t" + cipher.Encryption(plain_text, key) );    // english


    }

}
