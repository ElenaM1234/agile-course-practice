package ru.unn.agile.vector3d.viewmodel;

import java.util.regex.Pattern;
import ru.unn.agile.vector3d.model.Vector3D;

public class ViewModel {
    private OperationTab activeTab;
    private String vectorText;
    private String dotProductOperandText;
    private String crossProductOperandText;
    private String normResultText;
    private String normalizationResultText;
    private String dotProductResultText;
    private String crossProductResultText;
    private boolean buttonEnabled;

    public ViewModel() {
        activeTab = OperationTab.NORM;
        vectorText = "";
        dotProductOperandText = "";
        crossProductOperandText = "";
        normResultText = "";
        normalizationResultText = "";
        dotProductResultText = "";
        crossProductResultText = "";
        buttonEnabled = false;
    }

    public OperationTab getActiveTab() {
        return activeTab;
    }

    public void setActiveTab(final OperationTab newActiveTab) {
        activeTab = newActiveTab;
        refreshButtonState();
    }

    public boolean isButtonEnabled() {
        return buttonEnabled;
    }

    public String getVectorText() {
        return vectorText;
    }

    public void setVectorText(final String text) {
        vectorText = text;
        refreshButtonState();
    }

    public String getDotProductOperandText() {
        return dotProductOperandText;
    }

    public void setDotProductOperandText(final String text) {
        dotProductOperandText = text;
        refreshButtonState();
    }

    public String getCrossProductOperandText() {
        return crossProductOperandText;
    }

    public void setCrossProductOperandText(final String text) {
        crossProductOperandText = text;
        refreshButtonState();
    }

    public String getNormResultText() {
        return normResultText;
    }

    public String getNormalizationResultText() {
        return normalizationResultText;
    }

    public String getDotProductResultText() {
        return dotProductResultText;
    }

    public String getCrossProductResultText() {
        return crossProductResultText;
    }

    public boolean validate(final String text) {
        String coordPattern = "[\\+-]?[0-9]+(\\.[0-9]+)?";
        String vectorPattern = " *" + coordPattern + ", *"
                                    + coordPattern + ", *"
                                    + coordPattern + " *";
        String vectorInBracketsPattern = " *\\(" + vectorPattern + "\\) *";
        return Pattern.matches("^" + vectorPattern + "$", text)
            || Pattern.matches("^" + vectorInBracketsPattern + "$", text);
    }

    public Vector3D vectorFromString(final String text) {
        if (!validate(text)) {
            throw new IllegalArgumentException();
        }
        String[] coordTexts = text.replaceAll("[ \\+\\(\\)]+", "").split(",");
        double[] coords = new double[coordTexts.length];
        for (int i = 0; i < coordTexts.length; i++) {
            coords[i] = Double.parseDouble(coordTexts[i]);
        }
        return new Vector3D(coords);
    }

    public boolean canCalculate() {
        return validate(vectorText)
            && (activeTab != OperationTab.DOTPRODUCT || validate(dotProductOperandText))
            && (activeTab != OperationTab.CROSSPRODUCT || validate(crossProductOperandText));
    }

    void refreshButtonState() {
        buttonEnabled = canCalculate();
    }
}

enum OperationTab {
    NORM,
    NORMALIZATION,
    DOTPRODUCT,
    CROSSPRODUCT
}
