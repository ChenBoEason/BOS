package com.cb.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Servlet;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cb.bos.domain.Region;
import com.cb.bos.service.IRegionService;
import com.cb.bos.utils.PageBean;
import com.cb.bos.utils.PinYin4jUtils;
import com.cb.bos.web.action.base.BaseAction;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 
*<p>Title:RegionAction</p>	
*<p>Description:区域</p>	
*@author Eason
*@Time 2017年8月23日下午4:20:33
 */
@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region>{

	@Autowired
	private IRegionService regionService; 
	
	/**
	 * 数据分页显示
	 */
	
	public String pageQuery() throws Exception{
		
		
		regionService.pageQuery(pageBean);
		//将pagebean转化为json数据  bean对象用object  如果是数组之类的用Array
		//如果想把多余的用不到的参数删除 不返回页面 用该方法   
		//要去除Subarea 关联  否则会陷入死循环  
		this.writePageBean2Json(pageBean, new String[]{"currentPage","pageSize","detachedCriteria","subareas"});
		
		return NONE;
	 }
	/**
	 * xls表导入
	 * myFile 接收上传的文件
	 */
	private File myFile;
	public String importXls() throws Exception{
		//判断是否导入成功
		String flag = "1";
		try {
			//使用POI解析Excel文件
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(myFile));
			//获得第一个sheet页
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
			//设置一个集合list来存放数据
			List<Region> listRegions = new ArrayList<>();
			
			for(Row row : hssfSheet){
				int rowNum = row.getRowNum();
				if(rowNum > 0){
					String id = row.getCell(0).getStringCellValue();
					String province = row.getCell(1).getStringCellValue();
					String city = row.getCell(2).getStringCellValue();
					String district = row.getCell(3).getStringCellValue();
					String postcode = row.getCell(4).getStringCellValue();
					
					//城市拼音 和  区域拼音简写  河北省 石家庄市 长安区 
					String pCity = city.substring(0, city.length()-1);
					String[] stringToPinyin = PinYin4jUtils.stringToPinyin(pCity);
					String citycode = StringUtils.join(stringToPinyin, "");//shijiazhuang
					
					//简码 
					String pprovince = province.substring(0,province.length()-1);
					String pdistrict = district.substring(0,district.length()-1);
					String allpingyin = pprovince + pCity + pdistrict;//河北石家庄长安
					String[] headByString = PinYin4jUtils.getHeadByString(allpingyin);
					String shortcode = StringUtils.join(headByString, "");
					
					Region region = new Region(id, province, city, district, postcode, shortcode, citycode,null);
					listRegions.add(region);
				}
			}
			
			regionService.saveRegion(listRegions);
		} catch (Exception e) {
			flag = "0";
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		return "list";
	}
	/**
	 * 添加分区  区域显示 
	 * @return
	 * @throws IOException 
	 */
	private String q; //combox 远程模糊查询固定字段
	public String pageListAjax() throws IOException{
		List<Region> list = null;
		
		if(StringUtils.isNotBlank(q)){
			list = regionService.findByQ(q);
		}else{
			list = regionService.findAll();
		}
		
		String[] excludes = new String[]{"subareas","province","city","district","postcode","shortcode","citycode"};
		this.writeList2Json(list, excludes);
		return NONE;
	}
	/**
	 * 添加区域
	 * @return
	 */
	public String addRegion(){
		String province = getModel().getProvince();
		String city = getModel().getCity();
		String district = getModel().getDistrict();
		
		String pCity = city.substring(0, city.length()-1);
		String pprovince = province.substring(0,province.length()-1);
		String pdistrict = district.substring(0,district.length()-1);
		//城市拼音
		String[] stringToPinyin = PinYin4jUtils.stringToPinyin(pCity);
		getModel().setCitycode(StringUtils.join(stringToPinyin, ""));
		//地址简写
		String headCity = pprovince + pCity + pdistrict;
		String[] headByString = PinYin4jUtils.getHeadByString(headCity);
		getModel().setShortcode(StringUtils.join(headByString, ""));
		regionService.saveOrUpdate(getModel());
		return "list";
	}
	/**
	 * 可多选删除数据
	 */
	private String ids;
	public String deleteRegions(){
		regionService.deleteRegions(ids);
		return "list";
	}
	/**
	 * 修改区域信息
	 * @return
	 */
	public String editRegion(){
		String province = getModel().getProvince();
		String city = getModel().getCity();
		String district = getModel().getDistrict();
		
		String pCity = city.substring(0, city.length()-1);
		String pprovince = province.substring(0,province.length()-1);
		String pdistrict = district.substring(0,district.length()-1);
		//城市拼音
		String[] stringToPinyin = PinYin4jUtils.stringToPinyin(pCity);
		getModel().setCitycode(StringUtils.join(stringToPinyin, ""));
		//地址简写
		String headCity = pprovince + pCity + pdistrict;
		String[] headByString = PinYin4jUtils.getHeadByString(headCity);
		getModel().setShortcode(StringUtils.join(headByString, ""));
		regionService.saveOrUpdate(getModel());
		return "list";
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public void setQ(String q) {
		this.q = q;
	}
	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}
}
