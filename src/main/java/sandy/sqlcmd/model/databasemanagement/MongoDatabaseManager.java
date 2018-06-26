package sandy.sqlcmd.model.databasemanagement;

import com.mongodb.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import org.springframework.data.mongodb.core.query.Query;
import java.util.*;

@Component(value = "mongoDatabaseManager")
public class MongoDatabaseManager implements DatabaseManager {

    private MongoTemplate mongoTemplate;
    private static final String SERVICE_TABLE = "db_info";

    @Override
    public void connect(String address, String database, String userName, String password) throws MainProcessException {
        String uri = String.format("mongodb://%s:%s@%s/%s", userName, password, address, database);
        try {
            MongoClientURI mongoClientURI = new MongoClientURI(uri);
            MongoClient mongoClient = new MongoClient(mongoClientURI);
            mongoTemplate = new MongoTemplate(mongoClient, database);
            if ( !existTable(SERVICE_TABLE)) {
                mongoTemplate.createCollection(SERVICE_TABLE);
            }
        } catch (Exception e) {
            throw new MainProcessException( "Can't connect to Mongo! " + e.getMessage());
        }
    }

    @Override
    public void disconnect() throws MainProcessException {
        mongoTemplate = null;
    }

    @Override
    public boolean isConnect() {
        return mongoTemplate != null ? true : false;
    }

    @Override
    public boolean existTable(String tableName) {
              return mongoTemplate.collectionExists(tableName);
    }

    @Override
    public boolean existColumns(String tableName, int mode, Set<String> fields) {

        Set<String> fieldsFromDb = getFieldsTable(tableName);

        int diffSize = 0;
        if( mode == FULL_COVERAGES) {
            fieldsFromDb.removeAll(fields);
            diffSize = fieldsFromDb.size();
        }
        if( mode == EXISTENCE_THESE_FIELDS ) {
            fields.removeAll(fieldsFromDb);
            diffSize = fields.size();
        }

        return diffSize == 0;
    }
    @Override
    public DataSet getTables() {
        DataSet dataSet = new DataSet();
        dataSet.addRow();
        dataSet.addField(0, "tables_name");

        Set<String> tables = mongoTemplate.getCollectionNames();
        tables.remove(SERVICE_TABLE);
        tables.forEach(item-> dataSet.addField(dataSet.addRow(), item) );

        return dataSet;
    }

    @Override
    public void dropTable(String collectionName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("tableName").is(collectionName));

        mongoTemplate.remove(query, SERVICE_TABLE);
        mongoTemplate.dropCollection(collectionName);
    }

    @Override
    public void createTable(String tableName, String[] fieldsName) {
        BasicDBObject newTableInfo = new BasicDBObject();
        newTableInfo.put("tableName", tableName);
        newTableInfo.put("fields", fieldsName);

        mongoTemplate.insert(newTableInfo, SERVICE_TABLE);
        mongoTemplate.createCollection(tableName);
    }

    @Override
    public void insert(String tableName, Map<String, String> newRecord) {
        mongoTemplate.insert(newRecord, tableName);
    }

    @Override
    public void delete(String tableName, Map<String, String> condition) {
        Query query = getQueryWhithCondition(condition);
        mongoTemplate.remove(query, tableName);

    }

    @Override
    public void update(String tableName, Map<String, String> condition, Map<String, String> newValue) {
        Query query = getQueryWhithCondition(condition);
        Update update = getQueryUpdate(newValue);

        mongoTemplate.updateMulti(query, update, tableName);

    }

    @Override
    public void clearTable(String tableName) {
        mongoTemplate.remove(new Query(), tableName);
    }

    private Update getQueryUpdate(Map<String, String> newValue) {
        Update update = new Update();
        newValue.forEach(
                (k,v) ->update.set(k,v)
        );
        return update;
    }
    @Override
    public DataSet find(String tableName) {
        List<Object> table = mongoTemplate.find(new Query(), Object.class, tableName);
        return find(tableName, table);
    }

    @Override
    public DataSet find(String tableName, Map<String, String> condition) {
        Query query = getQueryWhithCondition(condition);
        List<Object> table = mongoTemplate.find(query, Object.class, tableName);
        return find(tableName, table);
    }

    private Query getQueryWhithCondition(Map<String, String> condition) {
        Criteria criteria = new Criteria();
        List<Criteria> criteriaWhere = new ArrayList<>(condition.size());
        condition.forEach((k,v) ->criteriaWhere.add(Criteria.where(k).is(v)));
        criteria.andOperator(criteriaWhere.toArray(new Criteria[condition.size()]));
        return new Query(criteria);
    }
    private DataSet find(String tableName, List<Object> table) {

        Set<String> fields = getFieldsTable(tableName);
        DataSet result = new DataSet();
        result.addRow();

        fields.forEach(field->result.addField(0,field));

        for ( Object item : table) {
            int i = result.addRow();
            for(int indexField = 0; indexField < result.quantityFieldsInRow(0); indexField++) {
                Object key = result.getField(0, indexField);
                Map<String, String> row = (HashMap<String, String>) item;
                result.addField(i, row.get(key));
            }
        }
        return result;
    }

    private Set<String> getFieldsTable(String tableName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("tableName").is(tableName));
        Map<String, Object> tableInfo =  mongoTemplate.findOne(query,HashMap.class, SERVICE_TABLE);
        BasicDBList fieldsReference =  (BasicDBList) tableInfo.get("fields");
        Set<String> fieldsFromDb = new HashSet<>();

        fieldsReference.forEach(item->fieldsFromDb.add((String) item) );
        return fieldsFromDb;
    }
}
