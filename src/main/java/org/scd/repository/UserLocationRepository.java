package org.scd.repository;

import org.scd.model.UserLocation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.*;


public interface UserLocationRepository extends CrudRepository<UserLocation,Long> {

    UserLocation save(UserLocation userLocation);

    @Query(value="SELECT * FROM USERS_LOCATION WHERE user_id = ?1 AND date BETWEEN ?2 AND ?3 ",nativeQuery = true)
    List<UserLocation> dateQuery(final Long userId,final Date startDate,final Date endDate);

    List<UserLocation> findByUserId (Long userId );

    UserLocation getById(final Long id);

}
