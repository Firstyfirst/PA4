package readability.stategy;

public class ReadFactory {

	public static ReadStategy setReadStategy(String stategy) {
        switch(stategy) {

            case "Url" : return new UrlStategy();

            case "File" : return new FileStategy();
        }
        return null;
    }

    public static String[] getReadStategyType() {
        String[] stategy = {"File", "Url"};
        return stategy;
    }
}