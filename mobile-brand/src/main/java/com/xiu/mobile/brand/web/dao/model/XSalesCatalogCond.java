package com.xiu.mobile.brand.web.dao.model;

import java.util.Date;

public class XSalesCatalogCond{
	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XIU_GOODS.X_SALES_CATALOG_COND.CATALOG_ID
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    private Long catalogId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XIU_GOODS.X_SALES_CATALOG_COND.CATGROUP_ID
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    private Long catgroupId;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XIU_GOODS.X_SALES_CATALOG_COND.CATGROUP_TYPE
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    private String catgroupType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XIU_GOODS.X_SALES_CATALOG_COND.SEARCHQUERY
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    private String searchquery;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XIU_GOODS.X_SALES_CATALOG_COND.CREATOR_ID
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    private Long creatorId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XIU_GOODS.X_SALES_CATALOG_COND.TIMEPLACED
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    private Date timeplaced;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XIU_GOODS.X_SALES_CATALOG_COND.OPERATOR_ID
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    private Long operatorId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XIU_GOODS.X_SALES_CATALOG_COND.LASTUPDATE
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    private Date lastupdate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XIU_GOODS.X_SALES_CATALOG_COND.FIELD1
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    private String field1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XIU_GOODS.X_SALES_CATALOG_COND.FIELD2
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    private String field2;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XIU_GOODS.X_SALES_CATALOG_COND.FIELD3
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    private String field3;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XIU_GOODS.X_SALES_CATALOG_COND.FIELD4
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    private Long field4;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XIU_GOODS.X_SALES_CATALOG_COND.FIELD5
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    private Long field5;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XIU_GOODS.X_SALES_CATALOG_COND.FIELD6
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    private Long field6;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XIU_GOODS.X_SALES_CATALOG_COND.FIELD7
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    private Long field7;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XIU_GOODS.X_SALES_CATALOG_COND.FIELD8
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    private Date field8;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XIU_GOODS.X_SALES_CATALOG_COND.OPTCOUNTER
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    private Long optcounter;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XIU_GOODS.X_SALES_CATALOG_COND.EVALUATEQUERY
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    private String evaluatequery;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XIU_GOODS.X_SALES_CATALOG_COND.QUERY
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    private String query;
    
    /**
     * 排序顺序	
     */
    private Integer orderBy;
    /**
     * 排序属性ID
     */
    private Long attrdictgrpId;

	public Integer getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	public Long getAttrdictgrpId() {
		return attrdictgrpId;
	}

	public void setAttrdictgrpId(Long attrdictgrpId) {
		this.attrdictgrpId = attrdictgrpId;
	}

	public Long getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(Long catalogId) {
		this.catalogId = catalogId;
	}

	public Long getCatgroupId() {
		return catgroupId;
	}

	public void setCatgroupId(Long catgroupId) {
		this.catgroupId = catgroupId;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.CATGROUP_TYPE
     *
     * @return the value of XIU_GOODS.X_SALES_CATALOG_COND.CATGROUP_TYPE
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public String getCatgroupType() {
        return catgroupType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.CATGROUP_TYPE
     *
     * @param catgroupType the value for XIU_GOODS.X_SALES_CATALOG_COND.CATGROUP_TYPE
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public void setCatgroupType(String catgroupType) {
        this.catgroupType = catgroupType == null ? null : catgroupType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.SEARCHQUERY
     *
     * @return the value of XIU_GOODS.X_SALES_CATALOG_COND.SEARCHQUERY
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public String getSearchquery() {
        return searchquery;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.SEARCHQUERY
     *
     * @param searchquery the value for XIU_GOODS.X_SALES_CATALOG_COND.SEARCHQUERY
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public void setSearchquery(String searchquery) {
        this.searchquery = searchquery == null ? null : searchquery.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.CREATOR_ID
     *
     * @return the value of XIU_GOODS.X_SALES_CATALOG_COND.CREATOR_ID
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public Long getCreatorId() {
        return creatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.CREATOR_ID
     *
     * @param creatorId the value for XIU_GOODS.X_SALES_CATALOG_COND.CREATOR_ID
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.TIMEPLACED
     *
     * @return the value of XIU_GOODS.X_SALES_CATALOG_COND.TIMEPLACED
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public Date getTimeplaced() {
        return timeplaced;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.TIMEPLACED
     *
     * @param timeplaced the value for XIU_GOODS.X_SALES_CATALOG_COND.TIMEPLACED
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public void setTimeplaced(Date timeplaced) {
        this.timeplaced = timeplaced;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.OPERATOR_ID
     *
     * @return the value of XIU_GOODS.X_SALES_CATALOG_COND.OPERATOR_ID
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public Long getOperatorId() {
        return operatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.OPERATOR_ID
     *
     * @param operatorId the value for XIU_GOODS.X_SALES_CATALOG_COND.OPERATOR_ID
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.LASTUPDATE
     *
     * @return the value of XIU_GOODS.X_SALES_CATALOG_COND.LASTUPDATE
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public Date getLastupdate() {
        return lastupdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.LASTUPDATE
     *
     * @param lastupdate the value for XIU_GOODS.X_SALES_CATALOG_COND.LASTUPDATE
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public void setLastupdate(Date lastupdate) {
        this.lastupdate = lastupdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.FIELD1
     *
     * @return the value of XIU_GOODS.X_SALES_CATALOG_COND.FIELD1
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public String getField1() {
        return field1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.FIELD1
     *
     * @param field1 the value for XIU_GOODS.X_SALES_CATALOG_COND.FIELD1
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public void setField1(String field1) {
        this.field1 = field1 == null ? null : field1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.FIELD2
     *
     * @return the value of XIU_GOODS.X_SALES_CATALOG_COND.FIELD2
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public String getField2() {
        return field2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.FIELD2
     *
     * @param field2 the value for XIU_GOODS.X_SALES_CATALOG_COND.FIELD2
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public void setField2(String field2) {
        this.field2 = field2 == null ? null : field2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.FIELD3
     *
     * @return the value of XIU_GOODS.X_SALES_CATALOG_COND.FIELD3
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public String getField3() {
        return field3;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.FIELD3
     *
     * @param field3 the value for XIU_GOODS.X_SALES_CATALOG_COND.FIELD3
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public void setField3(String field3) {
        this.field3 = field3 == null ? null : field3.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.FIELD4
     *
     * @return the value of XIU_GOODS.X_SALES_CATALOG_COND.FIELD4
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public Long getField4() {
        return field4;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.FIELD4
     *
     * @param field4 the value for XIU_GOODS.X_SALES_CATALOG_COND.FIELD4
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public void setField4(Long field4) {
        this.field4 = field4;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.FIELD5
     *
     * @return the value of XIU_GOODS.X_SALES_CATALOG_COND.FIELD5
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public Long getField5() {
        return field5;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.FIELD5
     *
     * @param field5 the value for XIU_GOODS.X_SALES_CATALOG_COND.FIELD5
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public void setField5(Long field5) {
        this.field5 = field5;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.FIELD6
     *
     * @return the value of XIU_GOODS.X_SALES_CATALOG_COND.FIELD6
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public Long getField6() {
        return field6;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.FIELD6
     *
     * @param field6 the value for XIU_GOODS.X_SALES_CATALOG_COND.FIELD6
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public void setField6(Long field6) {
        this.field6 = field6;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.FIELD7
     *
     * @return the value of XIU_GOODS.X_SALES_CATALOG_COND.FIELD7
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public Long getField7() {
        return field7;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.FIELD7
     *
     * @param field7 the value for XIU_GOODS.X_SALES_CATALOG_COND.FIELD7
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public void setField7(Long field7) {
        this.field7 = field7;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.FIELD8
     *
     * @return the value of XIU_GOODS.X_SALES_CATALOG_COND.FIELD8
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public Date getField8() {
        return field8;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.FIELD8
     *
     * @param field8 the value for XIU_GOODS.X_SALES_CATALOG_COND.FIELD8
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public void setField8(Date field8) {
        this.field8 = field8;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.OPTCOUNTER
     *
     * @return the value of XIU_GOODS.X_SALES_CATALOG_COND.OPTCOUNTER
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public Long getOptcounter() {
        return optcounter;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.OPTCOUNTER
     *
     * @param optcounter the value for XIU_GOODS.X_SALES_CATALOG_COND.OPTCOUNTER
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public void setOptcounter(Long optcounter) {
        this.optcounter = optcounter;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.EVALUATEQUERY
     *
     * @return the value of XIU_GOODS.X_SALES_CATALOG_COND.EVALUATEQUERY
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public String getEvaluatequery() {
        return evaluatequery;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.EVALUATEQUERY
     *
     * @param evaluatequery the value for XIU_GOODS.X_SALES_CATALOG_COND.EVALUATEQUERY
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public void setEvaluatequery(String evaluatequery) {
        this.evaluatequery = evaluatequery == null ? null : evaluatequery.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.QUERY
     *
     * @return the value of XIU_GOODS.X_SALES_CATALOG_COND.QUERY
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public String getQuery() {
        return query;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XIU_GOODS.X_SALES_CATALOG_COND.QUERY
     *
     * @param query the value for XIU_GOODS.X_SALES_CATALOG_COND.QUERY
     *
     * @mbggenerated Tue Jul 17 12:31:35 CST 2012
     */
    public void setQuery(String query) {
        this.query = query == null ? null : query.trim();
    }
}