package df.base.common.operation;

import df.base.common.parser.ParameterParser;
import df.base.dto.KeyValuePair;
import df.base.dto.form.FieldConfigDTO;

import java.util.ArrayList;
import java.util.List;

public class Example {

    public static void main(String[] args) {

        ParameterParser parser = new ParameterParser();

        List<String> definitions = new ArrayList<>();

        definitions.add("#validation:non_empty//(message='test!')");
        definitions.add("#validation:int_size//(max=100)");
        definitions.add("#math:sum//(a=2, b=2)");
        definitions.add("#persistence:post_save//(action='LOAD', dataSource=null)");
        definitions.add("#highlight:is_true//(value=#item.getQty(), min=10)");

        OperationManager operationManager = new OperationManager();

        for (String definition : definitions) {
            String[] parts   = definition.split("//");
            String   handler = parts[0];
            String   params  = parts[1];
            operationManager.executeOperation(handler, params);
        }

    }

}
