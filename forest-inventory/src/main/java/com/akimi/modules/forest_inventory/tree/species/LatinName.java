package com.akimi.modules.forest_inventory.tree.species;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.spec.AbstractSpecification;

@Property(maxLength = LatinName.MAX_LEN, mustSatisfy = LatinName.Spec.class)
@Parameter(maxLength = LatinName.MAX_LEN, mustSatisfy = LatinName.Spec.class)
@ParameterLayout(named = "Latin Name")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface LatinName {

    int MAX_LEN = 50;

    String PROHIBITED_CHARACTERS = "&%$!";

    class Spec extends AbstractSpecification<String> {
        @Override public String satisfiesSafely(String candidate) {
            for (char prohibitedCharacter : PROHIBITED_CHARACTERS.toCharArray()) {
                if( candidate.contains("" + prohibitedCharacter)) {
                    return "Character '" + prohibitedCharacter + "' is not allowed.";
                }
            }
            return null;
        }
    }
}
