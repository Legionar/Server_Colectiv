package profile.rest;

import java.sql.Blob;

public class ProfileDTO {
    private Long userID;
    private Long supervisorId;
    private Blob profilePicture;

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(Long supervisorId) {
        this.supervisorId = supervisorId;
    }

    public Blob getProfilePicture() {
        return profilePicture;
    }

    public ProfileDTO() {
    }

    public void setProfilePicture(Blob profilePicture) {
        this.profilePicture = profilePicture;
    }
}
