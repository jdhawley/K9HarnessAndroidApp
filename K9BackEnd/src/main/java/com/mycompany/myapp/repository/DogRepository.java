package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Dog;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Dog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {

    @Query("select dog from Dog dog where dog.owns.login = ?#{principal.username}")
    List<Dog> findByOwnsIsCurrentUser();

    Dog findOneByName(String name);

}
