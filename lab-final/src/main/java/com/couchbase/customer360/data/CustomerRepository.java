package com.couchbase.customer360.data;

import com.couchbase.client.core.error.CouchbaseException;
import com.couchbase.client.core.error.DocumentExistsException;
import com.couchbase.client.core.error.DocumentNotFoundException;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.codec.JacksonJsonSerializer;
import com.couchbase.client.java.codec.JsonTranscoder;
import com.couchbase.client.java.kv.*;
import com.couchbase.customer360.domain.Customer;
import org.springframework.stereotype.Repository;

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

        //transcoder = JsonTranscoder.create(new GsonSerializer());
       // transcoder = JsonTranscoder.create(JacksonJsonSerializer.create());
    }

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

    @Override
    public Customer create(Customer entity) {
        try {
            String key = entity.getType() + "::" + entity.getId();
            MutationResult result = collection.insert(key, entity); //,
//                    InsertOptions.insertOptions().transcoder(transcoder));
        } catch (DocumentExistsException ex) {
            System.err.println("The document already exists!");
        } catch (CouchbaseException ex) {
            System.err.println("Something else happened: " + ex);
        }
        return entity;
    }

    @Override
    public Customer update(Customer entity) {
        try {
            String key = entity.getType() + "::" + entity.getId();
            collection.replace(key,entity); //, ReplaceOptions.replaceOptions().transcoder(transcoder));
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
            collection.upsert(key, entity); //,
//                    UpsertOptions.upsertOptions().transcoder(transcoder));
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

}
