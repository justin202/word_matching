package kt.wordmatching;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kt.entity.LabelledToken;
import kt.entity.Token;
import kt.util.Algorithms;
import kt.util.FileHandler;

public class WordMatching {
	
	public void getPrecision(String algorithmType, List<String> token, List<String> dict){
		Map<Integer, ArrayList<String>> map = new HashMap<Integer, ArrayList<String>>();
		List<Token> result = new ArrayList<Token>();
		
		if(algorithmType.toLowerCase().equals("ged")){
			for(String t : token){
				if(t.contains("#") || t.contains("@") || t.contains(":") || t.contains("://")){
					ArrayList<String> list = new ArrayList<String>();
					list.add(t);
					result.add(new Token(t, 0, "NO", list));
					continue;
				}else{
					for(String d : dict){
						if(d.length() != t.length())
							continue;
						int distance = Algorithms.editDistance(true, t, d);
						addToMap(distance, d, map);
					}
					addToResult(t, map, result, "max");
				}
			}
			FileHandler.writeToFile(result, "ged.txt");
		}else if(algorithmType.toLowerCase().equals("led")){
			for(String t : token){
				if(t.contains("#") || t.contains("@") || t.contains(":") || t.contains("://")){
					ArrayList<String> list = new ArrayList<String>();
					list.add(t);
					result.add(new Token(t, 0, "NO", list));
					continue;
				}else{
					for(String d : dict){
						int distance = Algorithms.editDistance(false, t, d);
						addToMap(distance, d, map);
					}
					addToResult(t, map, result, "max");
				}
			}
			FileHandler.writeToFile(result, "led.txt");
		}else if(algorithmType.toLowerCase().equals("ngram")){
			for(String t : token){
				if(t.contains("#") || t.contains("@") || t.contains(":") || t.contains("://")){
					ArrayList<String> list = new ArrayList<String>();
					list.add(t);
					result.add(new Token(t, 0, "NO", list));
					continue;
				}else{
					for(String d : dict){
						// 2-gram
						int distance = Algorithms.nGram(2, t, d);
						addToMap(distance, d, map);
					}
					addToResult(t, map, result, "min");
				}
			}
			FileHandler.writeToFile(result, "n-gram.txt");
		}else if(algorithmType.toLowerCase().equals("soundex")){
			for(String t : token){
				if(t.contains("#") || t.contains("@") || t.contains(":") || t.contains("://")){
					ArrayList<String> list = new ArrayList<String>();
					list.add(t);
					result.add(new Token(t, 0, "NO", list));
					continue;
				}else{
					for(String d : dict){
						int distance = Algorithms.soundex(t, d);
						addToMap(distance, d, map);
					}
					addToResult(t, map, result, "min");
				}
			}
			FileHandler.writeToFile(result, "soundex.txt");
		}
		
		List<LabelledToken> lTokens = FileHandler.readLabelledToken("labelled-tokens.txt");
		
		int count = 0;
		Iterator<LabelledToken> it;
		boolean isFound = false;
		for(Token t : result){
			List<String> canonicals = t.getCanonical();
			if(canonicals == null)
				continue;
			for(String tc : canonicals){
				it = lTokens.iterator();
				while(it.hasNext()){
					LabelledToken lt = it.next();
					if(t.getOriginal().equals(lt.getOriginal()) && tc.equals(lt.getCanonical())){
						isFound = true;
						lTokens.remove(lt);
						count++;
						break;
					}
					if(isFound){
						isFound = false;
						break;
					}
				}
			}
		}
		int total = 0;
		for(Token t : result){
			if(t.getCanonical() == null){
				total++;
				continue;
			}
			total += t.getCanonical().size();
		}
		double recall = ((double)count / (double)result.size());
		double precision = ((double) count / (double) total);
		System.out.println("Correct: " + count);
		System.out.println("Total number of matched: " + total);
		System.out.println("Total number of unlabelled tokens: " + result.size());
		System.out.println("------------------");
		System.out.println("Precision: " + precision);
		System.out.println("Recall: " + recall);
		System.out.println("------------------");
	}
	
	private void addToResult(String token, Map<Integer, ArrayList<String>> map, List<Token> result, String matchType){
		Set<Integer> set = map.keySet();
		Object[] o = set.toArray();
		Arrays.sort(o);
		int match = 0;
		if(o.length!= 0)
			 match = (matchType.toLowerCase() == "max") ? (int) o[o.length - 1] : (int) o[0];
		result.add(new Token(token, match, "", map.get(match)));
		map.clear();
	}
	
	private void addToMap(int distance, String dict, Map<Integer, ArrayList<String>> map){
		if(map.get(distance) == null){
			ArrayList<String> list = new ArrayList<String>();
			list.add(dict);
			map.put(distance, list);
		}else{
			map.get(distance).add(dict);
		}
	}
	
	public static void main(String[] args) {
		ArrayList<String> dict = (ArrayList<String>) FileHandler.readFile("dict.txt");
		ArrayList<String> tokens = (ArrayList<String>) FileHandler.readFile("labelled-tokens.txt");
		
		long start = System.currentTimeMillis();
		
		WordMatching wm = new WordMatching();
		wm.getPrecision("ngram", tokens, dict);
		
		long end = System.currentTimeMillis();
		System.out.println("END: " + ((end - start) / 1000) + " s.");
	}
	
}
