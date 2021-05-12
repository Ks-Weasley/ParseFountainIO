
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.*;
import regularExpression.*;

class RegularExpressionTest{
	private static void testSceneHeading(Pattern p) {
		System.out.println("TEST SCENE HEADING: ");
		 System.out.println("INT. SCENE 1 " + p.matcher("INT. SCENE 1").find());
		 System.out.println("EXT SCENE 2 " + p.matcher("EXT SCENE 2").find());
		 System.out.println("ext. SCENE 3 " + p.matcher("ext. SCENE 3").find());
		 System.out.println("Exxxtr SCENE 4 " + p.matcher("Exxxtr SCENE 4").find());
		 System.out.println(" There in the bushes I see my heading ext scene 5 " + p.matcher(" There in the bushes I see my heading ext scene 5").find());
		 System.out.println("\\t int scene x " + p.matcher("\tint scene x").find());
		 System.out.println("int./ext. scene y " + p.matcher("int./ext. scene y").find());
		 System.out.println("y " + p.matcher("y").find());
		 System.out.println(".xyz " + p.matcher(".xyz").find());
		 System.out.println();

	}
	private static void testCharacter(Pattern p) {
		System.out.println("TEST CHARACTER: ");
		System.out.println("MARK " + p.matcher("MARK").find());
	 	System.out.println("HENRY " + p.matcher("HENRY ").find());
	 	System.out.println("  DARK  " + p.matcher("  DARK  ").find());
	 	System.out.println(" There in the bushes " + p.matcher(" There in the bushes").find());
	 	System.out.println("int./ext. scene y " + p.matcher("int./ext. scene y").find());
	 	System.out.println("y " + p.matcher("y").find());
	 	System.out.println("@MARK.HENRY " + p.matcher("@MARK.HENRY").find());
	 	System.out.println();

	}
	private static void testTransition(Pattern p) {
		System.out.println("TEST TRANSITION: ");
		System.out.println("CUT TO: " + p.matcher("CUT TO:").find());
	 	System.out.println("CUT TO: " + p.matcher("CUT TO: ").find());
	 	System.out.println("MOVE OUT: " + p.matcher("MOVE OUT:").find());
	 	System.out.println("MOVE TOOo:  " + p.matcher("MOVE TOOO: ").find());
	 	System.out.println("\\t CUT TO: " + p.matcher("\t CUT TO:").find());
	 	System.out.println();
	}
	private static void testParanthetical(Pattern p) {
		System.out.println("TEST PARANTHETICAL: ");
	 	System.out.println("(): " + p.matcher("()").find());
	 	System.out.println("(O.S) " + p.matcher("(O.S)").find());
	 	System.out.println("(This is Lord Voldemort): " + p.matcher("(This is Lord Voldemort)").find());
	 	System.out.println("\\t \t (He who must not be 1234): " + p.matcher("\t (He who must not be 1234)").find());
	 	System.out.println(" There in the bushes I see my heading ext scene 5 " + p.matcher(" There in the bushes I see my heading ext scene 5").find());
	 	System.out.println("   (  XASDFFSsenf  )   : " + p.matcher("   (  XASDFFSsenf  )   ").find());
	 	System.out.println();

	}
	private static void testSceneNumber(Pattern p) {
		 System.out.println("TEST SCENE NUMBER: ");
		 System.out.println("##" + p.matcher("##").find());
		 System.out.println("#1A#" + p.matcher("#1A#").find());
		 System.out.println("#1-I-A#" + p.matcher("#1-I-A#").find());
		 System.out.println("#1.#" + p.matcher("#1.#").find());
		 System.out.println("#1 " + p.matcher("#1").find());
		 System.out.println();
	}
	private static void testDualDialogue(Pattern p) {
		 System.out.println("TEST DUAL DIALOGUE: ");
		 System.out.println("  CHARLES " + p.matcher("CHARLES").find());
		 System.out.println("CHARLES ^ " + p.matcher("CHARLES ^").find());
		 System.out.println(" @CHARLES ^  " + p.matcher(" @CHARLES ^ ").find());
		 System.out.println();
		
	}
	private static void testLyric(Pattern p) {
		System.out.println("TEST LYRICS: ");
		System.out.println("TEST LYRIC: ");
		System.out.println("~Willy Wonka! Willy Wonka! The amazing chocolatier! " + p.matcher("~Willy Wonka! Willy Wonka! The amazing chocolatier!").find());
		System.out.println("Willy Wonka! ~Willy Wonka! The amazing chocolatier! " + p.matcher("Willy Wonka! ~Willy Wonka! The amazing chocolatier!").find());
		System.out.println("~~~Willy Wonka! Willy Wonka! The amazing chocolatier! " + p.matcher("~~~Willy Wonka! Willy Wonka! The amazing chocolatier!").find());
		System.out.println();
	}
	private static void testCenteredText(Pattern p) {
		System.out.println("TEST CENTERED TEXT: ");
		System.out.println(">  Center my text  < " + p.matcher(">  Center my text  <  ").find());
		System.out.println("Center my text >  Center my text  < Center my text " + p.matcher("Center my text >  Center my text  < Center my text").find());
		System.out.println(">  Center my text " + p.matcher(">  Center my text ").find());
		System.out.println(">  Center my text < there you go <  " + p.matcher(">  Center my text < there you go <  ").find());
		System.out.println();
	}
	private static void testBoldItalics(Pattern p) {
		System.out.println("TEST BOLD ITALICS: ");
		System.out.println("***BOLD ITALICS*** " + p.matcher("***BOLD ITALICS***").find());
		System.out.println(" XAD  ***  BOLD ITALICS   ***   XQWERTY  " + p.matcher(" XAD  ***  BOLD ITALICS   ***   XQWERTY ").find());
		System.out.println("   ***BOLD ITALICS   **   " + p.matcher("   ***BOLD ITALICS   **   ").find());
		System.out.println();
	}
	private static void testBold(Pattern p) {
		System.out.println("TEST BOLD: ");
		System.out.println("**BOLD** " + p.matcher("**BOLD**").find());
		System.out.println("SFWFGW  **  BOLD  **  XASDF " + p.matcher("SFWFGW  **  BOLD  **  XASDF").find());
		System.out.println(" **  BOLD  * " + p.matcher(" **  BOLD  * ").find());
		System.out.println();
	}
	private static void testItalics(Pattern p) {
		System.out.println("TEST ITALICS: ");
		System.out.println("*ITALICS* " + p.matcher("*ITALICS*").find());
		System.out.println(" zxcv * ITALICS;;' * dsfsd " + p.matcher(" zxcv * ITALICS;;' * dsfsd").find());
		System.out.println("ITALICS " + p.matcher("ITALICS").find());
		System.out.println();
	}
	private static void testUnderline(Pattern p) {
		System.out.println("TEST UNDERLINE");
		System.out.println("_UNDERLINE_ " + p.matcher("_UNDERLINE_").find());
		System.out.println("xasd  _  UNDERLINE  _  adafasf " + p.matcher("xasd  _  UNDERLINE  _  adafasf ").find());
		System.out.println("UNDERLINE " + p.matcher("UNDERLINE").find());
		System.out.println();
	}
	private static void testPageBreak(Pattern p) {
		System.out.println("TEST PAGE BREAK: ");
		System.out.println(" === " + p.matcher(" === ").find());
		System.out.println("zfsf === afssf " + p.matcher("zfsf === afssf").find());
		System.out.println(" == " + p.matcher(" == ").find());
		System.out.println();
	}
	private static void testBoneyard(Pattern p, String test) {
		System.out.println("TEST BONEYARD: ");
		System.out.println(test);
		System.out.println(p.matcher(test).find());
		System.out.println();
	}
	private static void testSection(Pattern p) {
		System.out.println("TEST SECTION: ");
		System.out.println("# This is a Section " + p.matcher("# This is a Section").find());
		System.out.println("## This is a Section " + p.matcher("## This is a Section").find());
		System.out.println("This is not # a Section " + p.matcher("This is not # a Section").find());
		System.out.println();
	}
	private static void testSynopses(Pattern p) {
		System.out.println("TEST SYNOPSES: ");
		System.out.println("= Set up the characters and the story. " + p.matcher("= Set up the characters and the story.").find());
		System.out.println("    = Set up the characters and the story. " + p.matcher("    = Set up the characters and the story.").find());
		System.out.println("Set up the = characters and the story. " + p.matcher("Set up the = characters and the story.").find());
		System.out.println();
	}
	
	public static void main(String args[]) throws IOException{
		
		// Print all the available regular expressions in the RegularExpression package
		for(String x: RegularExpression.regObj.keySet())
			System.out.println(x + " : " + RegularExpression.regObj.get(x));
		System.out.println();
		
		// Local copy of the regular expression map from RegularExpression package 
		final HashMap<String, Pattern> regObj = new HashMap<String, Pattern>(RegularExpression.regObj); 
		
		// Read string for comments removal 
		String test = "";
		FileReader fin = new FileReader("./media/test-comment-removal.txt");
		BufferedReader br = new BufferedReader(fin);
		int temp;
		while((temp=br.read())!=-1) {
			test+=(char)temp;
		}
		
		br.close();
		fin.close();
		
		// Testing 
		testSceneHeading(regObj.get("SCENE_HEADING"));
		testCharacter(regObj.get("CHARACTER"));
		testTransition(regObj.get("TRANSITION"));
		testParanthetical(regObj.get("PARANTHETICAL"));
		testSceneNumber(regObj.get("SCENE_NUMBER"));
		testDualDialogue(regObj.get("DUAL_DIALOGUE"));
		testLyric(regObj.get("LYRIC"));
		testCenteredText(regObj.get("CENTERED_TEXT"));
		testBoldItalics(regObj.get("BOLD_ITALICS"));
		testBold(regObj.get("BOLD"));
		testItalics(regObj.get("ITALICS"));
		testUnderline(regObj.get("UNDERLINE"));
		testPageBreak(regObj.get("PAGE_BREAK"));
		testBoneyard(regObj.get("BONEYARD"), test);
		testSection(regObj.get("SECTION"));
		testSynopses(regObj.get("SYNOPSES"));
		
	
	}
	
	
}

