package regularExpression;
import java.util.*;
import java.util.regex.Pattern;

public class RegularExpression{
	
	public static final Map<String, Pattern> regObj= new HashMap<String, Pattern>();
	static {
			regObj.put("SCENE_HEADING", get_scene_heading_REG_EX());
			regObj.put("CHARACTER", get_character_REG_EX());
			regObj.put("TRANSITION", get_transition_REG_EX()); 
			regObj.put("PARANTHETICAL", get_paranthetical_REG_EX()); 
			regObj.put("SCENE_NUMBER" , Pattern.compile("#[A-Za-z0-9-.]+#"));
			regObj.put("DUAL_DIALOGUE", get_dual_dialogue_REG_EX());
			regObj.put("LYRIC", Pattern.compile("^(\\s*)~"));
			regObj.put("CENTERED_TEXT", Pattern.compile("^>(.*)<$")); // Must be run before Transtion to avoid false negatives
			regObj.put("BOLD_ITALICS", Pattern.compile("\\*\\*\\*(.*?)\\*\\*\\*"));
			regObj.put("BOLD", Pattern.compile("\\*\\*(.*?)\\*\\*"));
			regObj.put("ITALICS", Pattern.compile("\\*(.*?)\\*")); // Caveat : While typing *a*b* => italics(a*b)
			regObj.put("UNDERLINE", Pattern.compile("_(.*?)_")); // This can come anywhere
			regObj.put("PAGE_BREAK", Pattern.compile("^\\s*===+\\s*$")) ;
			regObj.put("BONEYARD", Pattern.compile("/\\*([^\\*]|[\\r\\n]|(\\*+([^\\*/]|[\\r\\n])))*\\*+/"));
			regObj.put("SECTION", Pattern.compile("^\\s*#"));
			regObj.put("SYNOPSES", Pattern.compile("^\\s*="));
	}
	
	private static Pattern get_character_REG_EX(){
	
		// [^A-Za-z0-9\s]* => Will generate all special characters
		String special_characters = "\\s\\S";

		// characters
		String characters = "["+ special_characters + "]*";
		characters += "[A-Za-z]" + characters;

		// CHARACTER REGULAR EXPRESSION
		String capital_characters = "[A-Z0-9\\s]*";
		
		// no special characters are allowed in character; I wanted precede the character by @
		capital_characters += "[A-Z]" + capital_characters; 
		
		String forced_characters = "\\s*@["+ characters +"]*";
		String character_extensions = "(" + capital_characters + ")\\s*(\\([" + characters + "]*\\))\\s*";

		String fountain_characters = "(^" + capital_characters + "$|^" + character_extensions + "$|^" + forced_characters + "$)";
		
		return Pattern.compile(fountain_characters);
	}
	
	private static Pattern get_dual_dialogue_REG_EX(){
		// [^[A-Za-z0-9\s]*] => Will generate all special characters
		String special_characters = "\\s\\S";

		// characters
		String characters = "["+ special_characters + "]*";
		characters += "[A-Za-z]" + characters;

		// CHARACTER REGULAR EXPRESSION
		String capital_characters = "[A-Z0-9\\s]*";
		
		// no special characters are allowed in character; I wanted precede the character by @
		capital_characters += "[A-Z]" + capital_characters; 
		
		String forced_characters = "\\s*@["+ characters +"]*";
		String character_extensions = "(" + capital_characters + ")\\s*(\\([" + characters + "]*\\))\\s*";

		String fountian_dual_dialogue = "(^" + capital_characters + "\\s+\\^\\s*$|^" + character_extensions + "\\s+\\^\\s*$|^" + forced_characters + "\\s+\\^\\s*$)";
		
		return Pattern.compile(fountian_dual_dialogue);
	}
	
	private static Pattern get_paranthetical_REG_EX(){
		// special characters
		String special_characters = "\\s\\S";

		// characters
		String characters = "[A-Za-z0-9" + special_characters + "]*";
		characters += "[A-Za-z]" + characters;

		// PARANTHETICAL REGULAR EXPRESSION
		String fountian_paranthetical = "^["+special_characters+"]*\\((" + characters + ")*\\)[" + special_characters + "]*$";
		
		return Pattern.compile(fountian_paranthetical);
	}

	private static String generate_scene_start_string(){
		String res = "";
		String[] scene_start_temp = new String[] {
			"int",
			"ext",
			"est",
			"int./ext",
			"int/ext",
			"i/e"
		};
		
		for(int i=0; i< scene_start_temp.length; i++){
		    	var temp = scene_start_temp[i].toUpperCase();
			res += scene_start_temp[i];  //int
			res += "\\s|";  
			res += temp;  //INT
			res += "\\s|";
			res += scene_start_temp[i] + '.'; //int.
			res += "\\s|" ;
			res += temp + '.';  //INT.
			res += "\\s|";
		}
		res = res.substring(0, res.length()-1);
		return res;
	}

	private static Pattern get_scene_heading_REG_EX(){
		// generate scene_start_string
		String scene_start = generate_scene_start_string();

		// special characters
		String new_line = "\\n";
		String special_characters = "\\s\\S";

		// characters
		String characters = "[" + special_characters + "]*";
		characters += "[A-Za-z]" + characters;

		// SCENE HEADING
		String fountain_scene_heading = "^(\\s*" + scene_start + ")(" + characters + ")*(" + new_line + ")*$|^[.][^.][\\s\\S]*";

		return Pattern.compile(fountain_scene_heading);
	}
	
	private static Pattern get_transition_REG_EX(){
		// special characters
		String special_characters = "\\s\\S";

		// characters
		String characters = "[A-Za-z0-9" + special_characters + "]*";
		characters += "[A-Za-z]" + characters;

		// TRANSITION
		String fountian_transition = "([A-Z\\s]*(TO:)[\\s]*$)|(^>[" + characters + "]*$)";

		return Pattern.compile(fountian_transition);
	}

}
