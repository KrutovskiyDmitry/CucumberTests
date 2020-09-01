package steps;

import org.openqa.selenium.WebElement;

public class MyHandlers {

    public int refineAndParse(WebElement element) {
        int i = Integer.parseInt(element.getText().replaceAll("[^0-9]", ""));
        return i;
        }


    }

