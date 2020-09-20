package app.web.pavelk.shop3.repo;


import app.web.pavelk.shop3.entity.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {
    <T> T findByPhone(String phone, Class<T> type);
    boolean existsByPhone(String phone);

    void deleteByPhone(String phone);
}
