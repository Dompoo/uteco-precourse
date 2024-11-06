package store.infra.database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public abstract class FileDatabase<T> implements Database<T> {

    @Override
    public List<T> readAll() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(getFileName());

        validateFileExist(inputStream);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String headerLine = reader.readLine();

            validateLineExist(headerLine);

            List<T> objects = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                objects.add(convertStringToObject(line));
            }
            return objects;
        } catch (IOException e) {
            throw new RuntimeException("파일 읽기 중 오류가 발생했습니다", e);
        }
    }

    private static void validateLineExist(String headerLine) {
        if (headerLine == null) {
            throw new RuntimeException("파일이 비어있습니다.");
        }
    }

    private void validateFileExist(InputStream inputStream) {
        if (inputStream == null) {
            throw new RuntimeException("파일을 찾을 수 없습니다: " + getFileName());
        }
    }

    @Override
    public void saveAll(List<T> products) {
        File file = new File(getClass().getClassLoader().getResource(getFileName()).getFile());

        List<String> lines = new ArrayList<>();
        for (T product : products) {
            lines.add(convertObjectToString(product));
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("파일 쓰기 중 오류가 발생했습니다", e);
        }
    }

    protected abstract String getFileName();

    protected abstract String convertObjectToString(T object);

    protected abstract T convertStringToObject(String line);
}
