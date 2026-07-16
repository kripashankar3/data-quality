package dq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import dq.entities.operation.Operation;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.util.Arrays;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseSetup {

    protected SparkSession spark;
    protected Dataset<Row> employeeDataset;
    protected String aggRuleJsonPath = "src/test/resources/aggregate_rule.json";
    protected String compositeRuleJsonPath = "src/test/resources/composite_rule.json";
    protected String predicateRuleJsonPath = "src/test/resources/predicate_rule.json";

    protected static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder()
            .findAndAddModules()
            .build();

    @BeforeAll
    void setUp() {

        spark = SparkSession.builder()
                .master("local[2]")
                .appName("Spark Operation Tests")
                .config("spark.ui.enabled", "false")
                .getOrCreate();

        StructType schema = new StructType()
                .add("id", DataTypes.IntegerType)
                .add("name", DataTypes.StringType)
                .add("age", DataTypes.IntegerType)
                .add("department", DataTypes.StringType)
                .add("country", DataTypes.StringType)
                .add("salary", DataTypes.DoubleType)
                .add("bonus", DataTypes.DoubleType)
                .add("email", DataTypes.StringType)
                .add("active", DataTypes.BooleanType);

        List<Row> employees = Arrays.asList(
                RowFactory.create(1, "John", 25, "Engineering", "US", 120000.0, 15000.0, "john@gmail.com", true),
                RowFactory.create(2, "Alice", 19, "Engineering", "IN", 90000.0, 5000.0, "alice@yahoo.com", true),
                RowFactory.create(3, "Bob", 35, "Finance", "UK", 150000.0, 25000.0, "bob@gmail.com", true),
                RowFactory.create(4, "David", 42, "HR", "US", 80000.0, 7000.0, "david@company.com", false),
                RowFactory.create(5, "Emma", 29, "Finance", "CA", 110000.0, 12000.0, "emma@gmail.com", true),
                RowFactory.create(6, "Frank", 31, "Engineering", "US", 130000.0, 18000.0, "frank@company.com", true),
                RowFactory.create(7, "Grace", 23, "HR", "IN", 75000.0, 6000.0, "grace@gmail.com", false),
                RowFactory.create(8, "Henry", 27, "Engineering", "UK", 98000.0, 9000.0, "henry@gmail.com", true),
                RowFactory.create(9, "Ivy", 38, "Finance", "US", 170000.0, 30000.0, "ivy@yahoo.com", true),
                RowFactory.create(10, "Jack", 21, "Engineering", "CA", 85000.0, 4000.0, "jack@gmail.com", false)
        );

        employeeDataset = spark.createDataFrame(employees, schema);
    }

    protected long countMatches(Operation operation) {
        return employeeDataset
                .filter(operation.evaluate())
                .count();
    }

    @AfterAll
    void tearDown() {
        if (spark != null) {
            spark.stop();
        }
    }
}