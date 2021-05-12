package repository;

public class Fountain{
	private String html_text;
	private String plain_text;
	private String type;
	
	public Fountain(String h, String p, String t){
		html_text = h;
		plain_text = p;
		type = t;
	}
	public String getType() {
		return type;
	}
	public void display() {
		System.out.println();
		System.out.println("FOUNTAIN ELEMENT: ");
		System.out.println("Original text: " + plain_text);
		System.out.println("HTML equivalent: " + html_text);
		System.out.println("TYPE: " + type);
		System.out.println();
	}
}