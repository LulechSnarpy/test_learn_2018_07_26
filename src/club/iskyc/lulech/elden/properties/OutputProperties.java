package club.iskyc.lulech.elden.properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class OutputProperties {
	private final Map<String,String> valueToCode = new HashMap<>();
	private final Map<String,String> nameToField = new HashMap<>();
	{
		valueToCode.put("isParent", "100000");
		valueToCode.put("isVpd", "100000");
		valueToCode.put("investFlag", "100041");
		valueToCode.put("operateType", "100043");
		valueToCode.put("reportSupervision", "100042");
		valueToCode.put("deviationCheckRules", "100025");
		valueToCode.put("divType", "100046");
		valueToCode.put("divFrequency", "100047");
		valueToCode.put("isNvlType", "100044");
		valueToCode.put("isNeedNav", "100045");
		valueToCode.put("calaType", "110010");
		valueToCode.put("collectType", "110009");
		valueToCode.put("investNature", "100048");
		valueToCode.put("curCode", "110011");
		valueToCode.put("incomeType", "100050");
		valueToCode.put("investerChannel", "100051");
		valueToCode.put("cashManagement", "100000");
		valueToCode.put("custType", "110012");
		valueToCode.put("manageType", "100069");
		valueToCode.put("fiduciaryDuty", "100070");
		nameToField.put("isParent", "是否子产品");
		nameToField.put("isVpd", "虚拟产品");
		nameToField.put("investCode", "产品代码");
		nameToField.put("investName", "产品名称");
		nameToField.put("investFlag", "产品模式");
		nameToField.put("investSeriesCode", "所属组合");
		nameToField.put("operateType", "运作方式");
		nameToField.put("investChannel", "投资通道");
		nameToField.put("custodyAccount", "托管账户");
		nameToField.put("reportSupervision", "报送监管口径");
		nameToField.put("deviationCheckRules", "偏离度检核规则");
		nameToField.put("ownerUser", "所属用户");
		nameToField.put("startCouponDate", "产品起息日");
		nameToField.put("maturityDate", "产品到期日");
		nameToField.put("winupPaydate", "产品兑付日");
		nameToField.put("foundAmt", "产品成立金额(万)");
		nameToField.put("divType", "分红类型");
		nameToField.put("divFrequency", "分红频率");
		nameToField.put("divYearSeason", "分红月");
		nameToField.put("divSettlementDay", "分红日");
		nameToField.put("expectedReturn", "产品预期收益率%");
		nameToField.put("isNvlType", "净值类型");
		nameToField.put("isNeedNav", "申赎使用净值");
		nameToField.put("calaType", "计息基准");
		nameToField.put("collectType", "募集方式");
		nameToField.put("investNature", "投资性质");
		nameToField.put("productTerm", "产品期限(天)");
		nameToField.put("curCode", "币种");
		nameToField.put("incomeType", "产品收益类型");
		nameToField.put("investerChannel", "目标客户类型");
		nameToField.put("investBrand", "产品品牌");
		nameToField.put("investPeriod", "产品期次");
		nameToField.put("openDays", "开放周期(天)");
		nameToField.put("investRegisterCode", "产品登记编码");
		nameToField.put("cashManagement", "是否现金管理类产品");
		nameToField.put("custType", "客户类型");
		nameToField.put("manageType", "管理方式");
		nameToField.put("fiduciaryDuty", "受托职责");
	}
	
	public void toProperties() {
		try (FileOutputStream out = new FileOutputStream("./source/file/config.xml")){
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("root");
			Element firstChild = root.addElement("table");
			firstChild.addAttribute("name", "CMD_PRODUCT_SETUP");
			Element secondChild = firstChild.addElement("relation");
			secondChild.addAttribute("type", "code2key");
			for (Map.Entry<String, String> entry : valueToCode.entrySet()) {
				Element element = secondChild.addElement("map");
				element.addAttribute("code", entry.getKey());
				element.addAttribute("dictkey", entry.getValue());
			}
			Element secondChild2 =  firstChild.addElement("relation");
			secondChild2.addAttribute("type", "name2field");
			for (Map.Entry<String, String> entry : nameToField.entrySet()) {
				Element element = secondChild2.addElement("map");
				element.addAttribute("name", entry.getKey());
				element.addAttribute("field", entry.getValue());
			}
			OutputFormat of = OutputFormat.createPrettyPrint();
			XMLWriter writer = new XMLWriter(out,of);
			writer.write(document);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public void read () {
		valueToCode.clear();
		try (FileInputStream fis = new FileInputStream("./source/file/config.xml")){
			SAXReader reader = new SAXReader();
			Document document = reader.read(fis);
			Element root = document.getRootElement();
			for (Element element : (List<Element>)root.elements("table")) {
				if (!"CMD_PRODUCT_SETUP"
						.equals(element.attributeValue("name"))) continue;
				for (Element el : (List<Element>)element.elements("relation")) {
					if(!"code2key".equals(el.attributeValue("type"))) continue;
					for (Element e : (List<Element>)el.elements("map")) {
						String code = e.attributeValue("code");
						String dictkey = e.attributeValue("dictkey");
						valueToCode.put(code,dictkey);
					}
				}
			}
			System.out.println(valueToCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		OutputProperties out = new OutputProperties();
		out.toProperties();
		//out.read();
	}
}
