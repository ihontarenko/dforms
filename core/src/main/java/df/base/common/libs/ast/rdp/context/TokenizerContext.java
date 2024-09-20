package df.base.common.libs.ast.rdp.context;

import df.base.common.libs.ast.rdp.AbstractContext;
import df.base.common.libs.ast.rdp.TokenizerStrategyManager;

public class TokenizerContext extends AbstractContext {

    public void setTokenizerStrategyManager(TokenizerStrategyManager strategyManager) {
        setProperty(strategyManager);
    }

    public TokenizerStrategyManager getTokenizerStrategyManager() {
        return requireProperty(TokenizerStrategyManager.class);
    }

}
