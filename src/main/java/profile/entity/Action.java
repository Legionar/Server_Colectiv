package profile.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import profile.RequestDeserializer;

@JsonDeserialize(using = RequestDeserializer.class)
public interface Action {
}
