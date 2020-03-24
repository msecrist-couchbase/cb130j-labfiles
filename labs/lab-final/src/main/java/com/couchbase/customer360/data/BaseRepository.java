package com.couchbase.customer360.data;

import com.couchbase.customer360.domain.Entity;

/**
 * Interface that defines standard CRUD operations for entities.
 *
 */
public interface BaseRepository<T> {
	/**
	 * Find an entity by the specified ID and return a reference to
	 * an instance of the specified type.
	 * 
	 * @param id Unique ID of the entity
	 * @return Reference to an instance of the specified type that
	 * 	corresponds to the ID 
	 */
	T findById(String id);

	/**
	 * Persist a new instance of the specified type in the repository.
	 * 
	 * @param entity Source entity to be persisted
	 * @return Reference to the entity that has been persisted
	 */
	T create(T entity);

	/**
	 * Update an existing instance of the specified type in the repository.
	 * 
	 * @param entity Source entity to be persisted
	 * @return Reference to the entity that has been persisted
	 */
	T update(T entity);

	/**
	 * Insert or update an instance of the specified type in the repository.
	 * 
	 * @param entity Source entity to be persisted
	 * @return Reference to the entity that has been persisted
	 */
	T upsert(T entity);

	/**
	 * Delete the specified entity from the repository.
	 * 
	 * @param entity Source entity to be deleted
	 */
	void delete(T entity);
}