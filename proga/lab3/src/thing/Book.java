package thing;

public class Book extends Thing{
    public Book(String thingName, int count) {
        super(thingName, count);
    }
    private String text;
    public String getText() {
        return text;
    }
    public void addText(String newText){
        if (newText != null) {
            this.text += newText;
        }
    }

}
