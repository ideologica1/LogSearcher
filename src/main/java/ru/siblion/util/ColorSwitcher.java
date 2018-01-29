package ru.siblion.util;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@SessionScope
public class ColorSwitcher implements Serializable{

    private String backgroundStyle;

    private String fieldStyle;

    private String inverseFormTextStyle;

    private String inverseFieldTextStyle;

    private List<String> allowedColors;

    Logger logger = Logger.getLogger(ColorSwitcher.class);
    @PostConstruct
    private void init() {

        String defaultBackGroundColor = "#f0fadc";
        allowedColors = new ArrayList<>();
        allowedColors.add("#b4ffff");
        allowedColors.add("#b4f0f0");
        allowedColors.add("#b4ffc8");
        allowedColors.add("#c5dbbf");
        allowedColors.add("#ffedcc");
        allowedColors.add("#fae6c8");
        allowedColors.add("#ccb7cc");
        allowedColors.add("#b4b4ff");
        allowedColors.add("#b4c8ff");
        allowedColors.add("#b8b6bd");
        allowedColors.add("#f2f0c8");
        allowedColors.add("#f0fadc");
        allowedColors.add("#f2f0b4");
        allowedColors.add("#e9fab4");
        setBackgroundColor(defaultBackGroundColor);

    }

    public void setBackgroundColor(String backgroundColor) {
        logger.info("switched background color to " + backgroundColor);
        if (isColorAllowed(backgroundColor)) {
            this.backgroundStyle = "background-color: " + backgroundColor;
            String fieldColor = generateFieldColor(backgroundColor);
            this.fieldStyle = "background-color: " + fieldColor;
            this.inverseFormTextStyle = "color: " + generateInverseTextColor(backgroundColor);
            this.inverseFieldTextStyle = "color: " + generateInverseTextColor(fieldColor);
        }
        else {
         //   FacesContext.getCurrentInstance().addMessage(null,
          //          new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка: выбранный цвет недоступен", "Error"));
            setBackgroundColor(allowedColors.get((int)(Math.random()*allowedColors.size())));
        }
    }

    private String generateFieldColor(String backgroundColor) {
        Color color = Color.decode(backgroundColor);
        int backgroundRed = (int)(color.getRed()*1.15);
        int backgroundGreen = (int)(color.getGreen()*1.15);
        int backgroundBlue = (int)(color.getBlue()*1.15);
        int fieldRed = backgroundRed < 255 ? backgroundRed : 255;
        int fieldGreen = backgroundGreen < 255 ? backgroundGreen : 255;
        int fieldBlue = backgroundBlue < 255 ? backgroundBlue : 255;
        Color textColor = new Color(fieldRed, fieldGreen, fieldBlue);
        String hex = Integer.toHexString(textColor.getRGB());
        hex = "#" + hex.substring(2, hex.length());
        return hex;
    }

    private String generateInverseTextColor(String backgroundColor) {
        Color color = Color.decode(backgroundColor);
        int textRed = Math.abs(color.getRed() - 255);
        int textGreen = Math.abs(color.getGreen() - 255);
        int textBlue = Math.abs(color.getBlue() - 255);
        Color textColor = new Color(textRed, textGreen, textBlue);
        String hex = Integer.toHexString(textColor.getRGB());
        hex = "#" + hex.substring(2, hex.length());
        return hex;

    }

    private boolean isColorAllowed(String color) {
        for (String allowedColor: allowedColors) {
            if (allowedColor.equals(color))
                return true;
        }
        return false;
    }

    public String getBackgroundStyle() {
        return backgroundStyle;
    }

    public String getFieldStyle() {
        return fieldStyle;
    }

    public String getInverseFormTextStyle() {
        return inverseFormTextStyle;
    }

    public String getInverseFieldTextStyle() {
        return inverseFieldTextStyle;
    }

}
