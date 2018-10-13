package com.heyi.webapp.nettyserver.repository;

import com.heyi.webapp.nettyserver.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    User findById();

}
