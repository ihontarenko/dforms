package df.base.common.libs.ast.compiler;

import df.base.common.context.AbstractContext;

final public class CompilerContext extends AbstractContext {

    public Compiler getCompiler(Class<? extends Compiler> type) {
        return getProperty(type);
    }

    public void add(Compiler compiler) {
        setProperty(compiler);
    }

}
