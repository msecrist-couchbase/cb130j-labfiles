package com.couchbase.customer360.data;

import com.couchbase.client.core.error.CouchbaseException;
import com.couchbase.client.core.error.DocumentExistsException;
import com.couchbase.client.core.error.DocumentNotFoundException;
import com.couchbase.client.core.error.QueryException;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.kv.MutationResult;
import com.couchbase.client.java.query.QueryResult;
import com.couchbase.customer360.domain.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.couchbase.client.java.query.QueryOptions.queryOptions;

@Repository
public class CustomerRepository implements BaseRepository<Customer> {
    //private final JsonTranscoder transcoder;
    private final Cluster cluster;
    private final Bucket bucket;
    private final Collection collection;

    public CustomerRepository(Cluster cluster, Bucket customerBucket) {
        this.cluster = cluster;
        this.bucket = customerBucket;
        collection = bucket.defaultCollection();

    }

    // Lab 4
    @Override
    public Customer findById(String id) {
        try {
            GetResult result = collection.get(id); //, GetOptions.getOptions().transcoder(transcoder));
            return result.contentAs(Customer.class);
        } catch (DocumentNotFoundException ex) {
            System.err.println("Document not found!");
            return null;
        }
    }

    // Lab 5
    @Override
    public Customer create(Customer entity) {
        try {
            String key = entity.getType() + "::" + entity.getId();
            MutationResult result = collection.insert(key, entity); //,
        } catch (DocumentExistsException ex) {
            System.err.println("The document already exists!");
        } catch (CouchbaseException ex) {
            System.err.println("Something else happened: " + ex);
        }
        return entity;
    }

    // Lab 7
    @Override
    public Customer update(Customer entity) {
        try {
            String key = entity.getType() + "::" + entity.getId();
            collection.replace(key,entity);
        } catch (DocumentNotFoundException ex) {

            System.err.println("Document did not exist when trying to remove");
        } catch (CouchbaseException ex) {
            System.err.println("Something else happened: " + ex);
        }
        return entity;
    }

    @Override
    public Customer upsert(Customer entity) {
        try {
            String key = entity.getType() + "::" + entity.getId();
            collection.upsert(key, entity);
        } catch (CouchbaseException ex) {
            System.err.println("Something else happened: " + ex);
        }
        return entity;
    }

    @Override
    public void delete(Customer entity) {
        try {
            String key = entity.getType() + "::" + entity.getId();
            collection.remove(key);
        } catch (DocumentNotFoundException ex) {
            System.err.println("Document did not exist when trying to remove");
        } catch (CouchbaseException ex) {
            System.err.println("Something else happened: " + ex);
        }
    }

    // Lab 6 (Modified from original task)
    public List<Customer> findAllByCountry(String country) {
        String query="select customer360.* from `customer360` where billingAddress.country = $countryCode and type='customer' limit 10";
        List<Customer> customerList = null;
        try {
            QueryResult queryResult = cluster.query(query,
                    queryOptions().parameters(JsonObject.create().put("countryCode", country)));
            customerList =  queryResult.rowsAs(Customer.class);
        } catch (QueryException e) {
            System.err.println("Query failed: " + query);
        }
        return customerList;
    }

}
