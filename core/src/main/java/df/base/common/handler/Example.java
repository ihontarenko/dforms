package df.base.common.handler;

import df.base.common.parser.ParameterParser;
import df.base.dto.KeyValuePair;
import df.base.dto.form.FieldConfigDTO;

import java.util.ArrayList;
import java.util.List;

public class Example {

    public static void main(String[] args) {

        ParameterParser parser = new ParameterParser();

        List<KeyValuePair> entries = new ArrayList<>() {{
            add(new FieldConfigDTO() {{
                setKey("#validation:non_empty");
                setValue("(message='test!')");
            }});
            add(new FieldConfigDTO() {{
                setKey("#trigger:reminder");
                setValue("(qty=10, condition='LTE')");
            }});
        }};

        for (KeyValuePair entry : entries) {
            String handlerDefinition = entry.getKey();
            String handlerExecutionParameters = entry.getValue();

            System.out.println(
                    parser.parse(handlerExecutionParameters)
            );
        }

    }

}
