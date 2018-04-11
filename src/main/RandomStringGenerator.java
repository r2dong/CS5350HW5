package main;

import java.util.Random;
import java.lang.StringBuilder;

public class RandomStringGenerator {

	static char[] charSet = 
		{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
				'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
	
	static String generate(int length) {
		Random random = new Random();
		StringBuilder builder = new StringBuilder();
		int count = 0;
		while (count < length) {
			builder.append(charSet[random.nextInt(charSet.length)]);
			count++;
		}
		return builder.toString();
	}
	
	static String mutate(String in) {
		Random random = new Random();
		StringBuilder builder = new StringBuilder();
		int inLength = in.length();
		for (int i = 0; i < inLength; i++)
			if (random.nextBoolean())
				builder.append(in.charAt(i));
			else
				builder.append(charSet[random.nextInt(charSet.length)]);
		return builder.toString();
	}
}
