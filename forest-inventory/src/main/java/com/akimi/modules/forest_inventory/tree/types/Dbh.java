package com.akimi.modules.forest_inventory.tree.types;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.annotation.Property;

import jakarta.validation.constraints.Digits;

@Digits(integer = 5, fraction = 2)
@ParameterLayout(named = "DBH in cm", describedAs = "Diameter at breast height")
@Parameter
@Property
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER,
        ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Dbh {
}
