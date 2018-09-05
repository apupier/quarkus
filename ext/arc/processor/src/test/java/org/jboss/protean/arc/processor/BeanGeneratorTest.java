package org.jboss.protean.arc.processor;

import static org.jboss.protean.arc.processor.Basics.index;

import java.io.IOException;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

import org.jboss.jandex.Index;
import org.jboss.protean.arc.processor.AnnotationLiteralProcessor;
import org.jboss.protean.arc.processor.BeanDeployment;
import org.jboss.protean.arc.processor.BeanGenerator;
import org.jboss.protean.arc.processor.BeanProcessor;
import org.jboss.protean.arc.processor.types.Foo;
import org.jboss.protean.arc.processor.types.FooQualifier;
import org.junit.Test;

public class BeanGeneratorTest {

    @Test
    public void testGenerator() throws IOException {

        Index index = index(Foo.class, FooQualifier.class, AbstractList.class, AbstractCollection.class, Collection.class, List.class, Iterable.class);
        BeanDeployment deployment = new BeanDeployment(index, null);
        deployment.init();

        BeanGenerator generator = new BeanGenerator();

        deployment.getBeans().forEach(bean -> generator.generate(bean, new AnnotationLiteralProcessor(BeanProcessor.DEFAULT_NAME, true)));
        // TODO test generated bytecode
    }

    @Test
    public void testGeneratorForNormalScopedProducer() throws IOException {

        Index index = index(Producer.class, Collection.class, List.class, Iterable.class);
        BeanDeployment deployment = new BeanDeployment(index, null);
        deployment.init();

        BeanGenerator generator = new BeanGenerator();

        deployment.getBeans().forEach(bean -> generator.generate(bean, new AnnotationLiteralProcessor(BeanProcessor.DEFAULT_NAME, true)));
        // TODO test generated bytecode
    }

    @Dependent
    static class Producer {

        @ApplicationScoped
        @Produces
        List<String> list;

    }

}