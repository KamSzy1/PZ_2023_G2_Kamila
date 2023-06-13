package validate;

public class ValidateTask {

    public static void checkTitle(String title) throws Exception {
        if (title.isEmpty()) {
            throw new Exception("Tytuł nie może być pusty");
        }
        if (title.length() > 128) {
            throw new Exception("Zbyt długi tytuł");
        }
    }

    public static void checkDescription(String description) throws Exception {
        if (description.isEmpty()) {
            throw new Exception("Opis nie może być pusty");
        }
        if (description.length() > 3000) {
            throw new Exception("Zbyt długi opis");
        }
    }

}
