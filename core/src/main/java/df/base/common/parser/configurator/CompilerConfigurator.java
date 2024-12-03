package df.base.common.parser.configurator;

import df.base.common.libs.ast.compiler.CompilerContext;
import df.base.common.libs.ast.configurer.Configurator;

public class CompilerConfigurator implements Configurator<CompilerContext> {

    @Override
    public void configure(CompilerContext ctx) {
        ctx.add();
    }

}
