package df.base.utils;

import java.text.Normalizer;

public class SlugifyTransliterator {

    public static String transliterate(String text) {
        StringBuilder builder = new StringBuilder();

        for (char character : text.toCharArray()) {
            String latin = CharacterMap.getLatinEquivalent(character);
            builder.append(latin);
        }

        return builder.toString();
    }

    public static String slugify(String text) {
        String transliterated = transliterate(text);

        String normalized = Normalizer.normalize(transliterated, Normalizer.Form.NFD);
        String slug = normalized.replaceAll("\\p{M}", "");

        slug = slug.toLowerCase().replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");

        return slug;
    }

    public enum CharacterMap {
        A_UPPER('А', "A"),
        B_UPPER('Б', "B"),
        V_UPPER('В', "V"),
        H_UPPER('Г', "H"),
        G_UPPER('Ґ', "G"),
        D_UPPER('Д', "D"),
        E_UPPER('Е', "E"),
        YE_UPPER('Є', "Ye"),
        ZH_UPPER('Ж', "Zh"),
        Z_UPPER('З', "Z"),
        Y_UPPER('И', "Y"),
        I_UPPER('І', "I"),
        YI_UPPER('Ї', "Yi"),
        Y_UPPER_Y('Й', "Y"),
        K_UPPER('К', "K"),
        L_UPPER('Л', "L"),
        M_UPPER('М', "M"),
        N_UPPER('Н', "N"),
        O_UPPER('О', "O"),
        P_UPPER('П', "P"),
        R_UPPER('Р', "R"),
        S_UPPER('С', "S"),
        T_UPPER('Т', "T"),
        U_UPPER('У', "U"),
        F_UPPER('Ф', "F"),
        KH_UPPER('Х', "Kh"),
        TS_UPPER('Ц', "Ts"),
        CH_UPPER('Ч', "Ch"),
        SH_UPPER('Ш', "Sh"),
        SHCH_UPPER('Щ', "Shch"),
        YU_UPPER('Ю', "Yu"),
        YA_UPPER('Я', "Ya"),
        A_LOWER('а', "a"),
        B_LOWER('б', "b"),
        V_LOWER('в', "v"),
        H_LOWER('г', "h"),
        G_LOWER('ґ', "g"),
        D_LOWER('д', "d"),
        E_LOWER('е', "e"),
        IE_LOWER('є', "ie"),
        ZH_LOWER('ж', "zh"),
        Z_LOWER('з', "z"),
        Y_LOWER('и', "y"),
        I_LOWER('і', "i"),
        YI_LOWER('ї', "i"),
        Y_LOWER_Y('й', "i"),
        K_LOWER('к', "k"),
        L_LOWER('л', "l"),
        M_LOWER('м', "m"),
        N_LOWER('н', "n"),
        O_LOWER('о', "o"),
        P_LOWER('п', "p"),
        R_LOWER('р', "r"),
        S_LOWER('с', "s"),
        T_LOWER('т', "t"),
        U_LOWER('у', "u"),
        F_LOWER('ф', "f"),
        KH_LOWER('х', "kh"),
        TS_LOWER('ц', "ts"),
        CH_LOWER('ч', "ch"),
        SH_LOWER('ш', "sh"),
        SHCH_LOWER('щ', "shch"),
        IU_LOWER('ю', "iu"),
        IA_LOWER('я', "ia"),
        AE_UPPER('Æ', "AE"),
        OE_UPPER('Ø', "OE"),
        AA_UPPER('Å', "AA"),
        AE_LOWER('æ', "ae"),
        OE_LOWER('ø', "oe"),
        AA_LOWER('å', "aa"),
        C_CEDILLA_UPPER('Ç', "C"),
        C_CEDILLA_LOWER('ç', "c"),
        E_ACUTE_UPPER('É', "E"),
        E_ACUTE_LOWER('é', "e"),
        N_TILDE_UPPER('Ñ', "N"),
        N_TILDE_LOWER('ñ', "n"),
        U_UMLAUT_UPPER('Ü', "U"),
        U_UMLAUT_LOWER('ü', "u"),
        O_UMLAUT_UPPER('Ö', "O"),
        O_UMLAUT_LOWER('ö', "o"),
        A_UMLAUT_UPPER('Ä', "A"),
        A_UMLAUT_LOWER('ä', "a"),
        S_SHARP('ß', "ss");

        private final char cyrillic;
        private final String latin;

        CharacterMap(char cyrillic, String latin) {
            this.cyrillic = cyrillic;
            this.latin = latin;
        }

        public char getCyrillic() {
            return cyrillic;
        }

        public String getLatin() {
            return latin;
        }

        public static String getLatinEquivalent(char cyrillic) {
            for (CharacterMap mapping : values()) {
                if (mapping.getCyrillic() == cyrillic) {
                    return mapping.getLatin();
                }
            }
            return String.valueOf(cyrillic);
        }
    }


}
