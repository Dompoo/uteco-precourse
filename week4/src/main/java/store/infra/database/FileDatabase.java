package store.infra.database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import store.infra.entity.DatabaseEntity;

public abstract class FileDatabase<T extends DatabaseEntity> implements Database<T> {

    private static final String COLUMN_SEPARATOR = ",";

    private final String headerLine;

    public FileDatabase() {
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath()))) {
            this.headerLine = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("파일 읽기 중 오류가 발생했습니다", e);
        }
    }

    @Override
    public List<T> readAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath()))) {
            return buildObjects(reader);
        } catch (IOException e) {
            throw new RuntimeException("파일 읽기 중 오류가 발생했습니다", e);
        }
    }

    private List<T> buildObjects(BufferedReader reader) throws IOException {
        List<T> objects = new ArrayList<>();
        reader.readLine();
        String line;
        while ((line = reader.readLine()) != null) {
            objects.add(convertLineToObject(mapData(line)));
        }
        return objects;
    }

    private Map<String, String> mapData(String line) {
        String[] datas = line.split(COLUMN_SEPARATOR);
        String[] columns = headerLine.split(COLUMN_SEPARATOR);
        Map<String, String> dataMap = new HashMap<>();
        for (int i = 0; i < columns.length; i++) {
            dataMap.put(columns[i], datas[i]);
        }
        return dataMap;
    }

    @Override
    public void updateAll(List<T> objects) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFilePath(), false))) {
            writer.write(buildUpdateScript(objects));
        } catch (IOException e) {
            throw new RuntimeException("파일 업데이트 중 오류가 발생했습니다", e);
        }
    }

    private String buildUpdateScript(List<T> objects) {
        StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.add(headerLine);
        for (T object : objects) {
            String line = object.toLine(headerLine.split(COLUMN_SEPARATOR));
            stringJoiner.add(line);
        }
        stringJoiner.add("");
        return stringJoiner.toString();
    }

    protected abstract String getFilePath();

    protected abstract T convertLineToObject(Map<String, String> dataMap);
}
