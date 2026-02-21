package df.application.persistence.entity.form;

import df.application.persistence.entity.support.ElementType;
import org.jmouse.core.access.ObjectAccessorWrapper;
import org.jmouse.core.access.descriptor.structured.DescriptorResolver;
import org.jmouse.dom.Node;
import org.jmouse.dom.NodeContext;
import org.jmouse.dom.meterializer.BootstrapTemplates;
import org.jmouse.dom.meterializer.BootstrapThemeModule;
import org.jmouse.dom.meterializer.DOMMaterializer;
import org.jmouse.dom.meterializer.DOMRenderingPipeline;
import org.jmouse.meterializer.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Demo {

    public static void main(String... arguments) {
        ThemeAssembly<Node, DOMRenderingPipeline>   assembler = ThemeAssembly.forTheme(new BootstrapThemeModule());
        PipelineBuilder<Node, DOMRenderingPipeline> builder   = new PipelineBuilder<>();

        builder.instanceFactory(DOMRenderingPipeline::new);
        configureTemplates(assembler.templateRegistry());

        DOMRenderingPipeline pipeline = assembler.build(new ObjectAccessorWrapper(), builder, new DOMMaterializer());

        Node node = pipeline.render(ModelReference.of(
                "df/form", getForm()
        ));

        node.execute(NodeContext.CORRECT_NODE_DEPTH);

        String html = node.interpret(NodeContext.defaults());

        DescriptorResolver.describe(FieldOption.class);

        System.out.println(html);
    }

    private static void configureTemplates(TemplateRegistry registry) {
        registry.register("df/form", Templates.defaultForm());
        registry.register("default/button/submit", BootstrapTemplates.submitButton("submit"));

        registry.register("field.type.SELECT", BootstrapTemplates.select("name", "description", "options", "option.optionValue", "option.optionLabel"));
        registry.register("field.type.TEXT", BootstrapTemplates.inputText("name", "description", "value"));
    }

    public static Form getForm() {
        Form form = new Form();

        form.setDescription("Add Resistor!");
        form.setId("resistor_1");
        form.setName("add_resistor");

        Field title = new Field();
        title.setElementType(ElementType.TEXT);
        title.setName("title");
        title.setDescription("Name Of Resistor");
        title.setId("title_1");

        form.addField(title);

        Field vendor = new Field();

        vendor.setElementType(ElementType.SELECT);
        vendor.setId("vendor_1");
        vendor.setName("vendor");
        vendor.setDescription("Vendor: ");

        Set<FieldOption> options = new HashSet<>();

        Map<String, String> vendors = new HashMap<>();

        vendors.put("ti",       "Texas Instruments");
        vendors.put("vishay",   "Vishay");
        vendors.put("nxp",      "NXP Semiconductors");
        vendors.put("stm",      "STMicroelectronics");
        vendors.put("onsemi",  "onsemi");
        vendors.put("infineon","Infineon Technologies");
        vendors.put("microchip","Microchip Technology");
        vendors.put("analog",  "Analog Devices");
        vendors.put("renesas", "Renesas Electronics");
        vendors.put("rohm",    "ROHM Semiconductor");

        for (Map.Entry<String, String> e : vendors.entrySet()) {
            FieldOption option = new FieldOption();
            option.setId("option_" + e.getKey());
            option.setOptionLabel(e.getValue());
            option.setOptionValue(e.getKey());
            options.add(option);
        }

        vendor.setOptions(options);

        form.addField(vendor);

        return form;
    }

}
