package com.webdata.parser.entities;

import java.util.List;

/**
 * Created by Sonik on 24.11.2017.
 */
public class Description {
    private List<String> designList;
    private List<String> extrasList;
    private List<String> materialCompositionList;
    private List<String> careInstructionsList;
    private List<String> sizeList;

    public Description(List<String> designList, List<String> extrasList, List<String> materialCompositionList, List<String> careInstructionsList, List<String> sizeList) {
        this.designList = designList;
        this.extrasList = extrasList;
        this.materialCompositionList = materialCompositionList;
        this.careInstructionsList = careInstructionsList;
        this.sizeList = sizeList;
    }

    public List<String> getDesignList() {
        return designList;
    }

    public List<String> getExtrasList() {
        return extrasList;
    }

    public List<String> getMaterialCompositionList() {
        return materialCompositionList;
    }

    public List<String> getCareInstructionsList() {
        return careInstructionsList;
    }

    public List<String> getSizeList() {
        return sizeList;
    }

    @Override
    public String toString() {
        return "    Produktdetails:" + '\n' +
                "       Design: " + designList + '\n' +
                "       Extras: " + extrasList + '\n' +
                "       Material Composition: " + materialCompositionList + '\n' +
                "       Care Instructions: " + careInstructionsList + '\n' +
                "       size:" + sizeList;
    }
}
