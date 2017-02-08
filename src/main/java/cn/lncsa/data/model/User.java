package cn.lncsa.data.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.Set;

/**
 * Created by catten on 16/1/15.
 */
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"profile","password"})
public class User implements IBaseModel<Integer> {

    private Integer id;
    private String name;
    private String password;

    private UserProfile profile;

    private Date registerDate;

    private Set<Role> roles;

    public User() {
        this.registerDate = new Date();
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Transient
    public String getShownName() {
        if (this.profile == null) {
            return this.getName();
        } else {
            if (profile.getNickname() != null && !profile.getNickname().trim().equals("")) {
                return profile.getNickname();
            } else {
                return this.getName();
            }
        }
    }

    @Transient
    public String getHeadPic() {
        if (this.profile == null) {
            return null;
        } else {
            if (this.profile.getHeadPic() != null) {
                return this.profile.getHeadPic();
            } else {
                return null;
            }
        }
    }

    /*
    *
    * Getter and setter
    *
    * */

    @Id
    @Column(length = 32)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotEmpty(message = "validate_username_empty")
    @Length(max = 32, min = 6, message = "validate_username_not_in_range")
    @Pattern(regexp = "^.([A-Za-z-_]|\\d){6,32}$", message = "validate_username_not_match_pattern")
    @Column(length = 32)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotEmpty(message = "validate_password_empty")
    @Length(max = 32, min = 6, message = "validate_password_not_in_range")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToOne
    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profileId) {
        this.profile = profileId;
    }

    @Temporal(TemporalType.DATE)
    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    @Column(name = "roles_id")
    @ManyToMany
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!name.equals(user.name)) return false;
        if (!password.equals(user.password)) return false;
        return registerDate.equals(user.registerDate);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + registerDate.hashCode();
        return result;
    }
}
