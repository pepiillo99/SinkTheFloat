package us.pepeyedu.SinkTheFloat.Utils;

public interface Callback<T> {
	void done(T result, Exception ex);
}
