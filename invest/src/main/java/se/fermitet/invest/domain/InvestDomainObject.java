package se.fermitet.invest.domain;

import java.util.UUID;

import org.mongodb.morphia.annotations.Id;

class InvestDomainObject {

	@Id
	private UUID id = UUID.randomUUID();

	public UUID getId() {
		return this.id;
	}

}
