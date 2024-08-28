package df.base.internal.confio;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

import static java.util.Objects.requireNonNull;

public class ConfioParser {

    private ConfioConfig config;

    public ConfioParser(ConfioConfig config) {
        this.config = config;
    }

    public void parseConfigFile(String filePath) throws IOException, URISyntaxException {
        // Читаємо всі рядки з файлу
        List<String> lines = Files.readAllLines(Paths.get(requireNonNull(getClass().getResource(filePath)).toURI()));
        Map<String, Object> currentSection = config.getSection("root");
        if (currentSection == null) {
            currentSection = new HashMap<>();
            config.putSection("root", currentSection);
        }
//        Deque<Map<String, Object>> sectionStack = new ArrayDeque<>(); // Стек для секцій
//        sectionStack.push(currentSection);

        // Регулярні вирази для розпізнавання секцій, списків, множин та ключ-значення пар
        Pattern sectionPattern = Pattern.compile("\\[(.+)\\]");
        Pattern listPattern = Pattern.compile("- (.+)");
        Pattern setPattern = Pattern.compile("\\{(.+)\\}");
        Pattern keyValuePattern = Pattern.compile("([^:]+): (.+)");
        String currentContext = "root";

        for (String line : lines) {
            line = line.trim(); // Видаляємо зайві пробіли
            if (line.isEmpty() || line.startsWith("#")) {
                continue; // Пропускаємо порожні рядки та коментарі
            }

            // Обробка секцій
            Matcher sectionMatcher = sectionPattern.matcher(line);
            if (sectionMatcher.matches()) {
                currentContext = sectionMatcher.group(1);
                Map<String, Object> section = config.getSection(currentContext);
                if (section == null) {
                    section = new HashMap<>();
                    config.putSection(currentContext, section);
                }
                currentSection = section;
//                sectionStack.push(currentSection);
                continue;
            }

            // Обробка пар ключ-значення
            Matcher keyValueMatcher = keyValuePattern.matcher(line);
            if (keyValueMatcher.matches()) {
                String key = keyValueMatcher.group(1).trim();
                String value = keyValueMatcher.group(2).trim();

                // Обробка булевих та числових значень
                if (value.equals("true") || value.equals("false")) {
                    currentSection.put(key, Boolean.parseBoolean(value));
                } else {
                    try {
                        currentSection.put(key, Integer.parseInt(value));
                    } catch (NumberFormatException e1) {
                        try {
                            currentSection.put(key, Double.parseDouble(value));
                        } catch (NumberFormatException e2) {
                            currentSection.put(key, resolveVariables(value));
                        }
                    }
                }
                continue;
            }

            // Обробка списків
            Matcher listMatcher = listPattern.matcher(line);
            if (listMatcher.matches()) {
                String item = listMatcher.group(1).trim();
                List<String> list = (List<String>) currentSection.computeIfAbsent("list", k -> new ArrayList<>());
                list.add(resolveVariables(item));
                continue;
            }

            // Обробка множин
            Matcher setMatcher = setPattern.matcher(line);
            if (setMatcher.matches()) {
                String[] items = setMatcher.group(1).split(",");
                Set<String> set = new HashSet<>();
                for (String item : items) {
                    set.add(resolveVariables(item.trim()));
                }
                currentSection.put("set", set);
                continue;
            }

            // Обробка включень інших файлів
            if (line.startsWith("include: ")) {
                String includeFile = line.substring(9).trim();
                parseConfigFile(includeFile);
                continue;
            }
        }
    }

    // Метод для розпізнавання та заміни змінних у значеннях
    private String resolveVariables(String value) {
        Pattern variablePattern = Pattern.compile("\\$\\{(.+?)\\}");
        Matcher matcher = variablePattern.matcher(value);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String variable = matcher.group(1);
            Object variableValue = config.getValue(new ConfigContext("root"), variable);
            if (variableValue != null) {
                matcher.appendReplacement(sb, variableValue.toString());
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        ConfioConfig config = new ConfioConfig();
        ConfioParser parser = new ConfioParser(config);

        parser.parseConfigFile("/default.confio");

        System.out.println(config);
    }

}