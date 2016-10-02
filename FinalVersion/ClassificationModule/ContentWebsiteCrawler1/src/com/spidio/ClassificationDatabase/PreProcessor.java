package com.spidio.ClassificationDatabase;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.NoSuchElementException;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;



public class PreProcessor {
	
	// speical characters , we want to keep in the output, didn't use this attribute in this summer
	private static final String[] sp_characters = {"'","%","$","+","-","&","."};
		
	// words that we want to skip, it's better to automatically skip them by part of speech tag in the future
	// keep mine and so on, us because of their other meanings
	private static final String[] skip_words = {
			"the","a","an","and","then","but","so","yes","no","not"
			,"this","that","these","those","here","there"
			,"you","your","yours","she","her","hers","him","his","he","i","we","me"
			,"they","them","their","theirs","our","ours"
			,"what","when","where","how","why","which","while"
			,"whatever","whenever","wherever","however","whether","either","neither","too","to"
			,"can","could","should","have","has","had","must","let"
			,"would","were","was","am","is","are","be","do","done","did","does"
			,"in","on"
			,"until","til","let","with","by","about","for","if","from"
			,"tcot"};
		
	// end of sentence symbols, didn't use this attribute in this summer
	private static final String[] eos = {".","!","?","\n"};
	
	
	// username, hashtag, smiley, Url functions maybe implemented in the future if we need process those features
	public static void username(String username){
		
	}
	
	public static void hashtag(String hashtag){
		
	}
	
	public static void smiley(String smiley){
		
	}
	
	public static void Url(String url){
		
	}
	
	// clean word without third party tools
	public String clean(String word){
		
			// remove non-ascii letters
			word = word.replaceAll("[^\\x00-\\x7F]","");
			
			// extract hashtags, not remove # here because we need it in the next cleanToken function
			if (word.contains("#")){
				hashtag(word);
				word = word.replaceAll("[#]\\d*", "#");
			}
			
			// remove @username and RT@username
			if (word.startsWith("RT") || word.contains("@")){
				username(word);
				return null;
			}
			
			//remove url
			if (word.contains("http://") || word.contains("www.") || word.contains(".com")
					|| word.equals("http") || word.equals("www") || word.equals("com")){
				Url(word);
				return null;
			}
			
		
			// a sequence with same character and its length is more than 3, combine them into one character
			// didn't in use in this summer
			/*String same_char_sequence = "(.)\1\1\1+";
			
			Pattern sequencePattern = Pattern.compile(same_char_sequence);
			Matcher sequenceMatcher = sequencePattern.matcher(word);
			
			while (sequenceMatcher.find()){
				String sequence = sequenceMatcher.group(1);
				String single = sequence.substring(0,1);
				word = word.replace(sequence,single);
				
			}*/
			// statement block above not work
			
			// remove parentheses
			word = word.replaceAll("[()\\[\\]{}<>]", "");
			
			// remove all digits word
			if (word.matches("\\d*")){
				return null;
			}
			
			// remove '.* 
			String[] temp2 = word.split("['].*");
			if (temp2.length > 0){
				word = temp2[0];
			}
	
			return word;
	}
	
	
	public String cleanToken(Reader reader) throws IOException{
		
		String cleaned = "";
		// tokenizer
		PTBTokenizer ptbt = new PTBTokenizer(reader,new CoreLabelTokenFactory(),"");
			for (CoreLabel label; ptbt.hasNext();){
				label = (CoreLabel) ptbt.next();
				String word = label.toString();
					
				// skip certain words
				boolean skip = false;
				
				String[] cap_skip_words = new String[skip_words.length];
				
				for (int i = 0; i < skip_words.length; i++){
					cap_skip_words[i] = (skip_words[i].substring(0,1).toUpperCase())
							 + skip_words[i].substring(1); 
					
					if (word.equals(skip_words[i])){
						skip = true;
					}
				}
				
				for (String s : cap_skip_words){
					if (word.equals(s)){
						skip = true;
					}
				}
				
				if (skip){
					continue;
				}
				
				
				// concatenate the word and its successors if they all start with uppercase
				if (word.matches("[A-Z].*")){
					try {
				
						String successor = ((CoreLabel) ptbt.peek()).toString();
					
						while (successor.matches("[A-Z].*")){
							word += ((CoreLabel) ptbt.next()).toString();
							successor = ((CoreLabel) ptbt.peek()).toString();
						}
					}
					catch (NoSuchElementException e){
						
					}
				}
				
				// remove # in hashtag
				if (word.contains("#")){
					word = word.replaceAll("[#]\\d*", "");
				}
				
				// remove words length less than 3
				if ( word.length() < 3){
					continue;
				}
				// remove 're, n't
				if (word.length() == 3 && word.contains("'")){
					continue;
				}
				// remove all symbol word (might be smiley)
				if (!word.matches(".*[A-Za-z0-9]+.*")){
					smiley(word);
					continue;
				}
				
				cleaned += (word + " ");
				
			}
		
		return cleaned;
		
			
	}
	
	
	// clean file with stanford corenlp
	public boolean cleanToken(Reader reader, Writer writer) throws IOException{
		
		// tokenizer
		PTBTokenizer ptbt = new PTBTokenizer(reader,new CoreLabelTokenFactory(),"");
			for (CoreLabel label; ptbt.hasNext();){
				label = (CoreLabel) ptbt.next();
				String word = label.toString();
				
				// convert the upper case letter in the first word of a sentence into lower case
				/*boolean endOfSentence = false;
				for (String s : eos){
					if (word.equals(s)){
						endOfSentence = true;
					}
				}
				
				if (endOfSentence){
					word = ((CoreLabel) ptbt.next()).toString();
					if (!(word.substring(1).matches(".*[A-Z]+.*"))){
						String fl = (word.substring(0, 1)).toLowerCase();
						word.replaceFirst("[A-Z]", fl);
					}
				}*/
				// the above statement block is not working
				
				
				// skip certain words
				boolean skip = false;
				
				String[] cap_skip_words = new String[skip_words.length];
				
				for (int i = 0; i < skip_words.length; i++){
					cap_skip_words[i] = (skip_words[i].substring(0,1).toUpperCase())
							 + skip_words[i].substring(1); 
					
					if (word.equals(skip_words[i])){
						skip = true;
					}
				}
				
				for (String s : cap_skip_words){
					if (word.equals(s)){
						skip = true;
					}
				}
				
				if (skip){
					continue;
				}
				
				
				// concatenate the word and its successors if they all start with uppercase
				if (word.matches("[A-Z].*")){
					try {
				
						String successor = ((CoreLabel) ptbt.peek()).toString();
					
						while (successor.matches("[A-Z].*")){
							word += ((CoreLabel) ptbt.next()).toString();
							successor = ((CoreLabel) ptbt.peek()).toString();
						}
					}
					catch (NoSuchElementException e){
						
					}
				}
				
				// remove # in hashtag
				if (word.contains("#")){
					word = word.replaceAll("[#]\\d*", "");
				}
				
				// remove words length less than 3
				if ( word.length() < 3){
					continue;
				}
				// remove 're, n't
				if (word.length() == 3 && word.contains("'")){
					continue;
				}
				// remove all symbol word (might be smiley)
				if (!word.matches(".*[A-Za-z0-9]+.*")){
					smiley(word);
					continue;
				}
				
				writer.write(word + " ");
				
			}
		
		return true;
		
	}
	
}
