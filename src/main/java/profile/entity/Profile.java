package profile.entity;

import javax.persistence.Entity;

@Entity
public class Profile {
    Long id;
    Long userId;
    Long supervisorId;
}
