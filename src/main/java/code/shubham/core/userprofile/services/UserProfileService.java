package code.shubham.core.userprofile.services;

import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.core.userprofile.dao.entities.UserProfile;
import code.shubham.core.userprofile.dao.repositories.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

	private final UserProfileRepository repository;

	@Autowired
	public UserProfileService(final UserProfileRepository repository) {
		this.repository = repository;
	}

	public UserProfile update(final String userId, final String address) {
		return this.repository.save(this.repository.findByUserId(userId).map(profile -> {
			profile.setAddress(address);
			return profile;
		}).orElse(UserProfile.builder().userId(userId).address(address).build()));
	}

	public String getAddress(final String userId) {
		return this.fetchByUserId(userId).getAddress();
	}

	public UserProfile fetchByUserId(final String userId) {
		return this.repository.findByUserId(userId)
			.orElseThrow(() -> new InvalidRequestException("userId", "No user found with id: %s", userId));
	}

}
