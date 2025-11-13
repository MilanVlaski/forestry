package com.akimi.modules.forest_inventory.tree.condition;


import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;

import jakarta.validation.constraints.Digits;

@Digits(integer = 1, fraction = 1)
@Parameter
@ParameterLayout(named = "Level")
public @interface TreeConditionLevel {

}
