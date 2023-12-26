package code.shubham.craft.driver.cab.services;

import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.commons.utils.Utils;
import code.shubham.craft.driver.cabmodels.RegisterCabRequest;
import code.shubham.craft.driver.cab.dao.entities.Cab;
import code.shubham.craft.driver.cab.dao.repositories.CabRepository;
import code.shubham.craft.driver.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CabService {

	private final CabRepository repository;

	@Autowired
	public CabService(final CabRepository repository) {
		this.repository = repository;
	}

	public Cab add(final RegisterCabRequest request) {
		final Optional<Cab> cab = this.repository.findByRegistrationNumber(request.getRegistrationNumber());
		if (cab.isPresent())
			throw new InvalidRequestException("vehicleRegistrationNumber",
					"Cab with registration number %s already exists", request.getRegistrationNumber());

		return this.repository.save(Cab.builder()
			.registrationNumber(request.getRegistrationNumber())
			.color(request.getColor())
			.driverId(request.getDriverId())
			.userId(request.getUserId())
			.build());
	}

	public List<Cab> getCabsByDriverIdAndUserId(final String driverId, final String userId) {
		return this.repository.findAllByDriverIdAndUserId(driverId, userId);
	}

	public Optional<Cab> fetch(final String driverId, final String userId, final String registrationNumber) {
		return this.repository.findAllByDriverIdAndUserIdAndRegistrationNumber(driverId, userId, registrationNumber);
	}

	public Optional<Cab> fetchByRegistrationNumber(final String registrationNumber) {
		return this.repository.findByRegistrationNumber(registrationNumber);
	}

}
