package io.startform.parent.library.boot;

import io.startform.parent.utils.AnsiColors;
import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

public class DefaultBanner implements Banner {

    private final String name;
    private final AnsiColors color;

    public DefaultBanner(String name, AnsiColors color) {
        this.name = name;
        this.color = color;
    }

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        out.print(color + AnsiColors.BLACK_BOLD.toString());
        out.println(name);
        out.println(sourceClass.getName());
        out.print(AnsiColors.RESET);
    }

}
