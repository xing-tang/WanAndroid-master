package com.open.baselibrary.managers.storage;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.bind.util.ISO8601Utils;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

final class DateTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

	private final DateFormat enUs12hFormat;
	private final DateFormat enUs24hFormat;

	DateTypeAdapter() {
		enUs12hFormat = new SimpleDateFormat("MMM d, y hh:mm:ss", Locale.US);
		enUs24hFormat = new SimpleDateFormat("MMM d, y HH:mm:ss", Locale.US);
	}

	@Override
	public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
		String dateFormatAsString = ISO8601Utils.format(src);
		return new JsonPrimitive(dateFormatAsString);
	}

	@Override
	public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		if (!(json instanceof JsonPrimitive)) {
			throw new JsonParseException("The date should be a string value");
		}
		Date date = deserializeToDate(json);
		if (typeOfT == Date.class) {
			return date;
		} else if (typeOfT == Timestamp.class) {
			return new Timestamp(date.getTime());
		} else if (typeOfT == java.sql.Date.class) {
			return new java.sql.Date(date.getTime());
		} else {
			throw new IllegalArgumentException(getClass() + " cannot deserialize to " + typeOfT);
		}
	}

	private Date deserializeToDate(JsonElement json) {
		synchronized (enUs24hFormat) {
			try {
				return enUs24hFormat.parse(json.getAsString());
			} catch (ParseException ignored) {
			}
			try {
				return enUs12hFormat.parse(json.getAsString());
			} catch (ParseException ignored) {
			}
			try {
				return ISO8601Utils.parse(json.getAsString(), new ParsePosition(0));
			} catch (ParseException e) {
				throw new JsonSyntaxException(json.getAsString(), e);
			}
		}
	}

	@Override
	public String toString() {
		return DateTypeAdapter.class.getSimpleName();
	}
}
