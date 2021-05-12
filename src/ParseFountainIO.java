import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import errors.ParsingErrors;

import regularExpression.RegularExpression;
import repository.Fountain;

/**
 * 
 */

/**
 * @author Krithika Swaminathan
 *	A parser for screen play prototyping present at fountain.io
 */
public class ParseFountainIO {
	private final static HashMap<String, Pattern> regObj = new HashMap<String, Pattern>(RegularExpression.regObj);
	
	public static String fileRead(String file){
		String text = "";
		try{
			FileReader fin = new FileReader(file);
			BufferedReader bf = new BufferedReader(fin);
			int temp;
			while((temp=bf.read())!= -1){
				text += (char)temp;
			}
			bf.close();
			fin.close();
		}catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		return text;
	}
	
	public static void fileWrite(String file, String content){
		try{
			FileWriter fout = new FileWriter(file);
			BufferedWriter bf = new BufferedWriter(fout);
			bf.write(content);
			bf.flush();
			bf.close();
			fout.close();
		}catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
	}
	/**
	 * @param args
	 */
	private static String formatStringUsingRegex(Pattern p, String input, String openTag, String closeTag) {
		Matcher match = p.matcher(input);
		String res = "";
		int prevEnd = 0;
		while(match.find()) {
			res += input.substring(prevEnd, match.start());
			res += openTag + match.group(1) + closeTag;
			prevEnd = match.end();
		};
		res += input.substring(prevEnd);
		return res;
	}
	
	private static ArrayList<Fountain> parseFountain(String[] plain_text){
		ArrayList<Fountain> decipheredText = new ArrayList<Fountain>();
		long simultaneous_character = 0;
		String htmlString = "";
		
		for(int i=0; i<plain_text.length; i++){
			String html_text = "";
			String type_of_object = "";
			String dialogues = "";
			String character = "";
			
			try{
			
				if(regObj.get("SCENE_HEADING").matcher(plain_text[i]).find()){
					if(plain_text[i].trim().charAt(0) == '.')
						plain_text[i] = plain_text[i].substring(plain_text[i].indexOf('.')+1, plain_text[i].length());
					if(regObj.get("SCENE_NUMBER").matcher(plain_text[i]).find())
						type_of_object = "SCENE HEADING,SCENE NUMBER"; // Dunno what to do with scene number
					else 
						type_of_object = "SCENE HEADING";
					
					html_text = "<h3 class=\'scene_heading\'>" + plain_text[i] + "</h3>";
					
					// A scene heading should be followed by a newline
					if(!(i+1<plain_text.length && plain_text[i+1].isEmpty()))
						throw new ParsingErrors("Warning at "+ (i+1) +" :Scene heading should be followed by a newline;");
				}	
			
				else if(regObj.get("CHARACTER").matcher(plain_text[i]).find() || regObj.get("DUAL_DIALOGUE").matcher(plain_text[i]).find()){
					
					// Check if forced character. If so then change the @ sign at the beginning 
					if(plain_text[i].trim().charAt(0) == '@')
						plain_text[i] = plain_text[i].substring(plain_text[i].indexOf("@")+1, plain_text[i].length());
					plain_text[i] = plain_text[i].toUpperCase();
					
					// Check if dual dialogue - If so the id of all dual dialogues will be same as parent
					if(!(regObj.get("DUAL_DIALOGUE").matcher(plain_text[i]).find())){
						simultaneous_character += 1;
						type_of_object = "CHARACTER";
					}
					
					else {
						plain_text[i] = plain_text[i].substring(0, plain_text[i].lastIndexOf("^")-1);
						type_of_object = "CHARACTER,DUAL DIALOGUE";
					}
					
					// Add dialogues of the character
					character = "<p>" + plain_text[i] + "</p>";
					// ignore preceding new lines after the CHARACTER. However, give a Warning message in console.
					while((i+1)<plain_text.length && plain_text[i+1].isEmpty()){
							System.out.println("Warning at Line " + (i+1) + ": Found empty lines after a Character!");
							i++;
						}
					while((i+1)<plain_text.length && !plain_text[i+1].isEmpty()){
						dialogues += "<p>" + plain_text[i+1] + "</p>";
						i++;
					}
					
					if(dialogues.isEmpty())
						throw new ParsingErrors("Line " + i + "Found one or more white spaces after a dialogue!");
		
					html_text = "<div class = \'character" + simultaneous_character + "\' >" + character + "" + dialogues + "</div>";  
				}
				
				else if(regObj.get("CENTERED_TEXT").matcher(plain_text[i]).find()){
					type_of_object = "CENTER";
					plain_text[i] = plain_text[i].trim();
					plain_text[i] = plain_text[i].substring(1, plain_text[i].length() - 1);
					html_text = "<div style=\'text-align:center\'>" + plain_text[i] + "</div>";
				}
			
				else if(regObj.get("TRANSITION").matcher(plain_text[i]).find()){
					if(plain_text[i].trim().charAt(0) == '>')
						plain_text[i] = plain_text[i].substring(plain_text[i].indexOf(">")+1, plain_text[i].length());
					type_of_object = "TRANSITION";
					html_text = "<div class=\'transition\'>" + plain_text[i] + "</div>";
					
					// Must be preceded by and followed by a newline
					if(!(i-1<plain_text.length && i+1<plain_text.length && plain_text[i-1].isEmpty() && plain_text[i+1].isEmpty()))
						throw new ParsingErrors("Warning at Line " + (i+1) +" No proper transition defined. Expect a preceding and leading empty line");
					
				}
			
				
				else if(regObj.get("PAGE_BREAK").matcher(plain_text[i]).find()){	
					type_of_object = "PAGE BREAK";
					html_text = "<hr>";
					
				}
				else{
					// Check if preceded by character - if so dialogue
					if(plain_text[i].isEmpty()){
						html_text = "<br>";
						type_of_object = "EMPTY LINE";
					}
					else{
						html_text = "<div class=\'action\'>" + plain_text[i] + "</div>";
						type_of_object = "ACTION";
					}
				}
				
				// TO REPLACE BOLD ITALICS
				if(regObj.get("BOLD_ITALICS").matcher(plain_text[i]).find()) 
					html_text = formatStringUsingRegex(regObj.get("BOLD_ITALICS"), html_text, "<b><em>", "</em></b>");
				
				// TO REPLACE BOLD
				if(regObj.get("BOLD").matcher(plain_text[i]).find()) 
					html_text = formatStringUsingRegex(regObj.get("BOLD"), html_text, "<b>", "</b>");
				
				// TO REPLACE ITALICS
				if(regObj.get("ITALICS").matcher(plain_text[i]).find()) 
					html_text = formatStringUsingRegex(regObj.get("ITALICS"), html_text, "<em>", "</em>");
				
				// TO REPLACE UNDERLINE
				if(regObj.get("UNDERLINE").matcher(plain_text[i]).find()) 
					html_text = formatStringUsingRegex(regObj.get("UNDERLINE"), html_text, "<u>", "</u>");
			
			}catch(ParsingErrors err){
				System.out.println(err.getMessage());
			}catch(Exception err) {
				System.out.println(err.getMessage());
				System.out.println("Aborting.....");
				System.exit(0);
			}
			decipheredText.add(new Fountain(
					html_text, 
					(type_of_object.split(",")[0].equals("CHARACTER"))? character + "\n" + dialogues.replaceAll("<br>", "\n") : plain_text[i],
					type_of_object
				));
			htmlString += html_text + "\n";
		}
		
		// Converting into a HTML
		htmlString = "<body>" + htmlString + "</body>";
		String styling = getStyling(simultaneous_character);
		htmlString = "<!DOCTYPE html>\n"
				+ "<html>\n\t"
				+ "<head>\n\t\t"
				+ "<title>Parsed Information</title>\n\t\t"
				+ "<meta charset=\"UTF-8\"/>\n\t"
				+ "<style>\n\t"
				+ styling
				+ "\n\t</style>"
				+ "</head>\n\t"
				+ htmlString
				+ "</html>"
				;
		fileWrite("./media/outfile.html", htmlString);
		
		// Returning the object
		return decipheredText;
		
	}
	private static String getStyling(long simultaneous_character) {
		// TODO Auto-generated method stub
		String cssStyling = fileRead("./media/styling.css");
		return cssStyling;
	}
	public static void main(String[] args) {
		String res = "";
		try {
			res = fileRead(args[0]);
		}catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Input file not provided!\nReading default input file.....");
			res = fileRead("./media/test.txt");
		}finally {
			res = formatStringUsingRegex(regObj.get("BONEYARD"), res, "", "");
			ArrayList<Fountain> parsedInput = parseFountain(res.split("\n"));
			for(Fountain f: parsedInput)
				f.display();
		
		}
	}
}



