package org.apache.doris.flink;

import org.apache.doris.flink.cfg.DorisExecutionOptions;
import org.apache.doris.flink.cfg.DorisOptions;
import org.apache.doris.flink.cfg.DorisReadOptions;
import org.apache.doris.flink.cfg.DorisSink;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.table.data.GenericRowData;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.data.StringData;
import org.apache.flink.table.types.logical.DoubleType;
import org.apache.flink.table.types.logical.LogicalType;
import org.apache.flink.table.types.logical.VarCharType;
import org.junit.Test;

import java.util.Properties;

/**
 * example using {@link DorisSink} for streaming.
 */
public class DorisStreamSinkExample {


    @Test
    public void testJsonString() throws Exception {
        /*
         * Test for JsonString element
         */
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStream<String> source = env.fromElements("")
                .map(new MapFunction<String, String>() {
                    @Override
                    public String map(String value) throws Exception {
                        return "{\"longitude\": \"116.405419\", \"city\": \"北京\", \"latitude\": \"39.916927\"}";
                    }
                });


        Properties pro = new Properties();
        pro.setProperty("format", "json");
        pro.setProperty("strip_outer_array", "true");
        source.addSink(
                DorisSink.sink(
                        DorisReadOptions.builder().build(),
                        DorisExecutionOptions.builder()
                                .setBatchSize(3)
                                .setBatchIntervalMs(0l)
                                .setMaxRetries(3)
                                .setStreamLoadProp(pro).build(),
                        DorisOptions.builder()
                                .setFenodes("FE_IP:8030")
                                .setTableIdentifier("db.table")
                                .setUsername("root")
                                .setPassword("").build()
                ));
        env.execute("doris stream sink example");
    }


    @Test
    public void testJsonStringWithDefaultReadOptions() throws Exception {
        /*
         * Test for JsonString element with default ReadOptions
         */
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStream<String> source = env.fromElements("")
                .map(new MapFunction<String, String>() {
                    @Override
                    public String map(String value) throws Exception {
                        return "{\"longitude\": \"116.405419\", \"city\": \"北京\", \"latitude\": \"39.916927\"}";
                    }
                });


        Properties pro = new Properties();
        pro.setProperty("format", "json");
        pro.setProperty("strip_outer_array", "true");
        source.addSink(
                DorisSink.sink(
                        DorisExecutionOptions.builder()
                                .setBatchSize(3)
                                .setBatchIntervalMs(0l)
                                .setMaxRetries(3)
                                .setStreamLoadProp(pro).build(),
                        DorisOptions.builder()
                                .setFenodes("FE_IP:8030")
                                .setTableIdentifier("db.table")
                                .setUsername("root")
                                .setPassword("").build()
                ));
        env.execute("doris stream sink example");
    }


    @Test
    public void testJsonStringWithDefaultReadOptionsAndExecutionOptions() throws Exception {
        /*
         * Test for JsonString element with default ReadOptions and ExecutionOptions
         */
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStream<String> source = env.fromElements("")
                .map(new MapFunction<String, String>() {
                    @Override
                    public String map(String value) throws Exception {
                        return "{\"longitude\": \"116.405419\", \"city\": \"北京\", \"latitude\": \"39.916927\"}";
                    }
                });

        source.addSink(
                DorisSink.sink(
                        DorisOptions.builder()
                                .setFenodes("FE_IP:8030")
                                .setTableIdentifier("db.table")
                                .setUsername("root")
                                .setPassword("").build()
                ));
        env.execute("doris stream sink example");
    }


    @Test
    public void testRowData() throws Exception {
        /*
         * Test for RowData element
         */
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStream<RowData> source = env.fromElements("")
                .map(new MapFunction<String, RowData>() {
                    @Override
                    public RowData map(String value) throws Exception {
                        GenericRowData genericRowData = new GenericRowData(3);
                        genericRowData.setField(0, StringData.fromString("北京"));
                        genericRowData.setField(1, 116.405419);
                        genericRowData.setField(2, 39.916927);
                        return genericRowData;
                    }
                });

        String[] fields = {"grid_no", "longitude", "latitude"};
        LogicalType[] types = {new VarCharType(), new DoubleType(), new DoubleType()};

        source.addSink(
                DorisSink.sink(
                        fields,
                        types,
                        DorisReadOptions.builder().build(),
                        DorisExecutionOptions.builder()
                                .setBatchSize(3)
                                .setBatchIntervalMs(0L)
                                .setMaxRetries(3)
                                .build(),
                        DorisOptions.builder()
                                .setFenodes("FE_IP:8030")
                                .setTableIdentifier("db.table")
                                .setUsername("root")
                                .setPassword("").build()
                ));
        env.execute("doris stream sink example");
    }


    @Test
    public void testRowDataWithDefaultReadOptions() throws Exception {
        /*
         * Test for RowData element with default ReadOptions
         */
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStream<RowData> source = env.fromElements("")
                .map(new MapFunction<String, RowData>() {
                    @Override
                    public RowData map(String value) throws Exception {
                        GenericRowData genericRowData = new GenericRowData(3);
                        genericRowData.setField(0, StringData.fromString("北京"));
                        genericRowData.setField(1, 116.405419);
                        genericRowData.setField(2, 39.916927);
                        return genericRowData;
                    }
                });

        String[] fields = {"grid_no", "longitude", "latitude"};
        LogicalType[] types = {new VarCharType(), new DoubleType(), new DoubleType()};

        source.addSink(
                DorisSink.sink(
                        fields,
                        types,
                        DorisExecutionOptions.builder()
                                .setBatchSize(3)
                                .setBatchIntervalMs(0L)
                                .setMaxRetries(3)
                                .build(),
                        DorisOptions.builder()
                                .setFenodes("FE_IP:8030")
                                .setTableIdentifier("db.table")
                                .setUsername("root")
                                .setPassword("").build()
                ));
        env.execute("doris stream sink example");
    }


    @Test
    public void testRowDataWithDefaultReadOptionsAndExecutionOptions() throws Exception {
        /*
         * Test for RowData element with default ReadOptions and ExecutionOptions
         */
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStream<RowData> source = env.fromElements("")
                .map(new MapFunction<String, RowData>() {
                    @Override
                    public RowData map(String value) throws Exception {
                        GenericRowData genericRowData = new GenericRowData(3);
                        genericRowData.setField(0, StringData.fromString("北京"));
                        genericRowData.setField(1, 116.405419);
                        genericRowData.setField(2, 39.916927);
                        return genericRowData;
                    }
                });

        String[] fields = {"city", "longitude", "latitude"};
        LogicalType[] types = {new VarCharType(), new DoubleType(), new DoubleType()};

        source.addSink(
                DorisSink.sink(
                        fields,
                        types,
                        DorisOptions.builder()
                                .setFenodes("FE_IP:8030")
                                .setTableIdentifier("db.table")
                                .setUsername("root")
                                .setPassword("").build()
                ));
        env.execute("doris stream sink example");
    }
}