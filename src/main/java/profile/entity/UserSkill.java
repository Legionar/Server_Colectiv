package profile.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import login.entity.User;

import javax.persistence.*;

@Entity
@Table(name = "user_skill_technology")
@JsonDeserialize(as = UserSkill.class)
public class UserSkill implements Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;
    @ManyToOne
    @JoinColumn(name = "technology_id")
    private Technology technology;

    public UserSkill() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Technology getTechnology() {
        return technology;
    }

    public void setTechnology(Technology technology) {
        this.technology = technology;
    }
}
