package df.application.configuration;

import org.jmouse.core.access.ObjectAccessorWrapper;
import org.jmouse.dom.Node;
import org.jmouse.dom.meterializer.*;
import org.jmouse.dom.meterializer.hooks.NodeRuleHook;
import org.jmouse.dom.meterializer.hooks.SubmissionDecorationHook;
import org.jmouse.dom.meterializer.rules.ComplementForm;
import org.jmouse.meterializer.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.jmouse.meterializer.NodeTemplate.element;
import static org.jmouse.meterializer.NodeTemplate.text;

@Configuration
public class DomRenderingConfiguration {

    @Bean
    public Rendering<Node> domRendering() {
        ThemeAssembly<Node, DOMRenderingPipeline>   assembler = ThemeAssembly.forTheme(new BootstrapThemeModule());
        PipelineBuilder<Node, DOMRenderingPipeline> builder   = new PipelineBuilder<>();

        builder.instanceFactory(DOMRenderingPipeline::new);

        assembler.addHook(new NodeRuleHook(
                new NodeRuleSet().add(new ComplementForm("post"))
        ));
        assembler.addHook(new SubmissionDecorationHook());

        configureTemplates(assembler.templateRegistry());

        return assembler.build(new ObjectAccessorWrapper(), builder, new DOMMaterializer());
    }

    private static void configureTemplates(TemplateRegistry registry) {
        // default components
        registry.register("default.form", DefaultTemplates.defaultForm());
        registry.register("default.button.submit", DefaultTemplates.submitButton("submit"));
        // default fields
        registry.register("field.type.NONE", DefaultTemplates.composite("children"));
        registry.register("field.type.SELECT", DefaultTemplates.select("name", "description", "options", "option.optionValue", "option.optionLabel"));
        registry.register("field.type.RADIO", DefaultTemplates.radioGroup("name", "description", "options", "option.optionValue", "option.optionLabel"));
        registry.register("field.type.TEXT", DefaultTemplates.inputText("name", "label", "value"));
        registry.register("field.type.NUMBER", DefaultTemplates.inputNumber("name", "label", "value"));
        registry.register("field.type.URL", DefaultTemplates.input(InputType.URL, "name", "label", "value"));
    }

}
