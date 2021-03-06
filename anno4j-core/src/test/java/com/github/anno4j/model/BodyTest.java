package com.github.anno4j.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openrdf.annotations.Iri;
import org.openrdf.repository.Repository;
import org.openrdf.repository.object.LangString;
import org.openrdf.repository.object.ObjectConnection;
import org.openrdf.repository.object.ObjectRepository;
import org.openrdf.repository.object.config.ObjectRepositoryFactory;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.sail.memory.MemoryStore;

import static org.junit.Assert.assertEquals;

/**
 * Created by schlegel on 06/05/15.
 */
public class BodyTest {

    Repository repository;
    ObjectConnection connection;

    @Before
    public void setUp() throws Exception {
        repository = new SailRepository(new MemoryStore());
        repository.initialize();

        ObjectRepositoryFactory factory = new ObjectRepositoryFactory();
        ObjectRepository objectRepository = factory.createRepository(repository);
        connection = objectRepository.getConnection();
    }

    @After
    public void tearDown() throws Exception {
        connection.close();
    }

    @Test
    public void testPersistAnnotation() throws Exception {
        // Create test annotation
        TestBody body = new TestBody();
        body.setValue("Example Value");

        Annotation annotation = new Annotation();
        annotation.setBody(body);


        // persist annotation
        connection.addObject(annotation);

        // query persisted object and check test body implementation
        Annotation result = connection.getObject(Annotation.class, annotation.getResource());
        assertEquals(((TestBody)annotation.getBody()).getValue(), ((TestBody) result.getBody()).getValue());
    }

    @Iri("http://www.example.com/schema#bodyType")
    public static class TestBody extends Body {

        public TestBody() {
        }

        @Iri("http://www.example.com/schema#value")
        private String value;

        @Iri("http://www.example.com/schema#langValue")
        private LangString langValue;

        @Iri("http://www.example.com/schema#doubleValue")
        private Double doubleValue;

        public Double getDoubleValue() {
            return doubleValue;
        }

        public void setDoubleValue(Double doubleValue) {
            this.doubleValue = doubleValue;
        }

        public LangString getLangValue() {
            return langValue;
        }

        public void setLangValue(LangString langValue) {
            this.langValue = langValue;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "TestBody{" +
                    "value='" + value + '\'' +
                    '}';
        }
    }

}