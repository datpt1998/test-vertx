package util;

import annotation.Entity;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowIterator;
import io.vertx.sqlclient.RowSet;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DBUtil {
    public static <T> List<T> getList(Class<T> clazz, RowSet<Row> result) throws IllegalAccessException, InstantiationException {
        Logger logger = Logger.getLogger(DBUtil.class.getName());
        List<T> objList = new ArrayList<>();
        RowIterator<Row> iterator = result.iterator();
        while (iterator.hasNext()) {
            Row row = iterator.next();
            T newObj = ReflectionUtil.getObject(clazz, row);
            if(newObj == null) continue;
            objList.add(newObj);
        }
        return objList;
    }
}
