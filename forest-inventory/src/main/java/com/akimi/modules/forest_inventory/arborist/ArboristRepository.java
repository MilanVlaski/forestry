package com.akimi.modules.forest_inventory.arborist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.apache.causeway.extensions.secman.jpa.user.dom.ApplicationUser;

public interface ArboristRepository
        extends JpaRepository<ApplicationUser, Long> {

    @Query("SELECT u FROM ApplicationUser u JOIN u.roles r WHERE "
            + "r.name = 'arborist'")
    List<ApplicationUser> findAllArborists();

    @Query("SELECT u FROM ApplicationUser u JOIN u.roles r WHERE "
            + "r.name = 'arborist' AND u.username = :username")
    List<ApplicationUser> findAllArboristsWithUsername(
            @Param("username") String username
    );

    @Query("""
            select distinct u.username
            from ApplicationUser u
            join u.roles r
            where r.name = 'arborist'
              and (:search is null or
                   lower(u.username) like lower(concat('%', :search, '%')))
            """)
    List<String> findAllArboristUsernamesWithUsernameLike(
            @Param("search") String search
    );
}
