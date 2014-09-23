/**
 * Reading Questions 4
 * 9/10/14
 * CSC320
 * @author Adam Bavosa
 */

/**
 * This program takes a user input of string of any length and creates a SimHash
 * fingerprint of 32 bits in length. First a user enters their string. Next the Java
 * hash function is applied to each word in the string. The words are awarded
 * a value based on their first letter; vowels are worth 2, consonants are worth 1.
 * Next each word has the value added or subtracted at each bit of the fingerprint,
 * subtraction for zero and addition for one. Lastly, the fingerprint bit values are
 * converted to zero or or one; zero if negative after the last step, and one if
 * positive.
 */

import java.util.*;

public class simhash
{
	public static void main (String[] args)
	{
		//Input a string to be simhashed
		System.out.println("Input a string to be simhashed\n");
		Scanner sc = new Scanner(System.in);

		String input = sc.nextLine();


		//put each word in the string into an array
		Scanner parse = new Scanner(input);
		//String can be any length, structure is dynamic
		ArrayList<String> words = new ArrayList<String>();

		int numbWords=0; //holds number of words in original string
		while(parse.hasNext())
		{
			words.add(parse.next());
			numbWords++;
		}

		//apply hash function to words and place into an array of 32bit binary strings
		String[] binaryStrings = new String[numbWords];

		for(int i=0;i<numbWords;i++)
			binaryStrings[i] = Integer.toBinaryString(words.get(i).hashCode());

		//toBinartString does not display all 32 bits if the first bits are zeros
		//apply a correction so each string has full 32 bits, even if they begin
		//with zero(s)
		for(int i=0;i<numbWords;i++)
		{
			if(binaryStrings[i].length()<32)
			{
				while(binaryStrings[i].length()!=32)
					binaryStrings[i] = ("0" + binaryStrings[i]);
			}
		}


		//Fingerprint array of 32 integers
		int[] fingerPrintInts = new int[32];


		//Create values for words and add/subtract them
		for(int i=0; i<32; i++)
		{
			for(int j=0; j<numbWords; j++)
			{
				switch(words.get(j).charAt(0))
				{
					//words that start with vowel worth 2
					case 'a':
					case 'e':
					case 'i':
					case 'o':
					case 'u':
					case 'y': 
					if(binaryStrings[j].charAt(i)=='1')
						fingerPrintInts[i] += 2;
					else
						fingerPrintInts[i] -= 2;
					break;
					//words that start with consonant, worth 1
					default: 
					if(binaryStrings[j].charAt(i)=='1')
						fingerPrintInts[i] += 1;
					else
						fingerPrintInts[i] -= 1;
				}
			}
		}


		//convert fingerprint + and - decimal numbers to binary
		for(int i=0;i<32;i++)
		{
			if(fingerPrintInts[i]<1)
				fingerPrintInts[i]=0;
			else
				fingerPrintInts[i]=1;
		}


		//Print out the resulting fingerprint
		System.out.println("SimHash fingerprint for your string:");
		for (int i=0;i<32;i++)
		{
			System.out.print(fingerPrintInts[i]);
		}
		System.out.println("\n");
	}
}