package objects;

import java.sql.ResultSet;
import java.util.List;

public interface IMapper<T> {

	public List<T> mapRersultSetToObject(ResultSet rs, Class<?> outputClass);
}
