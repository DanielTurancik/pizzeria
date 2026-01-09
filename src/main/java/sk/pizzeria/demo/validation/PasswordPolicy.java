package sk.pizzeria.demo.validation;

public final class PasswordPolicy {

    private PasswordPolicy() {}

    //10 chars, 1 upper, 1 lower, 1 digit, 1 special, no spaces
    public static boolean isStrong(String raw) {
        if (raw == null) return false;
        if (raw.length() < 10) return false;
        if (raw.chars().anyMatch(Character::isWhitespace)) return false;

        boolean upper = raw.chars().anyMatch(Character::isUpperCase);
        boolean lower = raw.chars().anyMatch(Character::isLowerCase);
        boolean digit = raw.chars().anyMatch(Character::isDigit);
        boolean special = raw.chars().anyMatch(ch -> !Character.isLetterOrDigit(ch));

        return upper && lower && digit && special;
    }

    public static String hint() {
        return "Heslo musí obsahovať aspoň 10 znakov, 1 veľké písmeno, malé písmeno, číslo a špecialny znak (bez medzier)";
    }
}