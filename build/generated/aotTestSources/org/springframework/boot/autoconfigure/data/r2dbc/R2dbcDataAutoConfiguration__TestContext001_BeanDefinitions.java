package org.springframework.boot.autoconfigure.data.r2dbc;

import io.dsub.sweatboys.opendiscogs.api.artist.domain.Artist;
import java.lang.Class;
import java.util.List;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.data.domain.ManagedTypes;
import org.springframework.data.r2dbc.convert.MappingR2dbcConverter;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.mapping.R2dbcMappingContext;
import org.springframework.data.relational.RelationalManagedTypes;
import org.springframework.r2dbc.core.DatabaseClient;

/**
 * Bean definitions for {@link R2dbcDataAutoConfiguration}
 */
public class R2dbcDataAutoConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'org.springframework.boot.autoconfigure.data.r2dbc.R2dbcDataAutoConfiguration'.
   */
  private static BeanInstanceSupplier<R2dbcDataAutoConfiguration> getRdbcDataAutoConfigurationInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<R2dbcDataAutoConfiguration>forConstructor(DatabaseClient.class)
            .withGenerator((registeredBean, args) -> new R2dbcDataAutoConfiguration(args.get(0)));
  }

  /**
   * Get the bean definition for 'r2dbcDataAutoConfiguration'
   */
  public static BeanDefinition getRdbcDataAutoConfigurationBeanDefinition() {
    Class<?> beanType = R2dbcDataAutoConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getRdbcDataAutoConfigurationInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'r2dbcEntityTemplate'.
   */
  private static BeanInstanceSupplier<R2dbcEntityTemplate> getRdbcEntityTemplateInstanceSupplier() {
    return BeanInstanceSupplier.<R2dbcEntityTemplate>forFactoryMethod(R2dbcDataAutoConfiguration.class, "r2dbcEntityTemplate", R2dbcConverter.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(R2dbcDataAutoConfiguration.class).r2dbcEntityTemplate(args.get(0)));
  }

  /**
   * Get the bean definition for 'r2dbcEntityTemplate'
   */
  public static BeanDefinition getRdbcEntityTemplateBeanDefinition() {
    Class<?> beanType = R2dbcEntityTemplate.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getRdbcEntityTemplateInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance for 'r2dbcManagedTypes'.
   */
  private static InstanceSupplier<RelationalManagedTypes> rdbcManagedTypesInstance() {
    return (registeredBean ->  {
      List<Class<?>> types = List.of(Artist.class);
      ManagedTypes managedTypes = ManagedTypes.fromIterable(types);
      return RelationalManagedTypes.from(managedTypes);
    } );
  }

  /**
   * Get the bean definition for 'r2dbcManagedTypes'
   */
  public static BeanDefinition getRdbcManagedTypesBeanDefinition() {
    Class<?> beanType = RelationalManagedTypes.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(R2dbcDataAutoConfiguration__TestContext001_BeanDefinitions.rdbcManagedTypesInstance());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'r2dbcMappingContext'.
   */
  private static BeanInstanceSupplier<R2dbcMappingContext> getRdbcMappingContextInstanceSupplier() {
    return BeanInstanceSupplier.<R2dbcMappingContext>forFactoryMethod(R2dbcDataAutoConfiguration.class, "r2dbcMappingContext", ObjectProvider.class, R2dbcCustomConversions.class, RelationalManagedTypes.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(R2dbcDataAutoConfiguration.class).r2dbcMappingContext(args.get(0), args.get(1), args.get(2)));
  }

  /**
   * Get the bean definition for 'r2dbcMappingContext'
   */
  public static BeanDefinition getRdbcMappingContextBeanDefinition() {
    Class<?> beanType = R2dbcMappingContext.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getRdbcMappingContextInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'r2dbcConverter'.
   */
  private static BeanInstanceSupplier<MappingR2dbcConverter> getRdbcConverterInstanceSupplier() {
    return BeanInstanceSupplier.<MappingR2dbcConverter>forFactoryMethod(R2dbcDataAutoConfiguration.class, "r2dbcConverter", R2dbcMappingContext.class, R2dbcCustomConversions.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(R2dbcDataAutoConfiguration.class).r2dbcConverter(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'r2dbcConverter'
   */
  public static BeanDefinition getRdbcConverterBeanDefinition() {
    Class<?> beanType = MappingR2dbcConverter.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getRdbcConverterInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'r2dbcCustomConversions'.
   */
  private static BeanInstanceSupplier<R2dbcCustomConversions> getRdbcCustomConversionsInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<R2dbcCustomConversions>forFactoryMethod(R2dbcDataAutoConfiguration.class, "r2dbcCustomConversions")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean(R2dbcDataAutoConfiguration.class).r2dbcCustomConversions());
  }

  /**
   * Get the bean definition for 'r2dbcCustomConversions'
   */
  public static BeanDefinition getRdbcCustomConversionsBeanDefinition() {
    Class<?> beanType = R2dbcCustomConversions.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getRdbcCustomConversionsInstanceSupplier());
    return beanDefinition;
  }
}
