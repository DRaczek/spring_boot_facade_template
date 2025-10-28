package draczek.facadetemplate.configuration;

import java.util.List;
import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * specification-arg-resolver dependency config.
 */
@Configuration
class SearchConfiguration implements WebMvcConfigurer {

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    //custom way to resolve request arguments
    argumentResolvers.add(new SpecificationArgumentResolver());
  }
}