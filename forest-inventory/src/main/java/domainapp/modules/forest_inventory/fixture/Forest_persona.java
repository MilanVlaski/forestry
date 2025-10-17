package domainapp.modules.forest_inventory.fixture;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;

import org.apache.causeway.applib.services.clock.ClockService;
import org.apache.causeway.applib.services.registry.ServiceRegistry;
import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.testing.fakedata.applib.services.FakeDataService;
import org.apache.causeway.testing.fixtures.applib.personas.BuilderScriptWithResult;
import org.apache.causeway.testing.fixtures.applib.personas.Persona;
import org.apache.causeway.testing.fixtures.applib.setup.PersonaEnumPersistAll;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.val;
import lombok.experimental.Accessors;

import domainapp.modules.forest_inventory.forest.Forest;
import domainapp.modules.forest_inventory.forest.Forests;
import jakarta.inject.Inject;

@RequiredArgsConstructor
public enum Forest_persona implements Persona<Forest, Forest_persona.Builder> {

    FOO("Foo", "Foo.pdf"),
    BAR("Bar", "Bar.pdf"),
    BAZ("Baz", null),
    FRODO("Frodo", "Frodo.pdf"),
    FROYO("Froyo", null),
    FIZZ("Fizz", "Fizz.pdf"),
    BIP("Bip", null),
    BOP("Bop", null),
    BANG("Bang", "Bang.pdf"),
    BOO("Boo", null);

    private final String name;
    private final String contentFileName;

    @Override
    public Builder builder() {
        return new Builder().setPersona(this);
    }

    @Override
    public Forest findUsing(final ServiceRegistry serviceRegistry) {
        return serviceRegistry.lookupService(Forests.class).map(x -> x.findByNameExact(name)).orElseThrow();
    }

    @Accessors(chain = true)
    public static class Builder extends BuilderScriptWithResult<Forest> {

        @Getter @Setter private Forest_persona persona;

        @Override
        protected Forest buildResult(final ExecutionContext ec) {

            val simpleObject = wrap(forests).create(persona.name);

            if (persona.contentFileName != null) {
                val bytes = toBytes(persona.contentFileName);
                val attachment = new Blob(persona.contentFileName, "application/pdf", bytes);
                simpleObject.updateAttachment(attachment);
            }

            return simpleObject;
        }

        @SneakyThrows
        private byte[] toBytes(String fileName){
            InputStream inputStream = new ClassPathResource(fileName, getClass()).getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[16384];

            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            return buffer.toByteArray();
        }

        // -- DEPENDENCIES

        @Inject Forests forests;
        @Inject ClockService clockService;
        @Inject FakeDataService fakeDataService;
    }

    public static class PersistAll
            extends PersonaEnumPersistAll<Forest, Forest_persona, Builder> {
        public PersistAll() {
            super(Forest_persona.class);
        }
    }


}
