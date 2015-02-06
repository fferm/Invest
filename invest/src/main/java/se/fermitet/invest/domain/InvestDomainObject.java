package se.fermitet.invest.domain;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.mongodb.morphia.annotations.Id;

abstract class InvestDomainObject {

	@NotNull
	@Id
	UUID id = UUID.randomUUID();

	public UUID getId() {
		return this.id;
	}

}
