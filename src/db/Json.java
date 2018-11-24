package db;
import com.google.gson.*;

public class Json<T> {
	final Class<T> typeParameterClass;

    public Json(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }
	
	public T GetObject(String jsonString)
	{
		Gson gson = new Gson();
		return gson.fromJson(jsonString, this.typeParameterClass);
	}

	public String GetStringJson(T object)
	{
		Gson gson = new Gson();
		return gson.toJson(object);
	}
}
