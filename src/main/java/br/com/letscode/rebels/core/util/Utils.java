package br.com.letscode.rebels.core.util;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    private static final DecimalFormat df = new DecimalFormat("##.##");

    static {
        df.setRoundingMode(RoundingMode.UP);
    }

    public static String capitalize(String str) {
        if(str == null || str.isEmpty()) {
            return str;
        }

        return StringUtils.capitalize(str);
    }

    public static <DTO, ENTITY> List<DTO> converEntityToDTO(Class<DTO> dto, List<ENTITY> entities, ModelMapper modelMapper) {
        return entities.stream().map(r -> modelMapper.map(r, dto)).collect(Collectors.toList());
    }

    public static float calculatePercentage(int total, int group){
        return Float.parseFloat(df.format((group * 100) / total));
    }
}
