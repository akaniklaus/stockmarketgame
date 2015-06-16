package com.akolchin.stmg.server.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.akolchin.stmg.shared.TooManyResultsException;
import com.akolchin.stmg.shared.domain.AppUser;
import com.google.common.collect.Lists;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.cmd.LoadType;
import com.googlecode.objectify.cmd.Query;

public abstract class BaseDao<T> {
	private final Class<T> clazz;

	private Objectify lazyOfy;

	protected BaseDao(final Class<T> clazz) {
		this.clazz = clazz;
	}

	protected BaseDao(final Class<T> clazz, Objectify ofy) {
		this(clazz);
		lazyOfy = ofy;
	}

	public List<T> listAll() {
		return ofy().load().type(clazz).list();
	}

	public List<T> listAllByProperty(String propName, Object propValue) {
		return ofy().load().type(clazz).filter(propName, propValue).list();
	}

	public List<T> listTopByProperty(int n, String propName) {
		return ofy().load().type(clazz).order(propName).limit(n).list();
	}

	public void save(T entity) {
		saveAndReturn(entity);
	}

	public T saveAndReturn(T entity) {
		ofy().save().entity(entity).now();
		return entity;
	}

	public Key<T> saveAndReturnKey(T entity) {
		return ofy().save().entity(entity).now();
	}

	public Collection<T> saveAndReturn(Iterable<T> entities) {
		return ofy().save().entities(entities).now().values();
	}

	public T get(Key<T> key) {
		return ofy().load().key(key).now();
	}

	public T get(Long id) {
		// TODO probably it could be fixed by parameters of
		// work around for objectify cacheing and new query not having the
		// latest
		// data
		ofy().clear();

		return ofy().load().type(clazz).id(id).now();
	}

	public T getByProperty(String propName, Object propValue) throws TooManyResultsException {
		Query<T> q = ofy().load().type(clazz);
		q = q.filter(propName, propValue);
		Iterator<T> fetch = q.limit(2).list().iterator();
		if (!fetch.hasNext()) {
			return null;
		}
		T obj = fetch.next();
		if (fetch.hasNext()) {
			throw new TooManyResultsException(q.toString() + " returned too many results");
		}
		return obj;
	}

	public Boolean exists(Key<T> key) {
		return get(key) != null;
	}

	public Boolean exists(Long id) {
		return get(id) != null;
	}

	public List<T> getSubset(List<Long> ids) {
		return new ArrayList<T>(ofy().load().type(clazz).ids(ids).values());
	}

	public Map<Long, T> getSubsetMap(List<Long> ids) {
		return new HashMap<Long, T>(ofy().load().type(clazz).ids(ids));
	}

	public void delete(T object) {
		ofy().delete().entity(object);
	}

	public void delete(Long id) {
		ofy().delete().type(clazz).id(id);
	}

	public void delete(List<T> objects) {
		ofy().delete().entities(objects);
	}

	public List<T> get(List<Key<T>> keys) {
		return Lists.newArrayList(ofy().load().keys(keys).values());
	}

	protected Objectify ofy() {
		if (lazyOfy == null) {
			lazyOfy = com.akolchin.stmg.server.dao.objectify.OfyService.ofy();
		}
		return lazyOfy;
	}

	protected LoadType<T> query() {
		return ofy().load().type(clazz);
	}

	// Application specific methods applicable for several DAOs -------------

	public T getBySymbol(String symbol, AppUser user) throws TooManyResultsException {
		Query<T> q = ofy().load().type(clazz);
		q = q.filter("symbol", symbol);
		q = q.filter("userRef", user);
		Iterator<T> fetch = q.limit(2).list().iterator();
		if (!fetch.hasNext()) {
			return null;
		}
		T obj = fetch.next();
		if (fetch.hasNext()) {
			throw new TooManyResultsException(q.toString() + " returned too many results");
		}
		return obj;

	}

	public List<T> listAll(AppUser user) {
		return ofy().load().type(clazz).filter("userRef", user).list();
	}

}
