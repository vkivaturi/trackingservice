package org.digit.tracking.monitoring;

import org.junit.jupiter.api.Test;

public class RulesEngineTest {

    @Test
    public void testLoadData(){
        RuleEngine re = new RuleEngine();
        re.executeSingleRuleMethod("loadModel");
    }

    @Test
    public void testLoadDataAndRules(){
        RuleEngine re = new RuleEngine();
        re.executeAllRules();
    }

}
