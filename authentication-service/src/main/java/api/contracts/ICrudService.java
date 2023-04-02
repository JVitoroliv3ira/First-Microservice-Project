package api.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface ICrudService<E extends IEntity<T>, T, R extends JpaRepository<E, T>> {
    R getRepository();

    String getEntityNotFoundMessage();

    default E create(E entity) {
        entity.setId(null);
        return this.getRepository()
                .save(entity);
    }

    default E read(T id) {
        return this.getRepository()
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, this.getEntityNotFoundMessage()));
    }

    default E update(E entity) {
        if (Boolean.FALSE.equals(this.getRepository().existsById(entity.getId()))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, this.getEntityNotFoundMessage());
        }
        return this.getRepository().save(entity);
    }

    default void delete(T id) {
        E entity = this.getRepository()
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, this.getEntityNotFoundMessage()));
        this.getRepository().delete(entity);
    }
}
