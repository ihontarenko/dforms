package df.base.common.libs.parser;

public class StringUtils {

    private static String appendBefore(int number, char character, int length) {
        return number < (int) Math.pow(10, length) - 1
                ? new String(new char[(int) (length - Math.floor(Math.log10(number)) - 1)]).replace('\0', character) + number
                : String.valueOf(number);
    }

}
