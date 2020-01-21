package ru.kpfu.itis.Transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.kpfu.itis.Models.Token;

@Getter
@Setter
@AllArgsConstructor
public class TokenDTO {

    @JsonProperty()
    private String token;

    public static TokenDTO from(Token token){
        return new TokenDTO(token.getValue());
    }
}
