package draczek.facadetemplate

import draczek.facadetemplate.address.domain.command.AddressRepository
import draczek.facadetemplate.role.domain.command.RoleRepository
import draczek.facadetemplate.user.domain.command.Account
import draczek.facadetemplate.user.domain.command.AccountRepository
import draczek.facadetemplate.user.domain.command.Account_
import draczek.facadetemplate.user.domain.command.User
import draczek.facadetemplate.user.domain.command.UserRepository
import draczek.facadetemplate.user.domain.command.User_
import draczek.facadetemplate.userActionToken.domain.command.UserActionTokenHistoryRepository
import draczek.facadetemplate.userActionToken.domain.command.UserActionTokenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.Specification
import org.springframework.jdbc.core.JdbcTemplate

@SuppressWarnings("GroovyAccessibility")
class BaseCleanupSpecification extends BaseITSpecification {

    static Long[] NOT_DELETE_USER_IDS = [1, 2, 3]
    static Long[] NOT_DELETE_ACCOUNT_IDS = [1, 2]

    @Autowired
    JdbcTemplate jdbcTemplate
    @Autowired
    UserRepository userRepository
    @Autowired
    AccountRepository accountRepository
    @Autowired
    RoleRepository roleRepository
    @Autowired
    AddressRepository addressRepository
    @Autowired
    UserActionTokenRepository userActionTokenRepository
    @Autowired
    UserActionTokenHistoryRepository userActionTokenHistoryRepository

    void baseCleanup() {
        jdbcTemplate.execute("SET session_replication_role = replica;")
        userActionTokenHistoryRepository.deleteAllInBatch()
        userActionTokenRepository.deleteAllInBatch()
        addressRepository.deleteAllInBatch()
        accountRepository.deleteAllInBatch(
                accountRepository.findAll(accountToDeleteSpecification())
        )
        userRepository.deleteAllInBatch(
                userRepository.findAll(userToDeleteSpecification())
        )
        jdbcTemplate.execute("SET session_replication_role = DEFAULT;")
    }

    private static Specification<User> userToDeleteSpecification() {
        return (root, query, cb) ->
                root.get(User_.id).in(NOT_DELETE_USER_IDS).not()
    }

    private static Specification<Account> accountToDeleteSpecification() {
        return (root, query, cb) ->
                root.get(Account_.id).in(NOT_DELETE_ACCOUNT_IDS).not()
    }

    def setup() {
        baseCleanup()
    }

    def cleanup() {
        baseCleanup()
    }
}