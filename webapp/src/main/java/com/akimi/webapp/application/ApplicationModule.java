package com.akimi.webapp.application;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.akimi.modules.forest_inventory.ForestInventoryModule;

@Configuration
@Import({ForestInventoryModule.class})
@ComponentScan
public class ApplicationModule {

}
