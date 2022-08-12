package com.example.androidhomework.http;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.math.BigDecimal;

public class MyTypeAdapterFactory<T> implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<T> rawType = (Class<T>) type.getRawType();
        if (rawType == Float.class || rawType == float.class) {
            return (TypeAdapter<T>) new FloatlNullAdapter();
        }else if (rawType == Integer.class || rawType == int.class){
            return (TypeAdapter<T>) new IntNullAdapter();
        }
        return null;
    }
}

class IntNullAdapter extends TypeAdapter<Number> {

    @Override
    public void write(JsonWriter out, Number value) throws IOException {
        out.value(value);
    }

    @Override
    public Number read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }
        try {
            return reader.nextInt();
        } catch (NumberFormatException e) {
            //这里解析int出错，那么捕获异常并且返回默认值，因为nextInt出错中断了方法，没有完成位移，所以调用nextString()方法完成位移。
            reader.nextString();
            return 0;
        }
    }
}


class FloatlNullAdapter extends TypeAdapter<Float> {
    @Override
    public Float read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.STRING) {
            reader.skipValue(); //跳过当前
            return -1f;
        }
        BigDecimal bigDecimal = new BigDecimal(reader.nextString());
        return bigDecimal.floatValue();
    }

    @Override
    public void write(JsonWriter writer, Float value) throws IOException {
        writer.value(value);
    }
}


