package ar.franciscoruiz.accounts.auth.infrastructure.persistence;

import ar.franciscoruiz.accounts.auth.domain.AuthRepository;
import ar.franciscoruiz.shared.domain.Service;
import ar.franciscoruiz.shared.domain.auth.AuthPassword;
import ar.franciscoruiz.shared.domain.auth.AuthUser;
import ar.franciscoruiz.shared.domain.auth.AuthUsername;
import ar.franciscoruiz.shared.domain.auth.Authorities;
import ar.franciscoruiz.shared.infrastructure.hibernate.HibernateRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Optional;

@Service
public final class HibernateAuthRepository extends HibernateRepository<AuthUser> implements AuthRepository {
    public HibernateAuthRepository(@Qualifier("accounts-session_factory") SessionFactory sessionFactory) {
        super(sessionFactory, AuthUser.class);
    }

    @Override
    public Optional<AuthUser> search(AuthUsername username) {
        Session session = sessionFactory.openSession();
        String  sql     = String.format("SELECT username, password, role_id FROM users WHERE username='%s'", username.value());
        Query   query   = session.createNativeQuery(sql);

        List<Object[]> result = query.getResultList();

        String sqlRole   = String.format("SELECT name FROM roles WHERE id='%s'", result.get(0)[2]);
        Query  queryRole = session.createNativeQuery(sqlRole);

        List<String> resultRoles = queryRole.getResultList();

        session.close();

        return Optional.of(new AuthUser(
            new AuthUsername((String) result.get(0)[0]),
            new AuthPassword((String) result.get(0)[1]),
            new Authorities(resultRoles)
        ));
    }
}
