package org.digit.tracking.monitoring;

import org.openapitools.model.TripProgress;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.digit.tracking.util.Constants.RULE_LOAD_METHOD;
import static org.digit.tracking.util.Constants.RULE_METHOD_PREFIX;

public class RuleEngine {
    Rules rules = new Rules();


    public void executeSingleRuleMethod(String method, TripProgress tripProgress){

        Class<?> rulesClass = rules.getClass();
        try {
            Method loadModel = rulesClass.getDeclaredMethod(method, TripProgress.class);
            loadModel.invoke(rules, tripProgress);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    //Dynamically execute all rule methods
    public void executeAllRules(TripProgress tripProgress){
        //Step 1 - Execute the load method so that data model in Rules is populated
        this.executeSingleRuleMethod(RULE_LOAD_METHOD, tripProgress);

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
