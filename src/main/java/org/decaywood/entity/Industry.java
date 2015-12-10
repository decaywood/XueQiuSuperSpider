package org.decaywood.entity;

/**
 * @author: decaywood
 * @date: 2015/11/23 13:42
 */

/**
 * 行业板块
 */
public class Industry implements DeepCopy<Industry> {

    private final String industryName;//板块名字

    private final String industryInfo;//板块代码


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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Industry industry = (Industry) o;

        return industryName.equals(industry.industryName) && industryInfo.equals(industry.industryInfo);

    }

    @Override
    public int hashCode() {
        int result = industryName.hashCode();
        result = 31 * result + industryInfo.hashCode();
        return result;
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
