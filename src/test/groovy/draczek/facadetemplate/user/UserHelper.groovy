package draczek.facadetemplate.user

import draczek.facadetemplate.user.domain.command.User
import draczek.facadetemplate.user.domain.command.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@SuppressWarnings("GroovyAccessibility")
class UserHelper {
    @Autowired
    UserRepository userRepository

    User fetchUser(String username){
        return userRepository.findOneByUsername(username).get()
    }
}
