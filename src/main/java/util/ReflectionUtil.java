package util;

import annotation.Entity;
import io.vertx.sqlclient.Row;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtil {
    public static <T> T getObject(Class<T> clazz, Row data) throws IllegalAccessException, InstantiationException {
        boolean isEntity = clazz.isAnnotationPresent(Entity.class);
        Class<?> superClass = clazz.getSuperclass();
        if(!isEntity) {
            while (superClass != null) {
                if(superClass.isAnnotationPresent(Entity.class)) {
                    isEntity = true;
                    break;
                }
            }
        }
        if(!isEntity) {
            throw new IllegalArgumentException(clazz.getName() + "is not entity");
        } else {
            T newObj = clazz.newInstance();
            List<Field> allClassField = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
            superClass = clazz.getSuperclass();
            while(superClass != null) {
                allClassField.addAll(Arrays.asList(superClass.getDeclaredFields()));
                superClass = superClass.getSuperclass();
            }
            for(Field field : allClassField) {
                if(!field.isAnnotationPresent(annotation.Field.class)) continue;
                field.setAccessible(true);
                annotation.Field fieldAnot = field.getAnnotation(annotation.Field.class);
                String dbFieldName = fieldAnot.value();
                field.set(newObj, data.get(field.getType(), dbFieldName));
                field.setAccessible(false);
            }
            return newObj;
        }
    }
}
