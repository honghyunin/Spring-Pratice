package com.User.Management.repository;

import com.User.Management.domain.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public String findByName(String name);
    public User findById(String id);
    public User findByPassword(String password);

}
