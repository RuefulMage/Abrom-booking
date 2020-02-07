package ru.kpfu.itis.Transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.kpfu.itis.Models.Token;

@Data
@AllArgsConstructor
public class TokenDTO {

    private String token;

    public static TokenDTO from(Token token){
        return new TokenDTO(token.getValue());
    }
}
