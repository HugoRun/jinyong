<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
引导信息：请点击“使用”，将【鸿蒙兵匣】打开！<br/>
【乾坤袋】
增大乾坤袋<br/>
乾坤袋空间:49/50<br/>
财产:100灵石(仙晶0颗)<br/>
药品|装备|仙书|材料|商城<br/>
【鸿蒙兵匣】×1<anchor>使用<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/guide.do?step=use_box_hint")%>" method="get"/></anchor>
|丢弃<br/>
状态栏<br/>
返回游戏<br/>
<%@ include file="/init/templete/card_foot.jsp"%>