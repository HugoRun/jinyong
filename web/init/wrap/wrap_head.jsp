<%@page pageEncoding="UTF-8" isErrorPage="false"%>
<%@page import="com.ls.pub.config.GameConfig"%>
乾坤袋空间:${roleInfo.basicInfo.wrapSpare }/${roleInfo.basicInfo.wrapContent}<br/>
财产:${roleInfo.basicInfo.moneyDes }(<%=GameConfig.getYuanbaoName() %>${yuanbao }颗)<br/>