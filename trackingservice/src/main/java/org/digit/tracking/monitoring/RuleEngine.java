package org.digit.tracking.monitoring;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RuleEngine {
    Rules rules = new Rules();

    private final String LOAD_METHOD = "loadModel";
    private final String RULE_METHOD_PREFIX = "rule";

    public void executeSingleRuleMethod(String method){

        Class<?> rulesClass = rules.getClass();
        try {
            Method loadModel = rulesClass.getDeclaredMethod(method);
            loadModel.invoke(rules);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    //Dynamically execute all rule methods
    public void executeAllRules(){
        //Step 1 - Execute the load method so that data model in Rules is populated
        this.executeSingleRuleMethod(LOAD_METHOD);

        //Step 2 - Iterate through the rule methods and execute them
        Class<?> rulesClass = rules.getClass();
        try {
            Method[] methods = rulesClass.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().startsWith(RULE_METHOD_PREFIX))
                    method.invoke(rules);
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
