package org.decaywood.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/12/4 14:38
 */

/**
 * 公司信息
 */
public class CompanyInfo implements DeepCopy<CompanyInfo> {

    private final String compsname;//公司名称
    private final String orgtype;//组织形式
    private final String founddate;//成立日期
    private final String bizscope;//经营范围
    private final String majorbiz;//主营业务
    private final String region;//地区代码
    private final List<Industry> tqCompIndustryList;//所属板块


    /**
     *
     * @param compsname 公司名称
     * @param orgtype 组织形式
     * @param founddate 成立日期
     * @param bizscope 经营范围
     * @param majorbiz 主营业务
     * @param region 地区代码
     * @param tqCompIndustryList 所属板块
     */
    public CompanyInfo(String compsname,
                       String orgtype,
                       String founddate,
                       String bizscope,
                       String majorbiz,
                       String region,
                       List<Industry> tqCompIndustryList) {
        this.compsname = compsname;
        this.orgtype = orgtype;
        this.founddate = founddate;
        this.bizscope = bizscope;
        this.majorbiz = majorbiz;
        this.region = region;
        this.tqCompIndustryList = tqCompIndustryList;
    }

    public String getCompsname() {
        return compsname;
    }

    public String getOrgtype() {
        return orgtype;
    }

    public String getFounddate() {
        return founddate;
    }

    public String getBizscope() {
        return bizscope;
    }

    public String getMajorbiz() {
        return majorbiz;
    }

    public String getRegion() {
        return region;
    }

    public List<Industry> getTqCompIndustryList() {
        return tqCompIndustryList;
    }

    @Override
    public CompanyInfo copy() {
        return new CompanyInfo(
                compsname,
                orgtype,
                founddate,
                bizscope,
                majorbiz,
                region,
                new ArrayList<>(tqCompIndustryList));
    }


}
