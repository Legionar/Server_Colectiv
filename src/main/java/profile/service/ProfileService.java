package profile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import profile.entity.Profile;
import profile.repository.ProfileRepository;
import profile.rest.ProfileDTO;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public boolean createProfile(ProfileDTO profileDTO, Long userId, Long supervisorId) {
        Profile profile = profileRepository.saveAndFlush(createProfileFromRequest(profileDTO, userId, supervisorId));
        return profile.equals(createProfileFromRequest(profileDTO, userId, supervisorId));
    }

    private Profile createProfileFromRequest(ProfileDTO profileDTO, Long userId, Long supervisorId) {
        return new Profile();
    }

//    public Profile getProfile(Long userId, Long supervisorId, Long adminId) {
//        if (userId != null) {
//            return profileRepository.
//        }
//    }
}
