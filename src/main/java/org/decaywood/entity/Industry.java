package org.decaywood.entity;

/**
 * @author: decaywood
 * @date: 2015/11/23 13:42
 */
public class Industry implements DeepCopy<Industry> {

    private final String industryName;

    private final String industryInfo;


    public Industry(final String industryName, final String industrySiteURL) {
        this.industryName = industryName;
        this.industryInfo = industrySiteURL;
    }


    public String getIndustryName() {
        return industryName;
    }

    public String getIndustryInfo() {
        return industryInfo;
    }

    @Override
    public String toString() {
        return "industryName = " + industryName  + "  " + "industryInfo = " + industryInfo;
    }


    @Override
    public Industry copy() {
        return new Industry(industryName, industryInfo);
    }
}
