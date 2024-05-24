package pl.torlop.mtg.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorMapperService {
    public String mapColorIdentityToName(List<String> colorIdentity) {
        return null;
    }

    public String getStringColorIdentity(List<String> colorIdentity) {
        String base = "WUBRG";
        StringBuilder sb = new StringBuilder();
        for (String color : base.split("")) {
            if (colorIdentity.contains(color)) {
                sb.append(color);
            }
        }
        return sb.toString();
    }

    public String mapColorIdentityStringToName(String colorIdentity) {
        return null;
    }
}
