package net.ftxf.dapps.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import net.ftxf.dapps.entities.User;

public interface UserRepository extends CrudRepository<User, Long>{
	Optional<User> findByEmail(String email);
}
