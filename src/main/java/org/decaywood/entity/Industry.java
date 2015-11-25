package org.decaywood.entity;

import java.util.Collections;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/23 13:42
 */
public class Industry implements DeepCopy<Industry> {

    private final String industryName;

    private final String industryInfo;

    private List<Stock> stocks = Collections.emptyList();


    public Industry(final String industryName, final String industrySiteURL) {
        this.industryName = industryName;
        this.industryInfo = industrySiteURL;
    }


    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
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
