package com.akimi.webapp.integtests.metamodel;

import java.io.IOException;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import org.apache.causeway.applib.services.registry.ServiceRegistry;
import org.apache.causeway.applib.services.swagger.Format;
import org.apache.causeway.applib.services.swagger.Visibility;
import org.apache.causeway.testing.integtestsupport.applib.swagger.SwaggerExporter;
import org.apache.causeway.viewer.restfulobjects.jaxrsresteasy.CausewayModuleViewerRestfulObjectsJaxrsResteasy;

import lombok.val;

import com.akimi.webapp.integtests.WebAppIntegTestAbstract;

@Import({
        CausewayModuleViewerRestfulObjectsJaxrsResteasy.class
})
@DirtiesContext
class SwaggerExport_IntegTest extends WebAppIntegTestAbstract {

    @Inject ServiceRegistry serviceRegistry;

    @Test
    void export() throws IOException {
        val swaggerExporter = new SwaggerExporter(serviceRegistry);
        swaggerExporter.export(Visibility.PRIVATE, Format.JSON);
    }
}
