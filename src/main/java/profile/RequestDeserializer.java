package profile;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import profile.entity.Action;
import profile.entity.ProjectExperience;
import profile.entity.Skill;
import profile.entity.UserSkill;

import java.io.IOException;

public class RequestDeserializer extends JsonDeserializer<Action> {
    @Override
    public Action deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        ObjectNode root = mapper.readTree(jsonParser);
        if (root.has("phone")) {
            return mapper.readValue(root.toString(), ProjectExperience.class);
        }
        if (root.has("project")) {
            return mapper.readValue(root.toString(), ProjectExperience.class);
        }
        if (root.has("skill")) {
            return mapper.readValue(root.toString(), UserSkill.class);
        }
        if (root.has("skillArea")) {
            return mapper.readValue(root.toString(), Skill.class);
        }
        return null;
    }
}
