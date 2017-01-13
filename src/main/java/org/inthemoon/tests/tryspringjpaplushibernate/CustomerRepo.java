package org.inthemoon.tests.tryspringjpaplushibernate;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Dims on 13.01.2017.
 */
public interface CustomerRepo extends CrudRepository<CustomerEntity, Integer> {
}
