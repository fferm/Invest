package se.fermitet.invest.domain;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.mongodb.morphia.annotations.Id;

class InvestDomainObject {

	@NotNull
	@Id
	private UUID id = UUID.randomUUID();

	public UUID getId() {
		return this.id;
	}

}
