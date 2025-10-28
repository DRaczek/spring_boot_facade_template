package draczek.facadetemplate.role.domain.command;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Role's facade config class.
 */
@Configuration
class RoleConfig {
  @Bean
  RoleFacade roleFacade(RoleRepository roleRepository) {
    SearchRoleUseCase searchRoleUseCase = new SearchRoleUseCase(roleRepository);
    return new RoleFacade(searchRoleUseCase);
  }
}
