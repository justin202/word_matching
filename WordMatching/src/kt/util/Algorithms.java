package kt.util;

public class Algorithms {

	private final static int M = 1;
	private final static int I = -1;
	private final static int D = -1;
	private final static int R = -1;

	public static int editDistance(boolean isGlobal, String token, String dict){
		int dictLength = dict.length();
		int tokenLength = token.length();

		int[][] distance = new int[dictLength + 1][tokenLength + 1];

		// Initialize distance array
		for(int i = 0; i <= dictLength; i++) 
			distance[i][0] = isGlobal ? i * I : 0;
		for(int j = 0; j <= tokenLength; j++) 
			distance[0][j] = isGlobal ? j * D : 0;
		
		for(int i = 1; i <= dictLength; i++){
			for(int j = 1; j <= tokenLength; j++){
				distance[i][j] = getMax(distance[i][j-1] + D,
						distance[i-1][j] + I,
						distance[i-1][j-1] + (token.charAt(j - 1) == dict.charAt(i - 1) ? M : R),
						isGlobal);
			}
		}
		
		return isGlobal ? distance[dictLength][tokenLength] : getMaxInArray(distance);
	}
	
	private static int getMaxInArray(int[][] arr){
		int max = 0;
		
		for(int i = 0; i < arr.length; i++){
			for(int j = 0; j < arr[i].length; j++){
				max = max > arr[i][j] ? max : arr[i][j];
			}
		}
		
		return max;
	}
	
	private static int getMax(int a, int b, int c, boolean isGlobal){
		int maxOne = a > b ? Math.max(a, c) : Math.max(b, c);
		if(isGlobal)
			return maxOne;
		else
			return maxOne > 0 ? maxOne : 0;
	}
	
	public static int nGram(int n, String token, String dict){
		token = "#" + token + "#";
		dict = "#" + dict + "#";
		
		String[] tokenGram = stringToNParts(n, token); 
		String[] dictGram = stringToNParts(n, dict); 
		
		int count = 0;
		for(String t : tokenGram){
			for(String d : dictGram){
				if(t.equals(d)){
					count++;
					break;
				}
			}
		}
		
		return tokenGram.length + dictGram.length - 2 * count;
	}
	
	private static String[] stringToNParts(int n, String word){
		String[] ret = new String[word.length() - n + 1];
		for(int i = 0; i <= word.length() - n; i++){
			ret[i] = word.substring(i, i + n);
		}
		return ret;
	}
	
	public static int soundex(String token, String dict){
		String tokenSoundex = wordToSoundex(token);
		String dictSoundex = wordToSoundex(dict);
		
		return tokenSoundex.equals(dictSoundex) ? 0 : 1;
	}
	
	private static String wordToSoundex(String word){
		String output = "";
		word = word.toLowerCase();
		output = String.valueOf(word.charAt(0));
		
		int lastNumber = 0;
		
		for(int i = 1; i < word.length(); i++){
			switch(word.charAt(i)){
			// vowels: aehiouwy 0
			case 'a':
			case 'e':
			case 'h':
			case 'i':
			case 'o':
			case 'u':
			case 'w':
			case 'y':
				output += (0 == lastNumber ? "" : "0");
				lastNumber = 0;
				break;
			// labials: bpfv 1
			case 'b':
			case 'p':
			case 'f':
			case 'v':
				output += (1 == lastNumber ? "" : "1");
				lastNumber = 1;
				break;
			// fricatives, velars: cgjkqsxz 2
			case 'c':
			case 'g':
			case 'j':
			case 'k':
			case 'q':
			case 's':
			case 'x':
			case 'z':
				output += (2 == lastNumber ? "" : "2");
				lastNumber = 2;
				break;
			// dentals: dt 3
			case 'd':
			case 't':
				output += (3 == lastNumber ? "" : "3");
				lastNumber = 3;
				break;
			// lateral: l 4
			case 'l':
				output += (4 == lastNumber ? "" : "4");
				lastNumber = 4;
				break;
			// nasals: mn 5
			case 'm':
			case 'n':
				output += (5 == lastNumber ? "" : "5");
				lastNumber = 5;
				break;
			// rhotic: r 6
			case 'r':
				output += (6 == lastNumber ? "" : "6");
				lastNumber = 6;
				break;
			}
		}
		
		output = output.replace("0", "");
		
		output += "0000";
		
		return output.substring(0, 4);
	}

	public static void main(String[] args) {
//		System.out.println(editDistance(true, "crat", "arts"));
//		System.out.println(nGram(2, "lended", "deaden"));
//		System.out.println(wordToSoundex("kingston"));
	}

}
