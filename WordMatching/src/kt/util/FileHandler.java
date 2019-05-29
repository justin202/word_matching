package kt.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import kt.entity.LabelledToken;
import kt.entity.Token;

public class FileHandler {
	
	public static List<String> readFile(String path){
		List<String> list = new ArrayList<String>();
		
		File file = new File(path);
		BufferedReader br = null;
		try {
			 br = new BufferedReader(new FileReader(file));
			 String line;
			 while((line = br.readLine()) != null){
				 list.add(line.split("\t")[0]);
			 }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	public static List<LabelledToken> readLabelledToken(String path){
		List<LabelledToken> list = new ArrayList<LabelledToken>();

		File file = new File(path);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line;
			while((line = br.readLine()) != null){
				String[] words = line.split("\t");
				list.add(new LabelledToken(words[0], words[2]));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return list;
	}
	
	public static void writeToFile(List<Token> result, String path){
		File file = new File(path);
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(file);
			for(Token t : result)
				pw.println(t);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			pw.flush();
			pw.close();
		}
	}

}
